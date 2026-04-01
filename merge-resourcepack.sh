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
  OUTPUT_DIR="$SCRIPT_DIR"
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
