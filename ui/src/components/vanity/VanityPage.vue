<script setup lang="ts">
import { onMounted, ref, computed } from "vue";
import { useToast } from "primevue/usetoast";
import { api } from "../../lib/api";
import type { CosmeticSlotId, PlayerCosmeticsResponse } from "../../lib/types";

const toast = useToast();

const loading = ref(true);
const error = ref<string | null>(null);
const cosmetics = ref<PlayerCosmeticsResponse | null>(null);
const saving = ref<Record<CosmeticSlotId, boolean>>({
  helmet: false,
  chestplate: false,
  leggings: false,
  boots: false,
  spellbook: false,
});

type CosmeticOption = { id: string; displayName: string };

const NONE_OPTION: CosmeticOption = { id: "none", displayName: "None" };

const selectedObjects = ref<Record<CosmeticSlotId, CosmeticOption | null>>({
  helmet: NONE_OPTION,
  chestplate: NONE_OPTION,
  leggings: NONE_OPTION,
  boots: NONE_OPTION,
  spellbook: NONE_OPTION,
});

const suggestions = ref<Record<CosmeticSlotId, CosmeticOption[]>>({
  helmet: [],
  chestplate: [],
  leggings: [],
  boots: [],
  spellbook: [],
});

const SLOTS: { id: CosmeticSlotId; label: string; icon: string; description: string }[] = [
  { id: "helmet", label: "Helmet", icon: "🪖", description: "Customize your helmet appearance" },
  { id: "chestplate", label: "Chestplate", icon: "🥻", description: "Customize your chestplate appearance" },
  { id: "leggings", label: "Leggings", icon: "👖", description: "Customize your leggings appearance" },
  { id: "boots", label: "Boots", icon: "👢", description: "Customize your boots appearance" },
  { id: "spellbook", label: "Spellbook", icon: "📖", description: "Customize your spellbook appearance" },
];

const TIER_PREFIXES = new Set(["Netherite", "Diamond", "Iron", "Gold", "Chainmail"]);

function getSortKey(displayName: string): string {
  const words = displayName.split(" ");
  if (words[0] && TIER_PREFIXES.has(words[0])) {
    return words.slice(1).join(" ") + " " + words[0];
  }
  return displayName;
}

function sortOptions(options: CosmeticOption[]): CosmeticOption[] {
  const none = options.filter((o) => o.id === "none");
  const rest = options.filter((o) => o.id !== "none");
  rest.sort((a, b) => getSortKey(a.displayName).localeCompare(getSortKey(b.displayName)));
  return [...none, ...rest];
}

function getOptionsForSlot(slotId: CosmeticSlotId): CosmeticOption[] {
  const unlockedForSlot = cosmetics.value?.unlocked.filter((c) => c.slot === slotId) ?? [];
  return sortOptions([
    NONE_OPTION,
    ...unlockedForSlot.map((c) => ({ id: c.id, displayName: c.displayName })),
  ]);
}

function onSearch(slotId: CosmeticSlotId, event: { query: string }) {
  const query = event.query.toLowerCase().trim();
  const all = getOptionsForSlot(slotId);
  suggestions.value[slotId] = query
    ? all.filter((o) => o.displayName.toLowerCase().includes(query))
    : all;
}

function onBlur(slotId: CosmeticSlotId) {
  if (!selectedObjects.value[slotId]) {
    selectedObjects.value[slotId] = NONE_OPTION;
  }
}

const anySaving = computed(() => Object.values(saving.value).some(Boolean));

onMounted(async () => {
  const result = await api.getMyCosmetics();
  if (result.isError()) {
    error.value = result.error().message || "Failed to load cosmetics";
  } else {
    cosmetics.value = result.get();
    const sel = result.get().selected;
    for (const slotId of Object.keys(sel) as CosmeticSlotId[]) {
      const selectedId = sel[slotId];
      if (selectedId) {
        const option = getOptionsForSlot(slotId).find((o) => o.id === selectedId);
        selectedObjects.value[slotId] = option ?? NONE_OPTION;
      }
    }
  }
  loading.value = false;
});

async function onSelect(slotId: CosmeticSlotId) {
  const selected = selectedObjects.value[slotId];
  if (!selected) return;

  saving.value[slotId] = true;
  const style = selected.id;
  const result = await api.selectCosmetic(slotId, style);
  if (result.isError()) {
    const prevId = cosmetics.value?.selected[slotId] ?? null;
    const prevOption = prevId
      ? (getOptionsForSlot(slotId).find((o) => o.id === prevId) ?? NONE_OPTION)
      : NONE_OPTION;
    selectedObjects.value[slotId] = prevOption;
    toast.add({
      severity: "error",
      summary: "Error",
      detail: "Failed to update appearance",
      life: 3000,
    });
  } else {
    if (cosmetics.value) {
      cosmetics.value.selected[slotId] = style === "none" ? null : style;
    }
  }
  saving.value[slotId] = false;
}
</script>

<template>
  <div>
    <header class="mb-8">
      <h1 class="text-2xl md:text-3xl font-bold text-surface-900 dark:text-surface-0">
        Vanity
      </h1>
      <p class="text-surface-500 dark:text-surface-400">
        Customize the appearance of your armor and spellbook.
      </p>
    </header>

    <div v-if="loading" class="flex justify-center items-center h-64">
      <i class="pi pi-spin pi-spinner text-4xl text-primary"></i>
    </div>

    <div
      v-else-if="error"
      class="p-4 bg-red-100 dark:bg-red-900/20 text-red-700 dark:text-red-400 rounded-lg"
    >
      {{ error }}
    </div>

    <div v-else class="flex flex-col gap-4 max-w-2xl">
      <div
        v-for="slot in SLOTS"
        :key="slot.id"
        class="bg-surface-0 dark:bg-surface-800 border border-surface-200 dark:border-surface-700 rounded-xl p-5 flex items-center gap-5"
      >
        <!-- Slot icon -->
        <div
          class="w-12 h-12 rounded-lg bg-surface-100 dark:bg-surface-700 flex items-center justify-center text-2xl shrink-0"
        >
          {{ slot.icon }}
        </div>

        <!-- Slot info + autocomplete -->
        <div class="flex-1 min-w-0">
          <div class="flex items-center justify-between gap-4 flex-wrap">
            <div>
              <h2 class="font-semibold text-surface-900 dark:text-surface-0">
                {{ slot.label }}
              </h2>
              <p class="text-sm text-surface-500 dark:text-surface-400">
                {{ slot.description }}
              </p>
            </div>
            <div class="flex items-center gap-2 shrink-0">
              <i
                v-if="saving[slot.id]"
                class="pi pi-spin pi-spinner text-primary"
              ></i>
              <AutoComplete
                v-model="selectedObjects[slot.id]"
                :suggestions="suggestions[slot.id]"
                option-label="displayName"
                force-selection
                dropdown
                scroll-height="400px"
                :disabled="saving[slot.id] || anySaving"
                class="w-60"
                @complete="onSearch(slot.id, $event)"
                @item-select="onSelect(slot.id)"
                @blur="onBlur(slot.id)"
              />
            </div>
          </div>
        </div>
      </div>

      <p class="text-xs text-surface-400 dark:text-surface-500 mt-2">
        Cosmetic appearances are only visible while the item is equipped or held. Unlocking
        new cosmetics happens through in-game means.
      </p>
    </div>
  </div>
</template>
