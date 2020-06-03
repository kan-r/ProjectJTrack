import { authService } from './authService.js';
import { axiosx } from './axiosx.js';

const BASE_URL = authService.getBaseUrl();

class TimesheetCodeService {

    getTimesheetCodeList(){
        return axiosx.get(BASE_URL + '/timesheetCode', authService.getHttpOptions());
    }

    getTimesheetCode(timesheetCode){
        return axiosx.get(BASE_URL + `/timesheetCode/${timesheetCode}`, authService.getHttpOptions());
    }

    addTimesheetCode(timesheetCode){
        //validation
        if(timesheetCode.timesheetCode === ''){
            return Promise.reject('Timesheet Code is required');
        }

        timesheetCode.userCrt = authService.getAppUser();
        return axiosx.post(BASE_URL + '/timesheetCode', timesheetCode, authService.getHttpOptions());
    }

    updateTimesheetCode(timesheetCode){
        timesheetCode.userMod = authService.getAppUser();
        return axiosx.put(BASE_URL + '/timesheetCode', timesheetCode, authService.getHttpOptions());
    }

    deleteTimesheetCode(timesheetCode){
        return axiosx.delete(BASE_URL + `/timesheetCode/${timesheetCode}`, authService.getHttpOptions());
    }
}

export let timesheetCodeService = new TimesheetCodeService();
