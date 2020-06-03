import { $ezy } from '../ezy_modules/ezy.js';
import { jobService } from './jobService.js';
import { jobTypeService } from './jobTypeService.js';
import { jobStatusService } from './jobStatusService.js';
import { jobStageService } from './jobStageService.js';
import { jobPriorityService } from './jobPriorityService.js';
import { jobResolutionService } from './jobResolutionService.js';
import { timesheetService } from './timesheetService.js';
import { timesheetCodeService } from './timesheetCodeService.js';
import { userService } from './userService.js';

class GenService {

    loadJobList(){
        jobService.getJobList()
            .then(res => {
                $ezy.model.message.reset();
                $ezy.model.jobList = res;
            })
            .catch(err => {
                $ezy.model.message.setError(err);
            });
    }

    loadJobList2(){
        jobService.getJobList2($ezy.model.jobSO)
            .then(res => {
                $ezy.model.message.reset();
                $ezy.model.jobList2 = res;
            })
            .catch(err => {
                $ezy.model.message.setError(err);
            });
    }

    loadParentJobList(){
        jobService.getParentJobList()
            .then(res => {
                $ezy.model.message.reset();
                $ezy.model.parentJobList = res;
            })
            .catch(err => {
                $ezy.model.message.setError(err);
            });
    }

    loadJobTypeList(){
        jobTypeService.getJobTypeList()
            .then(res => {
                $ezy.model.message.reset();
                $ezy.model.jobTypeList = res;
            })
            .catch(err => {
                $ezy.model.message.setError(err);
            });
    }

    loadStatusList(){
        jobStatusService.getJobStatusList()
            .then(res => {
                $ezy.model.message.reset();
                $ezy.model.jobStatusList = res;
            })
            .catch(err => {
                $ezy.model.message.setError(err);
            });
    }

    loadJobStageList(){
        jobStageService.getJobStageList()
            .then(res => {
                $ezy.model.message.reset();
                $ezy.model.jobStageList = res;
            })
            .catch(err => {
                $ezy.model.message.setError(err);
            });
    }

    loadJobPriorityList(){
        jobPriorityService.getJobPriorityList()
            .then(res => {
                $ezy.model.message.reset();
                $ezy.model.jobPriorityList = res;
            })
            .catch(err => {
                $ezy.model.message.setError(err);
            });
    }

    loadJobResolutionList(){
        jobResolutionService.getJobResolutionList()
            .then(res => {
                $ezy.model.message.reset();
                $ezy.model.jobResolutionList = res;
            })
            .catch(err => {
                $ezy.model.message.setError(err);
            });
    }

    loadTimesheetList(){
        timesheetService.getTimesheetList()
            .then(res => {
                $ezy.model.message.reset();
                $ezy.model.timesheetList = res;
            })
            .catch(err => {
                $ezy.model.message.setError(err);
            });
    }

    loadTimesheetList2(){
        timesheetService.getTimesheetList2($ezy.model.timesheetSO)
            .then(res => {
                $ezy.model.message.reset();
                $ezy.model.timesheetList = res;
            })
            .catch(err => {
                $ezy.model.message.setError(err);
            });
    }

    loadTimesheetCodeList(){
        timesheetCodeService.getTimesheetCodeList()
            .then(res => {
                $ezy.model.message.reset();
                $ezy.model.timesheetCodeList = res;
            })
            .catch(err => {
                $ezy.model.message.setError(err);
            });
    }

    loadUserList(){
        userService.getUserList()
            .then(res => {
                $ezy.model.message.reset();
                $ezy.model.userList = res;
            })
            .catch(err => {
                $ezy.model.message.setError(err);
            });
    }
}

export let genService = new GenService();
