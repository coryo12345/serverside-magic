<script setup lang="ts">
import Dialog from "primevue/dialog";
import Tag from "primevue/tag";
import type { PositionedNode } from "../../composables/useSkillTreeLayout";

defineProps<{
  node: PositionedNode | null;
}>();

const visible = defineModel<boolean>("visible");
</script>

<template>
  <Dialog
    v-model:visible="visible"
    modal
    dismissable-mask
    :header="node?.name || 'Skill Details'"
    :style="{ width: '30rem' }"
    :breakpoints="{ '1199px': '75vw', '575px': '90vw' }"
    :class="{ 'border-2 border-primary-500': node?.unlocked }"
  >
    <div v-if="node" class="flex flex-col gap-6">
      <!-- Skill Info -->
      <div class="flex items-center gap-4">
        <div
          class="w-16 h-16 shrink-0 rounded-xl flex items-center justify-center border-2 transition-all duration-300"
          :class="[
            node.unlocked
              ? 'bg-primary-100 dark:bg-primary-900/30 text-primary-600 dark:text-primary-400 border-primary-200 dark:border-primary-800'
              : 'bg-surface-100 dark:bg-surface-800 text-surface-400 border-surface-200 dark:border-surface-700'
          ]"
        >
          <i
            :class="`pi ${node.unlocked ? 'pi-star-fill' : 'pi-lock'}`"
            style="font-size: 2rem"
          ></i>
        </div>
        <div class="flex flex-col gap-1">
          <h3 class="text-xl font-bold text-surface-900 dark:text-surface-0">
            {{ node.name }}
          </h3>
          <Tag
            :severity="node.unlocked ? 'success' : 'secondary'"
            :value="node.unlocked ? 'Unlocked' : 'Locked'"
            class="self-start"
          />
        </div>
      </div>

      <!-- Description -->
      <div class="space-y-4">
        <div>
          <label
            class="text-sm font-semibold text-surface-500 dark:text-surface-400 block mb-1"
          >
            Description
          </label>
          <p class="text-surface-900 dark:text-surface-100 leading-relaxed">
            {{ node.description }}
          </p>
        </div>

        <!-- Unlock Requirements -->
        <div v-if="node.advancementName || node.unlockDescription" class="pt-4 border-t border-surface-200 dark:border-surface-700">
          <label class="text-sm font-semibold text-surface-500 dark:text-surface-400 block mb-2">
             {{ node.unlocked ? 'Unlock Requirements' : 'How to Unlock' }}
          </label>
          
          <div v-if="node.advancementName" class="mb-3">
            <div class="flex items-start gap-2 text-surface-900 dark:text-surface-100">
              <i class="pi pi-shield mt-1 text-primary-500"></i>
              <div>
                <span class="font-medium">Advancement:</span>
                <p class="text-sm text-surface-600 dark:text-surface-400">
                  {{ node.advancementName }}
                </p>
              </div>
            </div>
          </div>

          <div v-if="node.unlockDescription">
            <div class="flex items-start gap-2 text-surface-900 dark:text-surface-100">
              <i class="pi pi-info-circle mt-1 text-primary-500"></i>
              <div>
                <span v-if="node.advancementName" class="font-medium">Additional Info:</span>
                <span v-else class="font-medium">Requirement:</span>
                <p class="text-sm text-surface-600 dark:text-surface-400">
                  {{ node.unlockDescription }}
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </Dialog>
</template>
