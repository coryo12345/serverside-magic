<script setup lang="ts">
import { onMounted, ref } from "vue";
import LoginPage from "./components/LoginPage.vue";
import Dashboard from "./components/Dashboard.vue";
import { AuthToken } from "./lib/authtoken";

type ViewState = "login" | "dashboard";

const currentView = ref<ViewState>("login");
const currentUser = ref<string>("");

const handleLoginSuccess = (username: string) => {
  currentUser.value = username;
  currentView.value = "dashboard";
};

const handleLogout = () => {
  currentUser.value = "";
  currentView.value = "login";
  AuthToken.clear();
};

onMounted(() => {
  const token = AuthToken.get();
  if (token && token.length && !AuthToken.isExpired(token)) {
    currentView.value = "dashboard";
  }
});
</script>

<template>
  <div
    class="app-container font-sans text-surface-900 dark:text-surface-0 antialiased"
  >
    <Toast />
    <transition name="fade" mode="out-in">
      <LoginPage
        v-if="currentView === 'login'"
        @login-success="handleLoginSuccess"
      />
      <Dashboard v-else :username="currentUser" @logout="handleLogout" />
    </transition>
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
