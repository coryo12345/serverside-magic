<script setup lang="ts">
import { type SpellDefinition } from "../../lib/types";

defineProps<{
  spell: SpellDefinition;
  compact?: boolean;
}>();
</script>

<template>
  <div
    class="select-none transition-all cursor-move h-full"
    :class="[
      compact
        ? 'w-full aspect-square rounded-xl bg-surface-100 dark:bg-surface-800 border-2 border-surface-200 dark:border-surface-700 text-surface-600 dark:text-surface-400 shadow-sm flex flex-col items-center justify-between p-2'
        : 'bg-surface-0 dark:bg-surface-800 border border-surface-200 dark:border-surface-700 rounded-lg p-3 shadow-sm hover:shadow-md flex items-center gap-3',
    ]"
  >
    <!-- Compact Layout -->
    <template v-if="compact">
      <h3
        class="font-bold text-surface-900 dark:text-surface-0 text-xs text-center w-full leading-tight"
      >
        {{ spell.displayName }}
      </h3>
      <div
        class="flex-1 flex items-center justify-center w-full overflow-hidden"
      >
        <i
          :class="`pi ${spell.icon || 'pi-bolt'}`"
          style="font-size: 2rem"
        ></i>
      </div>
    </template>

    <!-- List Layout -->
    <template v-else>
      <div
        class="flex items-center justify-center shrink-0 transition-colors w-10 h-10 rounded-full bg-primary-100 dark:bg-primary-900/30 text-primary-600 dark:text-primary-400"
      >
        <i
          :class="`pi ${spell.icon || 'pi-bolt'}`"
          style="font-size: 1.25rem"
        ></i>
      </div>

      <div class="overflow-hidden">
        <h3
          class="font-medium text-surface-900 dark:text-surface-0 truncate leading-tight"
        >
          {{ spell.displayName }}
        </h3>
        <p
          class="text-xs text-surface-500 dark:text-surface-400 truncate mt-0.5"
        >
          Cost: {{ spell.cost }}
        </p>
      </div>
    </template>
  </div>
</template>
