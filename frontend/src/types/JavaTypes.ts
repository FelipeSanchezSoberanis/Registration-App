export class User {
  id: Number;
  username: String;
};

export class Group {
  id: Number;
  name: String;
  owner: User;
  createdAt: Date;
  participants: User[];
};

export class ApiError {
  message: string;
  timestampe: Date;
};
