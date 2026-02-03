import { ref } from "vue";
import { api } from "../lib/api";
import { AuthToken } from "../lib/authtoken";

const isAuthenticated = ref(false);
const isLoading = ref(true);
const currentUser = ref<string>("");

export function useAuth() {
  let intervalId: number | undefined;

  function stopSessionMonitoring() {
    if (intervalId) {
      clearInterval(intervalId);
      intervalId = undefined;
    }
  }

  function logout() {
    stopSessionMonitoring();
    AuthToken.clear();
    isAuthenticated.value = false;
    currentUser.value = "";
  }

  function startSessionMonitoring() {
    stopSessionMonitoring();
    
    // Check every minute
    intervalId = window.setInterval(() => {
      // If expiring in 5 mins (300 seconds)
      if (AuthToken.willExpireWithin(300)) {
        logout();
      }
    }, 60 * 1000);
  }

  async function checkAuth() {
    isLoading.value = true;
    
    // 1. Check local token existence and basic validity
    const token = AuthToken.get();
    if (!token || AuthToken.isExpired(token)) {
      logout(); // Clean up if invalid
      isLoading.value = false;
      return false;
    }

    // 2. Verify with backend
    const result = await api.userinfo();
    if (result.isValid()) {
      isAuthenticated.value = true;
      currentUser.value = result.get();
      startSessionMonitoring();
    } else {
      logout();
    }
    
    isLoading.value = false;
    return isAuthenticated.value;
  }

  return {
    isAuthenticated,
    isLoading,
    currentUser,
    checkAuth,
    logout,
    startSessionMonitoring,
  };
}
