<script setup lang="ts">
import { ref, onMounted } from "vue";
import { api } from "../lib/api";
import { AuthToken } from "../lib/authtoken";

const emit = defineEmits<{
  (e: "login-success", username: string): void;
}>();

// State
const step = ref<1 | 2>(1);
const username = ref("");
const token = ref("");
const loading = ref(false);
const error = ref("");

// Step 1: Username
const handleUsernameSubmit = async () => {
  if (!username.value) {
    error.value = "Please enter a username.";
    return;
  }

  error.value = "";
  loading.value = true;

  const result = await api.requestCode(username.value);
  if (result.isError()) {
    error.value = result.error().message || "Failed to verify username.";
  } else {
    step.value = 2;
  }
  loading.value = false;
};

// Step 2: Token
const handleTokenSubmit = async () => {
  if (!token.value || token.value.length !== 4) {
    error.value = "Please enter a valid 4-digit code.";
    return;
  }

  error.value = "";
  loading.value = true;

  const result = await api.authVerify(username.value, token.value);
  if (result.isError()) {
    error.value =
      result.error().message ||
      "Failed to verify code. Try refreshing the page.";
  } else {
    AuthToken.set(result.get());
    emit("login-success", username.value);
  }
  loading.value = false;
};

// Auto-detect username from URL
onMounted(() => {
  const params = new URLSearchParams(window.location.search);
  const userParam = params.get("username");
  if (userParam) {
    username.value = userParam;
    handleUsernameSubmit();
  }
});

const reset = () => {
  step.value = 1;
  token.value = "";
  error.value = "";
  // Clear URL param if we reset, so refresh doesn't lock us
  const url = new URL(window.location.href);
  url.searchParams.delete("username");
  window.history.replaceState({}, "", url);
  username.value = "";
};
</script>

<template>
  <div
    class="flex items-center justify-center min-h-screen bg-surface-50 dark:bg-surface-950 p-4 transition-colors duration-300"
  >
    <div class="w-full max-w-md">
      <!-- Logo or Brand Placeholder -->
      <div class="text-center mb-8">
        <div
          class="inline-flex items-center justify-center w-16 h-16 rounded-full bg-primary-100 dark:bg-primary-900 text-primary-600 dark:text-primary-300 mb-4"
        >
          <i class="pi pi-bolt text-3xl"></i>
        </div>
        <h1 class="text-3xl font-bold text-surface-900 dark:text-surface-0">
          It's Time For Some Magic
        </h1>
        <p class="text-surface-500 dark:text-surface-400 mt-2">
          Manage your skills & spells
        </p>
      </div>

      <Card class="shadow-xl border-t-4 border-primary">
        <template #content>
          <div class="p-4">
            <h2
              class="text-xl font-semibold mb-6 text-center text-surface-800 dark:text-surface-100"
            >
              {{ step === 1 ? "Sign In" : "Verify Identity" }}
            </h2>

            <transition name="fade" mode="out-in">
              <!-- Step 1: Username -->
              <form
                v-if="step === 1"
                @submit.prevent="handleUsernameSubmit"
                class="flex flex-col gap-4"
              >
                <div class="flex flex-col gap-2">
                  <label
                    for="username"
                    class="font-medium text-surface-700 dark:text-surface-200"
                    >Username</label
                  >
                  <InputText
                    id="username"
                    v-model="username"
                    :disabled="loading"
                    placeholder="Enter your username"
                    class="w-full"
                  />
                </div>

                <Button
                  type="submit"
                  label="Continue"
                  icon="pi pi-arrow-right"
                  iconPos="right"
                  :loading="loading"
                  class="w-full mt-2"
                />
              </form>

              <!-- Step 2: Token -->
              <form
                v-else
                @submit.prevent="handleTokenSubmit"
                class="flex flex-col gap-6"
              >
                <div
                  class="bg-surface-50 dark:bg-surface-800 p-3 rounded-md flex items-center justify-between"
                >
                  <div class="flex items-center gap-2">
                    <Avatar
                      icon="pi pi-user"
                      shape="circle"
                      class="bg-primary text-white"
                    />
                    <span
                      class="font-medium text-surface-700 dark:text-surface-200"
                      >{{ username }}</span
                    >
                  </div>
                  <Button
                    icon="pi pi-pencil"
                    text
                    severity="secondary"
                    size="small"
                    @click="reset"
                    v-tooltip="'Change user'"
                  />
                </div>

                <div class="flex flex-col items-center gap-3">
                  <label
                    for="token"
                    class="font-medium text-surface-700 dark:text-surface-200"
                  >
                    <span class="block text-center"
                      >Check your in-game chat...</span
                    >
                    <span class="block text-center"
                      >You just received a code</span
                    >
                  </label>
                  <InputOtp
                    id="token"
                    v-model="token"
                    :length="4"
                    integerOnly
                    :disabled="loading"
                    class="gap-2"
                  />
                  <small class="text-surface-500 dark:text-surface-400"
                    >Please enter the 4-digit code.</small
                  >
                </div>

                <Button
                  type="submit"
                  label="Login"
                  icon="pi pi-check"
                  :loading="loading"
                  class="w-full"
                />
              </form>
            </transition>

            <!-- Error Message -->
            <div v-if="error" class="mt-4">
              <Message severity="error" :closable="false">{{ error }}</Message>
            </div>
          </div>
        </template>
      </Card>
    </div>
  </div>
</template>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
