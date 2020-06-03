import { authService } from './authService.js';
import { axiosx } from './axiosx.js';

const BASE_URL = authService.getBaseUrl();

class JobService {

    getJobList(){
        return axiosx.get(BASE_URL + '/job', authService.getHttpOptions());
    }

    getJobList2(jobSO){
        return axiosx.post(BASE_URL + '/job/SO', jobSO, authService.getHttpOptions());
    }

    getParentJobList(){
        return axiosx.get(BASE_URL + '/parentJob', authService.getHttpOptions());
    }

    getJob(jobNo){
        return axiosx.get(BASE_URL + `/job/${jobNo}`, authService.getHttpOptions());
    }

    addJob(job){
        //validation
        if(job.jobName === ''){
            return Promise.reject('Job Name  is required');
        }
    
        job.userCrt = authService.getAppUser();
        return axiosx.post(BASE_URL + '/job', job, authService.getHttpOptions());
    }

    updateJob(job){
        //validation
        if(job.jobName === ''){
            return Promise.reject('Job Name  is required');
        }
   
        job.userMod = authService.getAppUser();
        return axiosx.put(BASE_URL + '/job', job, authService.getHttpOptions());
    }

    deleteJob(jobNo){
        return axiosx.delete(BASE_URL + `/job/${jobNo}`, authService.getHttpOptions());
    }
}

export let jobService = new JobService();
