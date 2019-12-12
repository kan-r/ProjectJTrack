import { User } from '../user/user';

export class TimesheetCode {
    timesheetCode: string;
    timesheetCodeDesc: string;
    active: boolean;
    dateCrt: Date;
    userCrt: string;
    dateMod: Date;
    userMod: string;
    userCrtObj: User;
    userModObj: User;
}
