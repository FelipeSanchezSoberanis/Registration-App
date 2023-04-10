<script setup lang="ts">
import { onMounted, ref } from "vue";
import type { Ref } from "vue";
import type { Group } from "../types/JavaTypes";
import { authenticatedApiService } from "../axios/index";

const groups: Ref<Group[]> = ref([]);

onMounted(() => {
  authenticatedApiService.getGroups().then((_groups) => {
    groups.value = _groups;
  });
});
</script>

<template>
  <main class="container pt-3">
    <div class="row">
      <div class="col text-center">
        <h1>Groups</h1>
      </div>
    </div>
    <div class="card row" v-for="group in groups">
      <div class="col">
        {{ group.name }}
      </div>
    </div>
  </main>
</template>

<style scoped lang="scss"></style>
