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
const selectedValues = ref<Record<CosmeticSlotId, string>>({
  helmet: "none",
  chestplate: "none",
  leggings: "none",
  boots: "none",
  spellbook: "none",
});

const SLOTS: { id: CosmeticSlotId; label: string; icon: string; description: string }[] = [
  { id: "helmet", label: "Helmet", icon: "🪖", description: "Customize your helmet appearance" },
  { id: "chestplate", label: "Chestplate", icon: "🥻", description: "Customize your chestplate appearance" },
  { id: "leggings", label: "Leggings", icon: "👖", description: "Customize your leggings appearance" },
  { id: "boots", label: "Boots", icon: "👢", description: "Customize your boots appearance" },
  { id: "spellbook", label: "Spellbook", icon: "📖", description: "Customize your spellbook appearance" },
];

function getOptionsForSlot(slotId: CosmeticSlotId) {
  const unlockedForSlot = cosmetics.value?.unlocked.filter((c) => c.slot === slotId) ?? [];
  return [
    { id: "none", displayName: "None" },
    ...unlockedForSlot.map((c) => ({ id: c.id, displayName: c.displayName })),
  ];
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
      selectedValues.value[slotId] = sel[slotId] ?? "none";
    }
  }
  loading.value = false;
});

async function onSelect(slotId: CosmeticSlotId) {
  saving.value[slotId] = true;
  const style = selectedValues.value[slotId];
  const result = await api.selectCosmetic(slotId, style);
  if (result.isError()) {
    // Revert to previous selection from server state
    selectedValues.value[slotId] = cosmetics.value?.selected[slotId] ?? "none";
    toast.add({
      severity: "error",
      summary: "Error",
      detail: "Failed to update appearance",
      life: 3000,
    });
  } else {
    // Update local state to match what we just saved
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

        <!-- Slot info + dropdown -->
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
              <Select
                v-model="selectedValues[slot.id]"
                :options="getOptionsForSlot(slot.id)"
                option-label="displayName"
                option-value="id"
                :disabled="saving[slot.id] || anySaving"
                class="w-44"
                @change="onSelect(slot.id)"
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
