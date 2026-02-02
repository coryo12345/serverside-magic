<script setup lang="ts">
import { ref, onMounted } from "vue";
import { VueDraggableNext } from "vue-draggable-next";
import { api } from "../../lib/api";
import { type SpellDefinition } from "../../lib/types";
import SpellCard from "./SpellCard.vue";

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

function onSlotChange(slotIndex: number, event: any) {
  if (event.added) {
    const newSpell = event.added.element;
    const slot = slots.value[slotIndex];
    
    if (slot && slot.length > 1) {
       // Keep only the newly added spell in this slot
       slots.value[slotIndex] = [newSpell];
    }

    // Call API
    api.setSpellSlot(newSpell.id, slotIndex);
  }
}
</script>

<template>
  <div class="space-y-6">
    <!-- Header -->
    <div class="bg-surface-0 dark:bg-surface-800 p-6 rounded-xl shadow-sm border border-surface-200 dark:border-surface-700">
      <h2 class="text-xl font-bold text-surface-900 dark:text-surface-0 mb-4">Spell Hotbar</h2>
      <p class="text-surface-500 dark:text-surface-400 mb-6 text-sm">
        Drag spells from your spellbook below into the slots to assign them.
      </p>

      <!-- Hotbar Slots -->
      <div class="grid grid-cols-4 md:grid-cols-8 gap-4">
        <div
          v-for="(slotList, index) in slots"
          :key="index"
          class="aspect-square bg-surface-50 dark:bg-surface-900 rounded-lg border-2 border-dashed border-surface-300 dark:border-surface-700 flex flex-col justify-center overflow-hidden relative"
        >
          <span class="absolute top-1 left-2 text-xs text-surface-400 font-mono">{{ index + 1 }}</span>
          
          <VueDraggableNext
            class="h-full w-full flex items-center justify-center p-1"
            :list="slotList"
            group="spells"
            @change="(e) => onSlotChange(index, e)"
            :item-key="'id'"
          >
            <template v-if="slotList.length === 0">
                 <div class="h-full w-full"></div>
            </template>
            <div v-for="element in slotList" :key="element.id" class="w-full h-full">
               <SpellCard :spell="element" compact />
            </div>
          </VueDraggableNext>
        </div>
      </div>
    </div>

    <!-- Spell Library -->
    <div class="bg-surface-0 dark:bg-surface-800 p-6 rounded-xl shadow-sm border border-surface-200 dark:border-surface-700">
      <h2 class="text-xl font-bold text-surface-900 dark:text-surface-0 mb-4">My Spellbook</h2>
      <div v-if="error" class="text-red-500 mb-4">Error loading spells: {{ error }}</div>
      
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
