export type Notification = {
  notificationType: NotificationType;
  message: string;
};

export enum NotificationType {
  ERROR,
  SUCCESS
}

export class CreateRegistrationEventRequest {
  name: string;
  startTime: Date;
  endTime: Date;
}
