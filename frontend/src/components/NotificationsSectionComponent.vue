<script setup lang="ts">
import { onMounted } from "vue";
import { ref } from "vue";
import type { Ref } from "vue";
import { useNotificationStore } from "../stores/notification";

const notificationStore = useNotificationStore();
</script>

<template>
  <div aria-live="polite" aria-atomic="true" class="position-relative">
    <!-- Position it: -->
    <!-- - `.toast-container` for spacing between toasts -->
    <!-- - `top-0` & `end-0` to position the toasts in the upper right corner -->
    <!-- - `.p-3` to prevent the toasts from sticking to the edge of the container  -->
    <div ref="toastDivs" class="toast-container top-0 end-0 p-3">
      <!-- Then put toasts within -->
      <div
        v-for="notification in notificationStore.notifications"
        class="toast"
        role="alert"
        aria-live="assertive"
        aria-atomic="true">
        <div class="toast-header">
          <strong class="me-auto">Bootstrap</strong>
          <small class="text-body-secondary">just now</small>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="toast"
            aria-label="Close"></button>
        </div>
        <div class="toast-body">{{ notification.message }}</div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
