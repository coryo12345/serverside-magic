<script setup lang="ts">
import { onMounted, ref } from "vue";
import { api } from "../lib/api";

const emit = defineEmits(["logout"]);

onMounted(() => {
  loadSpells();
});

const spells = ref(null);
const err = ref<string | null>(null);
async function loadSpells() {
  err.value = null;
  const res = await api.getMySpells();
  if (res.isError()) {
    err.value = res.error().message;
  } else {
    spells.value = res.get();
  }
}
</script>

<template>
  <div class="min-h-screen bg-surface-50 dark:bg-surface-900 p-8">
    <div class="max-w-7xl mx-auto">
      <header class="mb-8 flex justify-between items-center">
        <div>
          <h1 class="text-3xl font-bold text-surface-900 dark:text-surface-0">
            Dashboard
          </h1>
          <p class="text-surface-500 dark:text-surface-400">Welcome back!</p>
        </div>
        <Button
          label="Logout"
          icon="pi pi-sign-out"
          severity="secondary"
          @click="emit('logout')"
        />
      </header>

      <pre>{{ JSON.stringify(spells, null, 2) }}</pre>
    </div>
  </div>
</template>
