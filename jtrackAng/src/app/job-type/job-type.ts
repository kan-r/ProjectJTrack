import { User } from '../user/user';

export class JobType {
    jobType: string;
    jobTypeDesc: string;
    active: boolean;
    dateCrt: Date;
    userCrt: string;
    dateMod: Date;
    userMod: string;
    userCrtObj: User;
    userModObj: User;
}
