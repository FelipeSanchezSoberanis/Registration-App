import { defineStore } from "pinia";
import { ref } from "vue";
import type { Ref } from "vue";

export const useAuthStore = defineStore("auth", () => {
  const isLoggedIn: Ref<boolean> = ref(false);

  return { isLoggedIn };
});
