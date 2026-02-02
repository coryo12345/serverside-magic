<script setup lang="ts">
import { ref, onMounted } from "vue";
import { VueDraggableNext } from "vue-draggable-next";
import { useToast } from "primevue/usetoast";
import { api } from "../../lib/api";
import { type SpellDefinition } from "../../lib/types";
import SpellCard from "./SpellCard.vue";

const toast = useToast();
const allSpells = ref<SpellDefinition[]>([]);
// 8 slots, each is a list of spells (max 1)
const slots = ref<SpellDefinition[][]>(Array.from({ length: 8 }, () => []));
const error = ref<string | null>(null);

onMounted(async () => {
  const res = await api.getMySpells();
  if (res.isError()) {
    error.value = res.error().message;
  } else {
    allSpells.value = Object.values(res.get());
  }
});

async function onSlotChange(slotIndex: number, event: any) {
  if (event.added) {
    const newSpell = event.added.element;
    const currentSlot = slots.value[slotIndex];
    if (!currentSlot) return;

    let originalSlotState: SpellDefinition[] = [];
    if (currentSlot.length > 1) {
      // The one that is NOT the new spell was the old one
      originalSlotState = currentSlot.filter((s) => s.id !== newSpell.id);
    } else {
      // It was empty before
      originalSlotState = [];
    }

    // Optimistic update: Enforce single item
    slots.value[slotIndex] = [newSpell];

    // Call API
    const result = await api.setSpellSlot(newSpell.id, slotIndex);

    if (result.isError()) {
      // Revert state
      slots.value[slotIndex] = originalSlotState;

      // Show error
      toast.add({
        severity: "error",
        summary: "Error",
        detail: "Failed to assign spell",
        life: 3000,
      });
    }
  }
}
</script>

<template>
  <div class="space-y-6">
    <!-- Header -->
    <div
      class="bg-surface-0 dark:bg-surface-800 p-6 rounded-xl shadow-sm border border-surface-200 dark:border-surface-700"
    >
      <h2 class="text-xl font-bold text-surface-900 dark:text-surface-0 mb-4">
        Spell Hotbar
      </h2>
      <p class="text-surface-500 dark:text-surface-400 mb-12 text-sm">
        Drag spells from your spellbook below into the slots to assign them.
      </p>

      <!-- Hotbar Slots -->
      <div
        class="grid grid-cols-3 md:grid-cols-4 lg:grid-cols-8 gap-x-4 gap-y-8 mt-4"
      >
        <div
          v-for="(slotList, index) in slots"
          :key="index"
          class="relative flex flex-col"
        >
          <span
            class="absolute -top-6 left-1/2 -translate-x-1/2 text-xs text-surface-400 font-mono"
            >Slot {{ index + 1 }}</span
          >

          <VueDraggableNext
            class="w-full rounded-xl transition-all duration-200"
            :class="[
              slotList.length === 0
                ? 'aspect-square border-2 border-dashed border-surface-300 dark:border-surface-700 bg-surface-50 dark:bg-surface-900 hover:border-surface-400 dark:hover:border-surface-600'
                : 'bg-transparent',
            ]"
            :list="slotList"
            group="spells"
            @change="(e) => onSlotChange(index, e)"
            :item-key="'id'"
          >
            <div
              v-for="element in slotList"
              :key="element.id"
              class="w-full h-full"
            >
              <SpellCard :spell="element" compact />
            </div>
          </VueDraggableNext>
        </div>
      </div>
    </div>

    <!-- Spell Library -->
    <div
      class="bg-surface-0 dark:bg-surface-800 p-6 rounded-xl shadow-sm border border-surface-200 dark:border-surface-700"
    >
      <h2 class="text-xl font-bold text-surface-900 dark:text-surface-0 mb-4">
        My Spellbook
      </h2>
      <div v-if="error" class="text-red-500 mb-4">
        Error loading spells: {{ error }}
      </div>

      <div class="h-[500px] overflow-y-auto pr-2 custom-scrollbar">
        <VueDraggableNext
          class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4"
          :list="allSpells"
          :group="{ name: 'spells', pull: 'clone', put: false }"
          :sort="false"
          :item-key="'id'"
        >
          <div v-for="element in allSpells" :key="element.id">
            <SpellCard :spell="element" />
          </div>
        </VueDraggableNext>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Custom scrollbar for better look in containers */
.custom-scrollbar::-webkit-scrollbar {
  width: 8px;
}
.custom-scrollbar::-webkit-scrollbar-track {
  background: transparent;
}
.custom-scrollbar::-webkit-scrollbar-thumb {
  background-color: var(--surface-300);
  border-radius: 20px;
}
.dark .custom-scrollbar::-webkit-scrollbar-thumb {
  background-color: var(--surface-600);
}
</style>
