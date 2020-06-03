import { authService } from './authService.js';
import { axiosx } from './axiosx.js';

const BASE_URL = authService.getBaseUrl();

class JobTypeService {

    getJobTypeList(){
        return axiosx.get(BASE_URL + '/jobType', authService.getHttpOptions());
    }

    getJobType(jobType){
        return axiosx.get(BASE_URL + `/jobType/${jobType}`, authService.getHttpOptions());
    }

    addJobType(jobType){
        //validation
        if(jobType.jobType === ''){
            return Promise.reject('Job Type is required');
        }

        jobType.userCrt = authService.getAppUser();
        return axiosx.post(BASE_URL + '/jobType', jobType, authService.getHttpOptions());
    }

    updateJobType(jobType){
        jobType.userMod = authService.getAppUser();
        return axiosx.put(BASE_URL + '/jobType', jobType, authService.getHttpOptions());
    }

    deleteJobType(jobType){
        return axiosx.delete(BASE_URL + `/jobType/${jobType}`, authService.getHttpOptions());
    }
}

export let jobTypeService = new JobTypeService();
