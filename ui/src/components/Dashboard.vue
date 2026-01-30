<script setup lang="ts">
defineProps<{
  username: string;
}>();

const emit = defineEmits(['logout']);
</script>

<template>
  <div class="min-h-screen bg-surface-50 dark:bg-surface-900 p-8">
    <div class="max-w-7xl mx-auto">
      <header class="mb-8 flex justify-between items-center">
        <div>
          <h1 class="text-3xl font-bold text-surface-900 dark:text-surface-0">Dashboard</h1>
          <p class="text-surface-500 dark:text-surface-400">Welcome back, {{ username }}!</p>
        </div>
        <Button label="Logout" icon="pi pi-sign-out" severity="secondary" @click="emit('logout')" />
      </header>

      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <!-- Placeholder Stats -->
        <Card class="surface-card p-4 shadow-sm border-round">
            <template #title>Server Status</template>
            <template #content>
                <div class="flex items-center gap-2 text-green-500 font-medium">
                    <i class="pi pi-check-circle"></i>
                    <span>Online</span>
                </div>
            </template>
        </Card>
        
        <Card class="surface-card p-4 shadow-sm border-round">
             <template #title>Active Players</template>
             <template #content>
                 <span class="text-2xl font-bold">12</span>
             </template>
        </Card>

        <Card class="surface-card p-4 shadow-sm border-round">
             <template #title>Uptime</template>
             <template #content>
                 <span class="text-2xl font-bold">4d 12h</span>
             </template>
        </Card>
      </div>
      
      <div class="mt-8">
        <Panel header="Recent Activity">
            <p class="m-0">
                No recent activity to display.
            </p>
        </Panel>
      </div>
    </div>
  </div>
</template>
