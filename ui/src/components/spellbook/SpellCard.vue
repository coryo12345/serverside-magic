<script setup lang="ts">
import { type SpellDefinition } from "../../lib/types";

defineProps<{
  spell: SpellDefinition;
  compact?: boolean;
}>();
</script>

<template>
  <div
    class="select-none transition-all cursor-move"
    :class="[
      compact
        ? 'flex flex-col items-center gap-2 w-full'
        : 'bg-surface-0 dark:bg-surface-800 border border-surface-200 dark:border-surface-700 rounded-lg p-3 shadow-sm hover:shadow-md flex items-center gap-3',
    ]"
  >
    <!-- Icon Container -->
    <div
      class="flex items-center justify-center shrink-0 transition-colors"
      :class="[
        compact
          ? 'w-full aspect-square rounded-xl bg-surface-100 dark:bg-surface-800 border-2 border-surface-200 dark:border-surface-700 text-surface-600 dark:text-surface-400 shadow-sm'
          : 'w-10 h-10 rounded-full bg-primary-100 dark:bg-primary-900/30 text-primary-600 dark:text-primary-400',
      ]"
    >
      <i
        v-if="spell.icon"
        :class="`pi ${spell.icon}`"
        :style="{ fontSize: compact ? '1.5rem' : '1.25rem' }"
      ></i>
      <i
        v-else
        class="pi pi-bolt"
        :style="{ fontSize: compact ? '1.5rem' : '1.25rem' }"
      ></i>
    </div>

    <!-- Text Info -->
    <div class="overflow-hidden" :class="{ 'text-center w-full': compact }">
      <h3
        class="font-medium text-surface-900 dark:text-surface-0 truncate leading-tight"
        :class="{ 'text-xs': compact }"
      >
        {{ spell.displayName }}
      </h3>
      <p
        v-if="!compact"
        class="text-xs text-surface-500 dark:text-surface-400 truncate mt-0.5"
      >
        Cost: {{ spell.cost }}
      </p>
    </div>
  </div>
</template>
