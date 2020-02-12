import axios from 'axios';
import AuthService from './AuthService';

const BASE_URL = AuthService.getBaseUrl();

class JobPriorityService {

    getJobPriorityList(){
        return axios.get(BASE_URL + '/jobPriority', AuthService.getHttpOptions());
    }

    getJobPriority(jobPriority){
        return axios.get(BASE_URL + `/jobPriority/${jobPriority}`, AuthService.getHttpOptions());
    }

    addJobPriority(jobPriority){
        jobPriority.userCrt = AuthService.getAppUser();
        return axios.post(BASE_URL + '/jobPriority', jobPriority, AuthService.getHttpOptions());
    }

    updateJobPriority(jobPriority){
        jobPriority.userMod = AuthService.getAppUser();
        return axios.put(BASE_URL + '/jobPriority', jobPriority, AuthService.getHttpOptions());
    }

    deleteJobPriority(jobPriority){
        return axios.delete(BASE_URL + `/jobPriority/${jobPriority}`, AuthService.getHttpOptions());
    }
}

export default new JobPriorityService();