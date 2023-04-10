<script setup lang="ts">
import { onMounted, ref } from "vue";
import type { Ref } from "vue";
import type { Group } from "../types/JavaTypes";
import { authenticatedApiService } from "../axios/index";
import { RouterLink } from "vue-router";

const groups: Ref<Group[]> = ref([]);

onMounted(() => {
  authenticatedApiService.getGroups().then((_groups) => {
    groups.value = _groups;
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
    <RouterLink v-for="group in groups" :to="{ path: `/groups/${group.id}` }">
      <div class="card row p-3 mb-3">
        <div class="col">
          <h4>
            {{ group.name }}
          </h4>
        </div>
      </div>
    </RouterLink>
  </main>
</template>

<style scoped lang="scss">
a {
  text-decoration: none;
}
</style>
