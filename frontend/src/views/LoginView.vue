<script setup lang="ts">
import { reactive } from "vue";
import type { LoginRequest } from "../types/AuthTypes";
import { publicApiService } from "../axios";
import { useAuthStore } from "@/stores/auth";
import { useRouter } from "vue-router";

const authStore = useAuthStore();
const router = useRouter();

const loginRequest: LoginRequest = reactive({
  username: "",
  password: ""
});

async function login() {
  await publicApiService.login(loginRequest);
  if (authStore.isLoggedIn) router.push({ path: "/groups" });
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
            class="btn btn-primary"
            @click="login">
            Submit
          </button>
        </div>
      </div>
    </div>
  </main>
</template>

<style scoped lang="scss">
.card {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
</style>
