<script setup lang="ts">
import { RegistrationEvent } from "@/types/JavaTypes";
import { onMounted, ref } from "vue";
import type { Ref } from "vue";
import { useRoute } from "vue-router";
import { authenticatedApiService } from "../axios/index";
import { timeDiffFromNowToString } from "@/utils/DateTimeUtils";
import PopupComponent from "@/components/PopupComponent.vue";
import type { CreateRegistrationEventRequest } from "@/types/FrontendTypes";

const router = useRoute();
const groupId = Number(router.params.groupId);
const showCreateEventPopup: Ref<boolean> = ref(false);

const events: Ref<RegistrationEvent[]> = ref([]);

const defaultDate = new Date();

const defaultNewEventRequest: CreateRegistrationEventRequest = {
  name: "",
  startTime: defaultDate,
  endTime: defaultDate
};
const newEventRequest: Ref<CreateRegistrationEventRequest> = ref(defaultNewEventRequest);

async function test(): Promise<void> {
  const res = await authenticatedApiService.createNewRegistrationEvent(
    groupId,
    newEventRequest.value
  );
  events.value.unshift(res as RegistrationEvent);

  newEventRequest.value = defaultNewEventRequest;
  showCreateEventPopup.value = false;
}

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
    <div class="row justify-content-end mb-4">
      <div class="col-auto">
        <button class="btn btn-primary" @click="showCreateEventPopup = true">
          Create new registration event
        </button>
      </div>
    </div>
    <RouterLink class="card p-4 mb-3" v-for="event in events" :to="{ path: '/' }">
      <div class="row">
        <div class="col">{{ event.name }}</div>
        <div class="col">Owner: {{ event.owner.username }}</div>
        <div class="col-auto">{{ timeDiffFromNowToString(event.createdAt) }}</div>
      </div>
    </RouterLink>

    <PopupComponent :condition="showCreateEventPopup">
      <div class="row mb-4">
        <div class="col">
          <h1>Create new registration event</h1>
        </div>
      </div>
      <div class="row justify-content-center mb-4">
        <label class="col-auto col-form-label">New registration event's name:</label>
        <input class="col form-control" type="text" v-model="newEventRequest.name" />
      </div>
      <div class="row justify-content-center mb-4">
        <label class="col-auto col-form-label">Start date time</label>
        <input class="col form-control" type="datetime-local" v-model="newEventRequest.startTime" />
      </div>
      <div class="row justify-content-center mb-4">
        <label class="col-auto col-form-label">End date time</label>
        <input class="col form-control" type="datetime-local" v-model="newEventRequest.endTime" />
      </div>
      <div class="row justify-content-center">
        <div class="col-auto">
          <button
            class="btn btn-success"
            @click="test"
            :class="{
              disabled:
                !newEventRequest.name.length &&
                newEventRequest.startTime === defaultDate &&
                newEventRequest.endTime === defaultDate
            }">
            Create
          </button>
        </div>
        <div class="col-auto">
          <button class="btn btn-danger" @click="showCreateEventPopup = false">Cancel</button>
        </div>
      </div>
    </PopupComponent>
  </main>
</template>

<style scoped lang="scss"></style>
