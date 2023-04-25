export type Notification = {
  notificationType: NotificationType;
  message: string;
};

export enum NotificationType {
  ERROR,
  SUCCESS
}
