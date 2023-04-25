export type User = {
  id: Number;
  username: String;
};

export type Group = {
  id: Number;
  name: String;
  owner: User;
  createdAt: Date;
  participants: User[];
};

export type ApiError = {
  message: string;
  timestampe: Date;
};
