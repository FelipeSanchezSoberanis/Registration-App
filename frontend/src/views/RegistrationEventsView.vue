<script setup lang="ts">
import type { RegistrationEvent } from "@/types/JavaTypes";
import { onMounted, ref } from "vue";
import type { Ref } from "vue";
import { useRoute } from "vue-router";
import { authenticatedApiService } from "../axios/index";
import { timeDiffFromNowToString } from "@/utils/DateTimeUtils";

const router = useRoute();
const groupId = Number(router.params.groupId);

const events: Ref<RegistrationEvent[]> = ref([]);

onMounted(() => {
  authenticatedApiService.getRegistrationEventsForGroup(groupId).then((data) => {
    if (data instanceof Array<RegistrationEvent>) {
      events.value = data;
    }
  });
});
</script>

<template>
  <main class="container pt-3">
    <div class="row mb-4">
      <div class="col text-center">
        <h1>Registration events</h1>
      </div>
    </div>
    <!-- <div class="row justify-content-end mb-4">
      <div class="col-auto">
        <button class="btn btn-primary" @click="showCreateGroupPopup = true">
          Create new group
        </button>
      </div>
</div> -->
    <RouterLink class="card p-4 mb-3" v-for="event in events" :to="{ path: '/' }">
      <div class="row">
        <div class="col">{{ event.name }}</div>
        <div class="col">Owner: {{ event.owner.username }}</div>
        <div class="col-auto">{{ timeDiffFromNowToString(event.createdAt) }}</div>
      </div>
    </RouterLink>
  </main>
</template>

<style scoped lang="scss"></style>
