import { defineStore } from "pinia";
import { ref } from "vue";
import type { Ref } from "vue";

export const useAuthStore = defineStore("auth", () => {
  const refreshToken: Ref<string | null> = ref(null);
  const isLoggedIn: Ref<boolean> = ref(false);

  return { refreshToken, isLoggedIn };
});
