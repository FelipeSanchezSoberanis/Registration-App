<script setup lang="ts">
import { onMounted, ref } from "vue";
import type { Ref } from "vue";
import { ApiError, Group } from "../types/JavaTypes";
import { authenticatedApiService } from "../axios/index";
import { RouterLink } from "vue-router";
import { timeDiffFromNowToString } from "../utils/DateTimeUtils";
import PopupComponent from "../components/PopupComponent.vue";

const groups: Ref<Group[]> = ref([]);
const showCreateGroupPopup: Ref<boolean> = ref(false);
const newGroupName: Ref<string> = ref("");

async function createNewGroup() {
  const res = await authenticatedApiService.createNewGroup(newGroupName.value);

  if (res instanceof Group) {
    const newGroup = res as Group;
    groups.value.unshift(newGroup);
  } else if (res instanceof ApiError) {
    const error = res as ApiError;
    console.log(error);
  }

  showCreateGroupPopup.value = false;
  newGroupName.value = "";
}

onMounted(() => {
  authenticatedApiService.getGroups().then((data) => {
    groups.value = data;
  });
});
</script>

<template>
  <main class="container pt-3">
    <div class="row mb-4">
      <div class="col text-center">
        <h1>Groups</h1>
      </div>
    </div>
    <div class="row justify-content-end mb-4">
      <div class="col-auto">
        <button class="btn btn-primary" @click="showCreateGroupPopup = true">
          Create new group
        </button>
      </div>
    </div>
    <RouterLink class="card p-4 mb-3" v-for="group in groups" :to="{ path: `/groups/${group.id}` }">
      <div class="row">
        <div class="col">{{ group.name }}</div>
        <div class="col">Owner: {{ group.owner.username }}</div>
        <div class="col-auto">{{ timeDiffFromNowToString(group.createdAt) }}</div>
      </div>
    </RouterLink>

    <PopupComponent :condition="showCreateGroupPopup">
      <div class="row mb-4">
        <div class="col">
          <h1>Create new group</h1>
        </div>
      </div>
      <div class="row justify-content-center mb-4">
        <label class="col-auto col-form-label">New group's name:</label>
        <input class="col form-control" type="text" v-model="newGroupName" />
      </div>
      <div class="row justify-content-center">
        <div class="col-auto">
          <button
            :class="{ disabled: !newGroupName.length }"
            class="btn btn-success"
            @click="createNewGroup">
            Create
          </button>
        </div>
        <div class="col-auto">
          <button class="btn btn-danger" @click="showCreateGroupPopup = false">Cancel</button>
        </div>
      </div>
    </PopupComponent>
  </main>
</template>

<style scoped lang="scss">
a {
  text-decoration: none;
}
</style>
