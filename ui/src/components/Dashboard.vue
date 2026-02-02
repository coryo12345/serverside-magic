<script setup lang="ts">
import { ref } from "vue";
import SpellbookConfig from "./spellbook/SpellbookConfig.vue";
import SideNavigation from "./SideNavigation.vue";

const emit = defineEmits(["logout"]);
const drawerVisible = ref(false);
</script>

<template>
  <div class="min-h-screen bg-surface-50 dark:bg-surface-900 flex">
    <!-- Desktop Sidebar (Hidden on mobile) -->
    <aside
      class="hidden md:flex w-64 bg-surface-0 dark:bg-surface-800 border-r border-surface-200 dark:border-surface-700 flex-col shrink-0 transition-all duration-300"
    >
      <SideNavigation @logout="emit('logout')" />
    </aside>

    <!-- Mobile Drawer -->
    <Drawer v-model:visible="drawerVisible">
      <SideNavigation @logout="emit('logout')" />
    </Drawer>

    <!-- Main Content -->
    <main class="flex-1 overflow-y-auto h-screen flex flex-col">
      <!-- Mobile Header / Toggle -->
      <div
        class="md:hidden p-4 bg-surface-0 dark:bg-surface-800 border-b border-surface-200 dark:border-surface-700 flex items-center gap-4"
      >
        <Button
          icon="pi pi-bars"
          text
          aria-label="Menu"
          @click="drawerVisible = true"
        />
        <h1 class="text-lg font-bold text-surface-900 dark:text-surface-0">
          Server Magic
        </h1>
      </div>

      <div class="p-8 max-w-7xl mx-auto w-full">
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

        <SpellbookConfig />
      </div>
    </main>
  </div>
</template>
