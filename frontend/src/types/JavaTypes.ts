export type User = {
  id: Number;
  username: String;
};

export type Group = {
  id: Number;
  name: String;
  owner: User;
  participants: User[];
};
