import { authService } from './authService.js';
import { axiosx } from './axiosx.js';

const BASE_URL = authService.getBaseUrl();

class JobStatusService {

    getJobStatusList(){
        return axiosx.get(BASE_URL + '/jobStatus', authService.getHttpOptions());
    }

    getJobStatus(jobStatus){
        return axiosx.get(BASE_URL + `/jobStatus/${jobStatus}`, authService.getHttpOptions());
    }

    addJobStatus(jobStatus){
        //validation
        if(jobStatus.jobStatus === ''){
            return Promise.reject('Job Status is required');
        }

        jobStatus.userCrt = authService.getAppUser();
        return axiosx.post(BASE_URL + '/jobStatus', jobStatus, authService.getHttpOptions());
    }

    updateJobStatus(jobStatus){
        jobStatus.userMod = authService.getAppUser();
        return axiosx.put(BASE_URL + '/jobStatus', jobStatus, authService.getHttpOptions());
    }

    deleteJobStatus(jobStatus){
        return axiosx.delete(BASE_URL + `/jobStatus/${jobStatus}`, authService.getHttpOptions());
    }
}

export let jobStatusService = new JobStatusService();
