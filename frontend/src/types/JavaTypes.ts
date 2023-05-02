export class User {
  id: Number;
  username: String;
}

export class Group {
  id: Number;
  name: String;
  owner: User;
  createdAt: Date;
  participants: User[];
}

export class ApiError {
  message: string;
  timestampe: Date;
}

export class RegistrationEvent {
  id: number;
  name: string;
  createdAt: Date;
  startTime: Date;
  endTime: Date;
  registrars: User[];
  owner: User;
  group: Group;
}
