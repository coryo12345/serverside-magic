import { useLocalStorage } from "@vueuse/core";
import { computed } from "vue";
import { useAuth } from "./useAuth";

const superuserEnabled = useLocalStorage("server-magic-superuser", false);

export function useSuperuser() {
  const { isSuperuser } = useAuth();
  const isSuperuserModeActive = computed(
    () => isSuperuser.value && superuserEnabled.value,
  );
  return { isSuperuser, superuserEnabled, isSuperuserModeActive };
}
