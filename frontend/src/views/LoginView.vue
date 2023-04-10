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
    <div class="card w-50">
      <div class="row justify-content-center">
        <div class="col-auto">
          <h1>Login</h1>
        </div>
      </div>
      <div class="row justify-content-center">
        <div class="col-auto">
          <input type="text" name="username" v-model="loginRequest.username" />
        </div>
      </div>
      <div class="row justify-content-center">
        <div class="col-auto">
          <input type="password" name="password" v-model="loginRequest.password" />
        </div>
      </div>
    </div>
  </main>
</template>

<style lang="scss"></style>
