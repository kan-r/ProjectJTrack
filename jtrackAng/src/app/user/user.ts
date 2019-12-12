export class User {
    userId: string;
    firstName: string;
    lastName: string;
    active: boolean;
    dateCrt: Date;
    userCrt: string;
    dateMod: Date;
    userMod: string;
    userCrtObj: {userId: string; firstName: string; lastName: string;};
    userModObj: {userId: string; firstName: string; lastName: string;};
    isAdmin: boolean;
}
