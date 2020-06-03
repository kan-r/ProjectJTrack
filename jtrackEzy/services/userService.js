import { authService } from './authService.js';
import { axiosx } from './axiosx.js';

const BASE_URL = authService.getBaseUrl();

class UserService {

    getUserList(){
        return axiosx.get(BASE_URL + '/user', authService.getHttpOptions());
    }

    getUser(userId){
        return axiosx.get(BASE_URL + `/user/${userId}`, authService.getHttpOptions());
    }

    addUser(user){
        //validation
        if(user.userId === ''){
            return Promise.reject('User ID is required');
        }

        //validation
        if(user.pword === ''){
            return Promise.reject('Password is required');
        }

        user.userCrt = authService.getAppUser();
        return axiosx.post(BASE_URL + '/user', user, authService.getHttpOptions());
    }

    updateUser(user){
        //validation
        if(user.pword === ''){
            return Promise.reject('Password is required');
        }

        user.userMod = authService.getAppUser();
        return axiosx.put(BASE_URL + '/user', user, authService.getHttpOptions());
    }

    deleteUser(userId){
        return axiosx.delete(BASE_URL + `/user/${userId}`, authService.getHttpOptions());
    }
}

export let userService = new UserService();
