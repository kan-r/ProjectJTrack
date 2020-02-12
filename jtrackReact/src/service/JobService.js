import axios from 'axios';
import AuthService from './AuthService';

const BASE_URL = AuthService.getBaseUrl();

class JobService {

    getJobList(){
        return axios.get(BASE_URL + '/job', AuthService.getHttpOptions());
    }

    getJobList2(jobSO){
        return axios.post(BASE_URL + '/job/SO', jobSO, AuthService.getHttpOptions());
    }

    getParentJobList(){
        return axios.get(BASE_URL + '/parentJob', AuthService.getHttpOptions());
    }

    getJob(jobNo){
        return axios.get(BASE_URL + `/job/${jobNo}`, AuthService.getHttpOptions());
    }

    addJob(job){
        job.userCrt = AuthService.getAppUser();
        return axios.post(BASE_URL + '/job', job, AuthService.getHttpOptions());
    }

    updateJob(job){
        job.userMod = AuthService.getAppUser();
        return axios.put(BASE_URL + '/job', job, AuthService.getHttpOptions());
    }

    deleteJob(jobNo){
        return axios.delete(BASE_URL + `/job/${jobNo}`, AuthService.getHttpOptions());
    }
}

export default new JobService();