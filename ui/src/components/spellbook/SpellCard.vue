<script setup lang="ts">
import { type SpellDefinition } from "../../lib/types";

defineProps<{
  spell: SpellDefinition;
  compact?: boolean;
}>();
</script>

<template>
  <div
    class="bg-surface-0 dark:bg-surface-800 border border-surface-200 dark:border-surface-700 rounded-lg p-3 shadow-sm hover:shadow-md transition-shadow cursor-move flex items-center gap-3 select-none"
    :class="{ 'flex-col text-center p-2': compact }"
  >
    <div
      class="w-10 h-10 rounded-full bg-primary-100 dark:bg-primary-900/30 flex items-center justify-center text-primary-600 dark:text-primary-400 shrink-0"
    >
      <i v-if="spell.icon" :class="`pi ${spell.icon}`" class="text-xl"></i>
      <i v-else class="pi pi-bolt text-xl"></i>
    </div>
    <div class="overflow-hidden">
      <h3 class="font-medium text-surface-900 dark:text-surface-0 truncate" :class="{ 'text-sm': compact }">
        {{ spell.displayName }}
      </h3>
      <p v-if="!compact" class="text-xs text-surface-500 dark:text-surface-400 truncate">
        Cost: {{ spell.cost }}
      </p>
    </div>
  </div>
</template>
