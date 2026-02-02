<script setup lang="ts">
import SpellbookConfig from "./spellbook/SpellbookConfig.vue";

const emit = defineEmits(["logout"]);
</script>

<template>
  <div class="min-h-screen bg-surface-50 dark:bg-surface-900 flex">
    <!-- Sidebar -->
    <aside
      class="w-64 bg-surface-0 dark:bg-surface-800 border-r border-surface-200 dark:border-surface-700 flex flex-col shrink-0 transition-all duration-300"
    >
      <div class="p-6 border-b border-surface-200 dark:border-surface-700">
        <h1 class="text-xl font-bold text-primary-600 dark:text-primary-400">
          Server Magic
        </h1>
      </div>

      <nav class="flex-1 p-4 space-y-2">
        <a
          href="#"
          class="flex items-center px-4 py-3 rounded-lg bg-primary-50 dark:bg-primary-900/20 text-primary-700 dark:text-primary-300 font-medium transition-colors"
        >
          <i class="pi pi-book mr-3"></i>
          My Spellbook
        </a>
      </nav>

      <div class="p-4 border-t border-surface-200 dark:border-surface-700">
        <Button
          label="Logout"
          icon="pi pi-sign-out"
          severity="secondary"
          text
          class="w-full justify-start"
          @click="emit('logout')"
        />
      </div>
    </aside>

    <!-- Main Content -->
    <main class="flex-1 overflow-y-auto h-screen">
      <div class="p-8 max-w-7xl mx-auto">
        <header class="mb-8">
          <h1 class="text-3xl font-bold text-surface-900 dark:text-surface-0">
            Spellbook Configuration
          </h1>
          <p class="text-surface-500 dark:text-surface-400">
            Manage your known spells and hotbar.
          </p>
        </header>

        <SpellbookConfig />
      </div>
    </main>
  </div>
</template>
