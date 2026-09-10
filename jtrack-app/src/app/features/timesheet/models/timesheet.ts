export interface Timesheet {
  id: number;
  userId: string;
  userName: string;
  jobId: number;
  jobName: string;
  workedDate: string;
  workedHours: number;
}
