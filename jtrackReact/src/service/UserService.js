import axios from 'axios';
import AuthService from './AuthService';

const BASE_URL = AuthService.getBaseUrl();

class UserService {

    getUserList(){
        return axios.get(BASE_URL + '/user', AuthService.getHttpOptions());
    }

    getUser(userId){
        return axios.get(BASE_URL + `/user/${userId}`, AuthService.getHttpOptions());
    }

    addUser(user){
        user.userCrt = AuthService.getAppUser();
        return axios.post(BASE_URL + '/user', user, AuthService.getHttpOptions());
    }

    updateUser(user){
        user.userMod = AuthService.getAppUser();
        return axios.put(BASE_URL + '/user', user, AuthService.getHttpOptions());
    }

    deleteUser(userId){
        return axios.delete(BASE_URL + `/user/${userId}`, AuthService.getHttpOptions());
    }
}

export default new UserService();