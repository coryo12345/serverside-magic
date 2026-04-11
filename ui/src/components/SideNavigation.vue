<script setup lang="ts">
defineProps<{
  modelValue: string;
}>();

defineEmits(["logout", "update:modelValue"]);

const navItems = [
  { id: "spellbook", label: "My Spellbook", icon: "pi pi-book" },
  { id: "skills", label: "Skill Tree", icon: "pi pi-sitemap" },
  { id: "secrets", label: "Secrets", icon: "pi pi-lock" },
  { id: "info", label: "Getting Started", icon: "pi pi-info-circle" },
];
</script>

<template>
  <div class="flex flex-col h-full">
    <div class="p-6 border-b border-surface-200 dark:border-surface-700">
      <h1 class="text-xl font-bold text-primary-600 dark:text-primary-400">
        Server Magic
      </h1>
    </div>

    <nav class="flex-1 p-4 space-y-2">
      <button
        v-for="item in navItems"
        :key="item.id"
        @click="$emit('update:modelValue', item.id)"
        class="flex items-center w-full px-4 py-3 rounded-lg font-medium transition-colors cursor-pointer"
        :class="[
          modelValue === item.id
            ? 'bg-primary-50 dark:bg-primary-900/20 text-primary-700 dark:text-primary-300'
            : 'text-surface-600 dark:text-surface-400 hover:bg-surface-100 dark:hover:bg-surface-700',
        ]"
      >
        <i :class="[item.icon, 'mr-3']"></i>
        {{ item.label }}
      </button>
    </nav>

    <div class="p-4 border-t border-surface-200 dark:border-surface-700">
      <Button
        label="Logout"
        icon="pi pi-sign-out"
        severity="secondary"
        text
        class="w-full justify-start"
        @click="$emit('logout')"
      />
    </div>
  </div>
</template>
