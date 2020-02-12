import axios from 'axios';
import AuthService from './AuthService';

const BASE_URL = AuthService.getBaseUrl();

class JobResolutionService {

    getJobResolutionList(){
        return axios.get(BASE_URL + '/jobResolution', AuthService.getHttpOptions());
    }

    getJobResolution(jobResolution){
        return axios.get(BASE_URL + `/jobResolution/${jobResolution}`, AuthService.getHttpOptions());
    }

    addJobResolution(jobResolution){
        jobResolution.userCrt = AuthService.getAppUser();
        return axios.post(BASE_URL + '/jobResolution', jobResolution, AuthService.getHttpOptions());
    }

    updateJobResolution(jobResolution){
        jobResolution.userMod = AuthService.getAppUser();
        return axios.put(BASE_URL + '/jobResolution', jobResolution, AuthService.getHttpOptions());
    }

    deleteJobResolution(jobResolution){
        return axios.delete(BASE_URL + `/jobResolution/${jobResolution}`, AuthService.getHttpOptions());
    }
}

export default new JobResolutionService();