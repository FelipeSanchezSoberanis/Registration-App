import moment from "moment";

export function timeDiffFromNowToString(from: Date): String {
  return moment(from).fromNow();
}
