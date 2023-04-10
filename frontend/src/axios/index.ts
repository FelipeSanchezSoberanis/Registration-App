import axios from "axios";
import type { AxiosResponse } from "axios";
import { useAuthStore } from "../stores/auth";
import type { LoginRequest, LoginRequestResponse } from "../types/AuthTypes";
import router from "../router/index";

const publicApi = axios.create();
const authenticatedApi = axios.create();

publicApi.defaults.baseURL = "http://localhost:8080";
authenticatedApi.defaults.baseURL = publicApi.defaults.baseURL;

export const publicApiService = {
  login: async (loginRequest: LoginRequest): Promise<void> => {
    const res: AxiosResponse<LoginRequestResponse> = await publicApi.post("/login", loginRequest);

    if (res.status !== 200) return;

    const authStore = useAuthStore();
    authStore.refreshToken = res.data.refreshToken;
    authStore.isLoggedIn = true;

    authenticatedApi.defaults.headers["Authorization"] = `Bearer ${res.data.accessToken}`;

    router.push({ path: "/groups" });
  }
};
