import { defineStore } from "pinia";
import { ref } from "vue";
import type { Ref } from "vue";

export const useAuthStore = defineStore("auth", () => {
  const accessToken: Ref<string | null> = ref(null);
  const refreshToken: Ref<string | null> = ref(null);
  const isLoggedIn: Ref<boolean> = ref(false);

  return { accessToken, refreshToken, isLoggedIn };
});
