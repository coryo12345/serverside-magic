<template>
  <div>
    <header class="mb-8">
      <h1
        class="text-2xl md:text-3xl font-bold text-surface-900 dark:text-surface-0"
      >
        Secrets
      </h1>
      <p class="text-surface-500 dark:text-surface-400">
        Hidden knowledge unlocked through your journey.
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

    <div
      v-else-if="secrets.length === 0"
      class="bg-surface-0 dark:bg-surface-800 p-10 rounded-xl shadow-sm border border-surface-200 dark:border-surface-700 flex flex-col items-center gap-4 text-center"
    >
      <i class="pi pi-lock text-5xl text-surface-400 dark:text-surface-500"></i>
      <p class="text-surface-600 dark:text-surface-400 text-lg">
        No secrets discovered yet.
      </p>
      <p class="text-surface-400 dark:text-surface-500 text-sm">
        Venture into The End to begin uncovering hidden knowledge.
      </p>
    </div>

    <div v-else class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
      <div
        v-for="secret in secrets"
        :key="secret.id"
        class="bg-surface-900 dark:bg-surface-950 border border-purple-700/50 dark:border-purple-600/40 rounded-xl p-6 shadow-lg flex flex-col gap-3"
        style="box-shadow: 0 0 18px 2px rgba(139, 92, 246, 0.15)"
      >
        <div class="flex items-center gap-3">
          <div
            class="w-9 h-9 rounded-full bg-purple-900/60 border border-purple-500/40 flex items-center justify-center shrink-0"
          >
            <i class="pi pi-eye text-purple-300 text-sm"></i>
          </div>
          <h2 class="text-base font-semibold text-purple-100 leading-tight">
            {{ secret.name }}
          </h2>
        </div>
        <p
          class="text-surface-300 dark:text-surface-400 text-sm leading-relaxed"
        >
          {{ secret.description }}
        </p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";
import { api } from "../../lib/api";
import type { SecretSkill } from "../../lib/types";
import { useSuperuser } from "../../composables/useSuperuser";

const secrets = ref<SecretSkill[]>([]);
const loading = ref(true);
const error = ref<string | null>(null);
const { isSuperuserModeActive } = useSuperuser();

onMounted(async () => {
  try {
    const result = await api.getMySecrets(isSuperuserModeActive.value);
    if (!result.isError()) {
      secrets.value = result.get();
    } else {
      error.value = result.error().message || "Failed to load secrets";
    }
  } catch (e) {
    error.value = "An unexpected error occurred";
    console.error(e);
  } finally {
    loading.value = false;
  }
});
</script>
