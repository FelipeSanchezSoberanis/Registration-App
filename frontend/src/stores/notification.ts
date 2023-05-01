import { defineStore } from "pinia";
import { ref } from "vue";
import type { Ref } from "vue";
import type { Notification } from "../types/FrontendTypes";
import { NotificationType } from "../types/FrontendTypes";

export const useNotificationStore = defineStore("notification", () => {
  const notifications: Ref<Notification[]> = ref([
    { message: "hi", notificationType: NotificationType.ERROR }
  ]);

  function addNotification(newNotification: Notification) {
    notifications.value.unshift(newNotification);
  }

  return { notifications, addNotification };
});
