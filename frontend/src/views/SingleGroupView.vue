<script setup lang="ts">
import { useRouter, useRoute } from "vue-router";

const route = useRoute();
const groupId = route.params.groupId;
</script>

<template>
  <main>
    <h1>{{ groupId }}</h1>
  </main>
</template>

<style scoped lang="scss"></style>
