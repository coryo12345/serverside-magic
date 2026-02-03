<script setup lang="ts">
import { onMounted } from "vue";
import LoginPage from "./components/LoginPage.vue";
import Dashboard from "./components/Dashboard.vue";
import { useAuth } from "./composables/useAuth";

const { isAuthenticated, isLoading, currentUser, checkAuth, logout } = useAuth();

const handleLoginSuccess = async () => {
  await checkAuth();
};

onMounted(() => {
  checkAuth();
});
</script>

<template>
  <div
    class="app-container font-sans text-surface-900 dark:text-surface-0 antialiased"
  >
    <Toast />
    
    <div v-if="isLoading" class="flex items-center justify-center min-h-screen">
      <ProgressSpinner />
    </div>

    <template v-else>
      <transition name="fade" mode="out-in">
        <Dashboard 
          v-if="isAuthenticated" 
          :username="currentUser" 
          @logout="logout" 
        />
        <LoginPage
          v-else
          @login-success="handleLoginSuccess"
        />
      </transition>
    </template>
  </div>
</template>

<style>
/* Global resets or transitions if needed */
body {
  margin: 0;
  padding: 0;
  background-color: var(--surface-ground);
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
