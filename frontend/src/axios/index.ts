import axios from "axios";
import type { AxiosResponse } from "axios";
import { useAuthStore } from "../stores/auth";
import type { LoginRequest, LoginRequestResponse } from "../types/AuthTypes";

const publicApi = axios.create();
const authenticatedApi = axios.create();

publicApi.defaults.baseURL = "http://localhost:8080";
authenticatedApi.defaults.baseURL = publicApi.defaults.baseURL;

export const publicApiService = {
  login: async (loginRequest: LoginRequest): Promise<boolean> => {
    const res: AxiosResponse<LoginRequestResponse> = await publicApi.post(
      "/login",
      loginRequest
    );

    if (res.status !== 200) return false;

    const authStore = useAuthStore();
    authStore.accessToken = res.data.accessToken;
    authStore.refreshToken = res.data.refreshToken;
    authStore.isLoggedIn = true;

    return true;
  },
};
