<script setup lang="ts">
defineEmits(["logout"]);
</script>

<template>
  <div class="flex flex-col h-full">
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
        @click="$emit('logout')"
      />
    </div>
  </div>
</template>
