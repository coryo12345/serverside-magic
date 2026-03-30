<script setup lang="ts">
import { ref, computed, onMounted } from "vue";
import { VueDraggableNext } from "vue-draggable-next";
import { useToast } from "primevue/usetoast";
import { api } from "../../lib/api";
import { type SpellDefinition } from "../../lib/types";
import SpellCard from "./SpellCard.vue";
import SpellDetailsDialog from "./SpellDetailsDialog.vue";
import { slotNumberToString, SPELL_SLOTS } from "../../lib/spellslots";

const toast = useToast();
const allSpells = ref<SpellDefinition[]>([]);
// 8 slots, each is a list of spells. This is to keep track of pre/post change state
// so we can easily revert actions if the API denies it
const slots = ref<Record<number, SpellDefinition[]>>({});
const error = ref<string | null>(null);
const search = ref("");

const filteredSpells = computed(() => {
  const q = search.value.trim().toLowerCase();
  if (!q) return allSpells.value;
  return allSpells.value.filter((s) =>
    s.displayName.toLowerCase().includes(q)
  );
});

const selectedSpell = ref<SpellDefinition | null>(null);
const isDialogVisible = ref(false);

function openSpellDetails(spell: SpellDefinition) {
  selectedSpell.value = spell;
  isDialogVisible.value = true;
}

onMounted(async () => {
  const res = await api.getMySpells();
  if (res.isError()) {
    error.value = res.error().message;
  } else {
    allSpells.value = Object.values(res.get().availableSpells);
    slots.value = {};
    Object.entries(res.get().spellSlotMap).forEach(([slot, def]) => {
      slots.value[parseInt(slot)] = [def];
    });
    Object.values(SPELL_SLOTS).forEach((slotNum) => {
      if (!slots.value[slotNum]) {
        slots.value[slotNum] = [];
      }
    });
  }
});

async function onSlotChange(slotIndex: number | string, event: any) {
  slotIndex = parseInt(slotIndex.toString());

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

    const result = await api.setSpellSlot(newSpell.id, slotIndex);
    if (result.isError()) {
      // Revert state
      slots.value[slotIndex] = originalSlotState;

      toast.add({
        severity: "error",
        summary: "Error",
        detail: "Failed to assign spell",
        life: 3000,
      });
    }
  } else if (event.removed) {
    const removedSpell = event.removed.element;
    const result = await api.clearSpellSlot(slotIndex);

    if (result.isError()) {
      // Revert state
      slots.value[slotIndex] = [removedSpell];

      toast.add({
        severity: "error",
        summary: "Error",
        detail: "Failed to clear slot",
        life: 3000,
      });
    }
  }
}
</script>

<template>
  <div class="space-y-6">
    <header class="mb-8">
      <h1
        class="text-2xl md:text-3xl font-bold text-surface-900 dark:text-surface-0"
      >
        Spellbook Configuration
      </h1>
      <p class="text-surface-500 dark:text-surface-400">
        Manage your known spells and hotbar.
      </p>
    </header>

    <!-- Hotbar Slots -->
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
        class="flex flex-wrap gap-x-4 gap-y-6 mt-4"
      >
        <div
          v-for="(slotList, index) in slots"
          :key="index"
          class="flex flex-col items-center justify-start w-24 shrink-0 gap-1"
        >
          <p class="text-xs text-surface-400 font-mono text-center text-nowrap">
            {{ slotNumberToString(index) }}
          </p>

          <VueDraggableNext
            class="w-24 h-24 rounded-xl transition-all duration-200 slot-grid"
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
              class="w-full h-full cursor-pointer"
              @click="openSpellDetails(element)"
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

      <input
        v-model="search"
        type="text"
        placeholder="Search spells..."
        class="mb-4 w-full max-w-xs px-3 py-1.5 text-sm rounded-lg border border-surface-300 dark:border-surface-600 bg-surface-50 dark:bg-surface-900 text-surface-900 dark:text-surface-0 placeholder-surface-400 focus:outline-none focus:ring-2 focus:ring-primary-400"
      />

      <VueDraggableNext
        class="flex flex-wrap gap-x-4 gap-y-6"
        :list="filteredSpells"
        :group="{ name: 'spells', pull: 'clone', put: false }"
        :sort="false"
        :item-key="'id'"
      >
        <div
          v-for="element in filteredSpells"
          :key="element.id"
          class="cursor-pointer w-24 h-24 shrink-0"
          @click="openSpellDetails(element)"
        >
          <SpellCard :spell="element" compact />
        </div>
      </VueDraggableNext>
    </div>
    
    <SpellDetailsDialog
      v-model:visible="isDialogVisible"
      :spell="selectedSpell"
    />
  </div>
</template>

<style scoped>
/* Force children of the slot to overlap */
.slot-grid {
  display: grid;
  grid-template-columns: 1fr;
  grid-template-rows: 1fr;
}

.slot-grid > * {
  grid-column: 1;
  grid-row: 1;
  min-width: 0;
  min-height: 0;
}

</style>
