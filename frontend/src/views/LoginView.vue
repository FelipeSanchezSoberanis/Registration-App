<script setup lang="ts">
import { reactive, ref } from "vue";
import type { Ref } from "vue";
import type { LoginRequest } from "../types/AuthTypes";
import { publicApiService } from "../axios";

const loginRequest: LoginRequest = reactive({
  username: "",
  password: "",
});
const correctlyLoggedIn: Ref<null | boolean> = ref(null);

async function login() {
  correctlyLoggedIn.value = await publicApiService.login(loginRequest);
}
</script>

<template>
  <main>
    <div class="card w-50">
      <div class="row text-center">
        <h1>Login</h1>
      </div>
      <div class="row text-center">
        <input
          class="w-75"
          type="text"
          name="username"
          v-model="loginRequest.username"
        />
      </div>
      <div class="row">
        <input
          type="password"
          name="password"
          v-model="loginRequest.password"
        />
      </div>
      <div class="row">
        <button @click="login" class="btn">Submit</button>
      </div>
    </div>
  </main>
</template>

<style lang="scss"></style>
