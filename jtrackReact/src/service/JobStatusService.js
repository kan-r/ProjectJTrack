import axios from 'axios';
import AuthService from './AuthService';

const BASE_URL = AuthService.getBaseUrl();

class JobStatusService {

    getJobStatusList(){
        return axios.get(BASE_URL + '/jobStatus', AuthService.getHttpOptions());
    }

    getJobStatus(jobStatus){
        return axios.get(BASE_URL + `/jobStatus/${jobStatus}`, AuthService.getHttpOptions());
    }

    addJobStatus(jobStatus){
        jobStatus.userCrt = AuthService.getAppUser();
        return axios.post(BASE_URL + '/jobStatus', jobStatus, AuthService.getHttpOptions());
    }

    updateJobStatus(jobStatus){
        jobStatus.userMod = AuthService.getAppUser();
        return axios.put(BASE_URL + '/jobStatus', jobStatus, AuthService.getHttpOptions());
    }

    deleteJobStatus(jobStatus){
        return axios.delete(BASE_URL + `/jobStatus/${jobStatus}`, AuthService.getHttpOptions());
    }
}

export default new JobStatusService();