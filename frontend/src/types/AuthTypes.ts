export type LoginRequest = {
  username: string;
  password: string;
};

export type LoginRequestResponse = {
  accessToken: string;
  refreshToken: string;
};
