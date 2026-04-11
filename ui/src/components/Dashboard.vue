<script setup lang="ts">
import { ref } from "vue";
import SpellbookConfig from "./spellbook/SpellbookConfig.vue";
import SideNavigation from "./SideNavigation.vue";
import SkillTreePage from "./skilltree/SkillTreePage.vue";
import SecretsPage from "./secrets/SecretsPage.vue";

const emit = defineEmits(["logout"]);
const drawerVisible = ref(false);
const currentPage = ref("spellbook");
</script>

<template>
  <div class="min-h-screen bg-surface-50 dark:bg-surface-900 flex">
    <!-- Desktop Sidebar (Hidden on mobile) -->
    <aside
      class="hidden md:flex w-64 bg-surface-0 dark:bg-surface-800 border-r border-surface-200 dark:border-surface-700 flex-col shrink-0 transition-all duration-300"
    >
      <SideNavigation v-model="currentPage" @logout="emit('logout')" />
    </aside>

    <!-- Mobile Drawer -->
    <Drawer v-model:visible="drawerVisible">
      <SideNavigation
        v-model="currentPage"
        @logout="emit('logout')"
        @update:modelValue="drawerVisible = false"
      />
    </Drawer>

    <!-- Main Content -->
    <main class="flex-1 overflow-y-auto h-screen flex flex-col">
      <!-- Mobile Header / Toggle -->
      <div
        class="md:hidden p-4 bg-surface-0 dark:bg-surface-800 border-b border-surface-200 dark:border-surface-700 flex items-center justify-between"
      >
        <div class="flex items-center gap-4">
          <Button
            icon="pi pi-bars"
            text
            aria-label="Menu"
            @click="drawerVisible = true"
          />
          <h1 class="text-lg font-bold text-primary-600 dark:text-primary-400">
            Server Magic
          </h1>
        </div>
      </div>

      <div class="p-4 md:p-8 max-w-7xl mx-auto w-full h-full">
        <template v-if="currentPage === 'spellbook'">
          <SpellbookConfig />
        </template>

        <template v-else-if="currentPage === 'skills'">
          <SkillTreePage></SkillTreePage>
        </template>

        <template v-else-if="currentPage === 'secrets'">
          <SecretsPage />
        </template>
      </div>
    </main>
  </div>
</template>
