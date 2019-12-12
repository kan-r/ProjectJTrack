import { User } from '../user/user';

export class JobStage {
    jobStage: string;
    jobStageDesc: string;
    active: boolean;
    dateCrt: Date;
    userCrt: string;
    dateMod: Date;
    userMod: string;
    userCrtObj: User;
    userModObj: User;
}
