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

    authenticatedApi.defaults.headers["Authorization"] = `Bearer ${res.data.accessToken}`;
    localStorage.setItem("refreshToken", res.data.refreshToken);

    const authStore = useAuthStore();
    authStore.isLoggedIn = true;

    router.push({ path: "/groups" });
  },
  getNewAccessToken: async (): Promise<void> => {
    const authStore = useAuthStore();
    authStore.isLoggedIn = false;

    const refreshToken = localStorage.getItem("refreshToken");

    if (!refreshToken) return;

    const res: AxiosResponse<LoginRequestResponse> = await publicApi.post("/refreshToken", {
      refreshToken
    });

    if (res.status !== 200) return;

    authenticatedApi.defaults.headers["Authorization"] = `Bearer ${res.data.accessToken}`;

    authStore.isLoggedIn = true;
  }
};
  }
};