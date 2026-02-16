<template>
  <div class="flex flex-col h-full">
    <header class="mb-8">
      <h1
        class="text-2xl md:text-3xl font-bold text-surface-900 dark:text-surface-0"
      >
        Skill Tree
      </h1>
      <p class="text-surface-500 dark:text-surface-400">
        Unlock and upgrade your magical abilities.
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
      v-else-if="skillTrees.length === 0"
      class="bg-surface-0 dark:bg-surface-800 p-6 rounded-xl shadow-sm border border-surface-200 dark:border-surface-700"
    >
      <p class="text-surface-600 dark:text-surface-400">
        No skill trees found.
      </p>
    </div>

    <div v-else-if="skillTrees.length === 1" class="flex-1">
      <SkillTreeViewer :tree="skillTrees[0]!" />
    </div>

    <div v-else class="h-[calc(100vh-16rem)] flex flex-col flex-1">
      <Tabs :value="skillTrees[0]?.skill.id ?? ''" class="flex flex-col flex-1">
        <TabList>
          <Tab
            v-for="tree in skillTrees"
            :key="tree.skill.id"
            :value="tree.skill.id"
          >
            {{ tree.skill.name }}
          </Tab>
        </TabList>
        <TabPanels class="p-0! bg-transparent! flex-1">
          <TabPanel
            v-for="tree in skillTrees"
            :key="tree.skill.id"
            :value="tree.skill.id"
            class="p-0! h-full"
          >
            <SkillTreeViewer :tree="tree" />
          </TabPanel>
        </TabPanels>
      </Tabs>
    </div>
  </div>
</template>

<script setup lang="ts">
import Tab from "primevue/tab";
import TabList from "primevue/tablist";
import TabPanel from "primevue/tabpanel";
import TabPanels from "primevue/tabpanels";
import Tabs from "primevue/tabs";
import { onMounted, ref } from "vue";
import { api } from "../../lib/api";
import type { SkillTree } from "../../lib/types";
import SkillTreeViewer from "./SkillTree.vue";

const skillTrees = ref<SkillTree[]>([]);
const loading = ref(true);
const error = ref<string | null>(null);

onMounted(async () => {
  try {
    const result = await api.getSkillTrees();
    if (!result.isError()) {
      skillTrees.value = result.get();
    } else {
      error.value = result.error?.toString() || "Failed to load skill trees";
    }
  } catch (e) {
    error.value = "An unexpected error occurred";
    console.error(e);
  } finally {
    loading.value = false;
  }
});
</script>
