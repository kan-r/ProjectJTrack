import { authService } from './authService.js';
import { axiosx } from './axiosx.js';

const BASE_URL = authService.getBaseUrl();

class JobResolutionService {

    getJobResolutionList(){
        return axiosx.get(BASE_URL + '/jobResolutions', authService.getHttpOptions());
    }

    getJobResolution(jobResolution){
        return axiosx.get(BASE_URL + `/jobResolutions/${jobResolution}`, authService.getHttpOptions());
    }

    addJobResolution(jobResolution){
        //validation
        if(jobResolution.jobResolution === ''){
            return Promise.reject('Job Resolution is required');
        }

        jobResolution.userCrt = authService.getAppUser();
        return axiosx.post(BASE_URL + '/jobResolutions', jobResolution, authService.getHttpOptions());
    }

    updateJobResolution(jobResolution){
        jobResolution.userMod = authService.getAppUser();
        return axiosx.put(BASE_URL + `/jobResolutions/${jobResolution.jobResolution}`, jobResolution, authService.getHttpOptions());
    }

    deleteJobResolution(jobResolution){
        return axiosx.delete(BASE_URL + `/jobResolutions/${jobResolution}`, authService.getHttpOptions());
    }
}

export let jobResolutionService = new JobResolutionService();
