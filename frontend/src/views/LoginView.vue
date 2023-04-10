<script setup lang="ts">
import { reactive, ref } from "vue";
import type { Ref } from "vue";
import type { LoginRequest } from "../types/AuthTypes";
import { publicApiService } from "../axios";

const loginRequest: LoginRequest = reactive({
  username: "",
  password: ""
});
const correctlyLoggedIn: Ref<null | boolean> = ref(null);

async function login() {
  correctlyLoggedIn.value = await publicApiService.login(loginRequest);
}
</script>

<template>
  <main>
    <div class="card w-50 p-5">
      <div class="row justify-content-center mb-3">
        <div class="col-auto">
          <h1>Login</h1>
        </div>
      </div>
      <div class="row justify-content-center mb-3">
        <div class="col-auto">
          <input
            placeholder="username"
            type="text"
            name="username"
            v-model="loginRequest.username" />
        </div>
      </div>
      <div class="row justify-content-center mb-3">
        <div class="col-auto">
          <input
            placeholder="password"
            type="password"
            name="password"
            v-model="loginRequest.password" />
        </div>
      </div>
      <div class="row justify-content-center mb-3">
        <div class="col-auto">
          <button
            :class="{ disabled: !loginRequest.username.length || !loginRequest.password.length }"
            class="btn btn-primary">
            Submit
          </button>
        </div>
      </div>
    </div>
  </main>
</template>

<style lang="scss"></style>
