import { User } from '../user/user';

export class JobPriority {
    jobPriority: string;
    jobPriorityDesc: string;
    active: boolean;
    dateCrt: Date;
    userCrt: string;
    dateMod: Date;
    userMod: string;
    userCrtObj: User;
    userModObj: User;
}
