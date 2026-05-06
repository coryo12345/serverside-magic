#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
RESOURCEPACK_DIR="$SCRIPT_DIR/resourcepack"
SERVER_PROPERTIES="$SCRIPT_DIR/run/server.properties"
DEV_MODE=false
PACK_FILENAME="servermagic-resourcepack.zip"

# Parse flags
POSITIONAL_ARGS=()
for arg in "$@"; do
  if [[ "$arg" == "--dev" ]]; then
    DEV_MODE=true
  else
    POSITIONAL_ARGS+=("$arg")
  fi
done
set -- "${POSITIONAL_ARGS[@]+"${POSITIONAL_ARGS[@]}"}"

if $DEV_MODE; then
  OUTPUT_DIR="$SCRIPT_DIR/run/config/servermagic/web/assets"
  mkdir -p "$OUTPUT_DIR"
else
  OUTPUT_DIR="$SCRIPT_DIR/src/main/resources/web/assets"
  mkdir -p "$OUTPUT_DIR"
fi
OUTPUT="$OUTPUT_DIR/$PACK_FILENAME"

# Check dependencies
for cmd in curl unzip zip; do
  if ! command -v "$cmd" &>/dev/null; then
    echo "Error: '$cmd' is required but not found." >&2
    exit 1
  fi
done

WORK_DIR="$(mktemp -d)"
trap 'chmod -R u+w "$WORK_DIR" 2>/dev/null; rm -rf "$WORK_DIR"' EXIT

OTHER_ZIP="$WORK_DIR/other_pack.zip"

if [[ $# -eq 0 ]]; then
  # Default: fetch latest release from coryo12345/custom-textures
  if ! command -v jq &>/dev/null; then
    echo "Error: 'jq' is required for auto-fetching the latest release." >&2
    exit 1
  fi
  echo "Fetching latest release from coryo12345/custom-textures..."
  RELEASE_JSON="$(curl -fsSL "https://api.github.com/repos/coryo12345/custom-textures/releases/latest")"
  DOWNLOAD_URL="$(echo "$RELEASE_JSON" | jq -r '.assets[] | select(.name | test("resourcepack"; "i")) | .browser_download_url' | head -1)"
  if [[ -z "$DOWNLOAD_URL" ]]; then
    echo "Error: No asset containing 'resourcepack' found in the latest release." >&2
    exit 1
  fi
  echo "Downloading: $DOWNLOAD_URL"
  curl -fsSL -o "$OTHER_ZIP" "$DOWNLOAD_URL"
elif [[ "${1:-}" =~ ^https?:// ]]; then
  # URL
  echo "Downloading: $1"
  curl -fsSL -o "$OTHER_ZIP" "$1"
else
  # Local file
  if [[ ! -f "${1:-}" ]]; then
    echo "Error: File not found: ${1:-}" >&2
    exit 1
  fi
  OTHER_ZIP="$1"
fi

MERGED_DIR="$WORK_DIR/merged"
mkdir -p "$MERGED_DIR"

echo "Extracting base resource pack..."
unzip -q "$OTHER_ZIP" -d "$MERGED_DIR"

echo "Merging mod resource pack (mod files take priority)..."
cp -r "$RESOURCEPACK_DIR"/. "$MERGED_DIR/"

echo "Zipping final pack to: $OUTPUT"
rm -f "$OUTPUT"
(cd "$MERGED_DIR" && zip -qr "$OUTPUT" .)

# Generate armor cosmetics from equipment directory
COSMETICS_FILE="$SCRIPT_DIR/src/main/java/servermagic/cosmetics/Cosmetics.java"
EQUIPMENT_DIR="$MERGED_DIR/assets/minecraft/equipment"

if [[ -f "$COSMETICS_FILE" && -d "$EQUIPMENT_DIR" ]]; then
  echo "Generating armor cosmetics in Cosmetics.java..."

  title_case() {
    echo "$1" | sed 's/_/ /g' | awk '{for(i=1;i<=NF;i++) $i=toupper(substr($i,1,1)) substr($i,2); print}'
  }

  GENERATED_FILE="$WORK_DIR/generated.txt"
  printf '\n' > "$GENERATED_FILE"

  # First pass: collect every name to detect which appear in multiple tiers
  NAMES_FILE="$WORK_DIR/names.txt"
  for tier in chainmail iron gold diamond netherite; do
    tier_dir="$EQUIPMENT_DIR/$tier"
    [[ -d "$tier_dir" ]] || continue
    for json_file in "$tier_dir"/*.json; do
      [[ -f "$json_file" ]] || continue
      basename "$json_file" .json >> "$NAMES_FILE"
    done
  done

  name_is_multi_tier() {
    local count
    count=$(grep -c "^${1}$" "$NAMES_FILE" 2>/dev/null || echo 0)
    [[ "$count" -gt 1 ]]
  }

  for tier in chainmail iron gold diamond netherite; do
    tier_dir="$EQUIPMENT_DIR/$tier"
    [[ -d "$tier_dir" ]] || continue

    for json_file in "$tier_dir"/*.json; do
      [[ -f "$json_file" ]] || continue
      name="$(basename "$json_file" .json)"

      display_tier="$(title_case "$tier")"
      display_name="$(title_case "$name")"
      var_prefix="$(echo "${tier}_${name}" | tr '[:lower:]' '[:upper:]')"
      item_model="minecraft:${tier}/${name}"
      cosmetic_id="${tier}/${name}"

      if name_is_multi_tier "$name"; then
        label_prefix="${display_tier} ${display_name}"
      else
        label_prefix="${display_name}"
      fi

      for slot_lower in helmet chestplate leggings boots; do
        slot_upper="$(echo "$slot_lower" | tr '[:lower:]' '[:upper:]')"
        slot_display="$(title_case "$slot_lower")"
        printf '    public static final Cosmetic %s = new Cosmetic(\n' "${var_prefix}_${slot_upper}" >> "$GENERATED_FILE"
        printf '            "%s", "%s", CosmeticSlot.%s, "%s");\n' \
          "${cosmetic_id}_${slot_lower}" \
          "${label_prefix} ${slot_display}" \
          "$slot_upper" \
          "$item_model" >> "$GENERATED_FILE"
      done
      printf '\n' >> "$GENERATED_FILE"
    done
  done

  awk -v gen_file="$GENERATED_FILE" '
    /\/\/ below are generated cosmetic items, do not edit manually <generated>/ {
      print
      while ((getline line < gen_file) > 0) print line
      in_generated = 1
      next
    }
    /\/\/ <\/generated>/ { in_generated = 0 }
    !in_generated { print }
  ' "$COSMETICS_FILE" > "$COSMETICS_FILE.tmp" && mv "$COSMETICS_FILE.tmp" "$COSMETICS_FILE"

  echo "  Updated Cosmetics.java"
fi

if $DEV_MODE; then
  PACK_URL="http://localhost:8080/assets/$PACK_FILENAME"
  PACK_SHA1="$(shasum -a 1 "$OUTPUT" | awk '{print $1}')"

  if [[ ! -f "$SERVER_PROPERTIES" ]]; then
    echo "Warning: $SERVER_PROPERTIES not found — skipping server.properties update." >&2
  else
    echo "Updating run/server.properties..."
    # Update resource-pack fields in-place
    sed -i '' \
      -e "s|^resource-pack=.*|resource-pack=$PACK_URL|" \
      -e "s|^resource-pack-sha1=.*|resource-pack-sha1=$PACK_SHA1|" \
      "$SERVER_PROPERTIES"
    echo "  resource-pack=$PACK_URL"
    echo "  resource-pack-sha1=$PACK_SHA1"
  fi
fi

echo "Done: $OUTPUT"
