<script setup lang="ts">
import { ref, computed } from "vue";
import Dialog from "primevue/dialog";
import Button from "primevue/button";
import Tag from "primevue/tag";
import Message from "primevue/message";
import { api } from "../../lib/api";
import type { PositionedNode } from "../../composables/useSkillTreeLayout";

const props = defineProps<{
  node: PositionedNode | null;
}>();

const emit = defineEmits<{
  (e: "unlocked"): void;
}>();

const visible = defineModel<boolean>("visible");
const loading = ref(false);
const error = ref<string | null>(null);

// Placeholder for future logic where we might have requirements like level or resources
const requirementsMet = computed(() => {
  // In the future, this would check against the user's current status
  return true;
});

const unlock = async () => {
  if (!props.node) return;
  
  loading.value = true;
  error.value = null;
  
  try {
    const result = await api.unlockSkill(props.node.id);
    if (!result.isError()) {
      emit("unlocked");
      visible.value = false;
    } else {
      error.value = result.error?.toString() || "Failed to unlock skill";
    }
  } catch (e) {
    error.value = "An unexpected error occurred: " + (e as Error).message;
    console.error(e);
  } finally {
    loading.value = false;
  }
};
</script>

<template>
  <Dialog
    v-model:visible="visible"
    modal
    dismissable-mask
    :header="node?.name || 'Skill Details'"
    :style="{ width: '30rem' }"
    :breakpoints="{ '1199px': '75vw', '575px': '90vw' }"
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

        <!-- Unlock Section -->
        <div v-if="!node.unlocked" class="pt-4 border-t border-surface-200 dark:border-surface-700">
          <div class="mb-4">
             <label class="text-sm font-semibold text-surface-500 dark:text-surface-400 block mb-2">
                Requirements
             </label>
             <div v-if="requirementsMet" class="flex items-center gap-2 text-sm text-green-600 dark:text-green-400">
                <i class="pi pi-check-circle"></i>
                <span>All requirements met</span>
             </div>
             <div v-else class="flex items-center gap-2 text-sm text-orange-600 dark:text-orange-400">
                <i class="pi pi-exclamation-triangle"></i>
                <span>You do not meet the requirements to unlock this skill.</span>
             </div>
          </div>

          <Button
            label="Unlock Skill"
            icon="pi pi-unlock"
            class="w-full"
            :loading="loading"
            :disabled="!requirementsMet"
            @click="unlock"
          />
          
          <Message v-if="error" severity="error" class="mt-4" closable @close="error = null">
            {{ error }}
          </Message>
        </div>
      </div>
    </div>
  </Dialog>
</template>
