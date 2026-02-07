<script setup lang="ts">
import { type SpellDefinition } from "../../lib/types";
import Dialog from "primevue/dialog";
import Button from "primevue/button";

defineProps<{
  spell: SpellDefinition | null;
}>();

const visible = defineModel<boolean>("visible");
</script>

<template>
  <Dialog
    v-model:visible="visible"
    modal
    :header="spell?.displayName || 'Spell Details'"
    :style="{ width: '40rem' }"
    :breakpoints="{ '1199px': '75vw', '575px': '90vw' }"
  >
    <div v-if="spell" class="flex flex-col gap-6">
      <!-- Icon and Basic Info -->
      <div class="flex items-center gap-4">
        <div
          class="w-20 h-20 shrink-0 rounded-xl bg-primary-100 dark:bg-primary-900/30 text-primary-600 dark:text-primary-400 flex items-center justify-center border-2 border-primary-200 dark:border-primary-800"
        >
          <i :class="`pi ${spell.icon || 'pi-bolt'}`" style="font-size: 3rem"></i>
        </div>
        <div class="flex flex-col gap-1">
          <h3 class="text-xl font-bold text-surface-900 dark:text-surface-0">
            {{ spell.displayName }}
          </h3>
          <span
            v-if="spell.group"
            class="text-sm font-medium px-2 py-0.5 rounded-md bg-surface-100 dark:bg-surface-800 text-surface-600 dark:text-surface-400 self-start border border-surface-200 dark:border-surface-700"
          >
            {{ spell.group }}
          </span>
        </div>
      </div>

      <!-- Details -->
      <div class="space-y-4">
        <div>
          <label class="text-sm font-semibold text-surface-500 dark:text-surface-400 block mb-1">
            Description
          </label>
          <p class="text-surface-900 dark:text-surface-100 leading-relaxed">
            {{ spell.description }}
          </p>
        </div>

        <div>
          <label class="text-sm font-semibold text-surface-500 dark:text-surface-400 block mb-1">
            Mana Cost
          </label>
          <p class="text-surface-900 dark:text-surface-100 font-mono">
            {{ spell.cost }}
          </p>
        </div>
      </div>
    </div>
    
    <template #footer>
      <Button label="Close" icon="pi pi-check" @click="visible = false" />
    </template>
  </Dialog>
</template>
