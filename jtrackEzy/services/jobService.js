import { authService } from './authService.js';
import { axiosx } from './axiosx.js';

const BASE_URL = authService.getBaseUrl();

class JobService {

    getJobList(){
        return axiosx.get(BASE_URL + '/jobs', authService.getHttpOptions());
    }

    getJobList2(jobSO){
        let url = `/jobs?name=${jobSO.jobName}&type=${jobSO.jobType}&status=${jobSO.jobStatus}&assignedTo=${jobSO.assignedTo}&includeChild=${jobSO.includeChildJobs}&nameC=${jobSO.jobNameChild}&typeC=${jobSO.jobTypeChild}&statusC=${jobSO.jobStatusChild}&assignedToC=${jobSO.assignedToChild}`;
        return axiosx.get(BASE_URL + url, authService.getHttpOptions());
    }

    getParentJobList(){
        return axiosx.get(BASE_URL + '/jobs?type=Project', authService.getHttpOptions());
    }

    getJob(jobNo){
        return axiosx.get(BASE_URL + `/jobs/${jobNo}`, authService.getHttpOptions());
    }

    addJob(job){
        //validation
        if(job.jobName === ''){
            return Promise.reject('Job Name  is required');
        }
    
        job.userCrt = authService.getAppUser();
        return axiosx.post(BASE_URL + '/jobs', job, authService.getHttpOptions());
    }

    updateJob(job){
        //validation
        if(job.jobName === ''){
            return Promise.reject('Job Name  is required');
        }
   
        job.userMod = authService.getAppUser();
        return axiosx.put(BASE_URL + `/jobs/${job.jobNo}`, job, authService.getHttpOptions());
    }

    deleteJob(jobNo){
        return axiosx.delete(BASE_URL + `/jobs/${jobNo}`, authService.getHttpOptions());
    }
}

export let jobService = new JobService();
