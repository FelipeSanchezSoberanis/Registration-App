import axios from "axios";
import type { AxiosResponse } from "axios";
import { useAuthStore } from "../stores/auth";
import type { LoginRequest, LoginRequestResponse } from "../types/AuthTypes";
import type { Group, ApiError, RegistrationEvent } from "../types/JavaTypes";
import router from "../router/index";
import type { CreateRegistrationEventRequest } from "@/types/FrontendTypes";

const publicApi = axios.create();
const authenticatedApi = axios.create();

const axiosValidateStatus = (_status: number) => true;

publicApi.defaults.baseURL = "http://localhost:8080";
publicApi.defaults.validateStatus = axiosValidateStatus;

authenticatedApi.defaults.baseURL = publicApi.defaults.baseURL;
authenticatedApi.defaults.validateStatus = axiosValidateStatus;

export const publicApiService = {
  login: async (loginRequest: LoginRequest): Promise<void> => {
    const res: AxiosResponse<LoginRequestResponse> = await publicApi.post("/login", loginRequest);

    if (res.status !== 200) return;

    authenticatedApi.defaults.headers["Authorization"] = `Bearer ${res.data.accessToken}`;
    localStorage.setItem("refreshToken", res.data.refreshToken);

    const authStore = useAuthStore();
    authStore.isLoggedIn = true;
  },
  getNewAccessToken: async (): Promise<void> => {
    const authStore = useAuthStore();
    authStore.isLoggedIn = false;

    const refreshToken = localStorage.getItem("refreshToken");

    if (!refreshToken) {
      authenticatedApi.defaults.headers["Authorization"] = null;
      router.push({ path: "/login" });
      return;
    }

    const res: AxiosResponse<LoginRequestResponse> = await publicApi.post("/refreshToken", {
      refreshToken
    });

    if (res.status !== 200) {
      authenticatedApi.defaults.headers["Authorization"] = null;
      localStorage.removeItem("refreshToken");
      router.push({ path: "/login" });
      return;
    }

    authenticatedApi.defaults.headers["Authorization"] = `Bearer ${res.data.accessToken}`;

    authStore.isLoggedIn = true;
  }
};

export const authenticatedApiService = {
  getGroups: async (): Promise<Group[]> => {
    const res: AxiosResponse<Group[]> = await authenticatedApi.get("/groups");

    if (res.status !== 200) return [];

    return res.data;
  },
  createNewGroup: async (newGroupName: string): Promise<Group | ApiError> => {
    const res: AxiosResponse<Group | ApiError> = await authenticatedApi.post("/groups", {
      name: newGroupName
    });
    return res.data;
  },
  getRegistrationEventsForGroup: async (
    groupId: number
  ): Promise<RegistrationEvent[] | ApiError> => {
    const res: AxiosResponse<RegistrationEvent[] | ApiError> = await authenticatedApi.get(
      `/groups/${groupId}/registrationEvents`
    );
    return res.data;
  },
  createNewRegistrationEvent: async (
    groupId: number,
    newEvent: CreateRegistrationEventRequest
  ): Promise<RegistrationEvent | ApiError> => {
    newEvent.startTime = new Date(newEvent.startTime.toString());
    newEvent.endTime = new Date(newEvent.endTime.toString());

    const res: AxiosResponse<RegistrationEvent | ApiError> = await authenticatedApi.post(
      `/groups/${groupId}/registrationEvents`,
      {
        ...newEvent,
        startTime: newEvent.startTime.toISOString(),
        endTime: newEvent.endTime.toISOString()
      }
    );

    return res.data;
  }
};
