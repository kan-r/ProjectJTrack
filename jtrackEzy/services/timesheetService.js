import { authService } from './authService.js';
import { axiosx } from './axiosx.js';

const BASE_URL = authService.getBaseUrl();

class TimesheetService {

    getTimesheetList(){
        return axiosx.get(BASE_URL + '/timesheet', authService.getHttpOptions());
    }

    getTimesheetList2(timesheetSO){
        return axiosx.post(BASE_URL + '/timesheet/SO', timesheetSO, authService.getHttpOptions());
    }

    getTimesheet(timesheetId){
        return axiosx.get(BASE_URL + `/timesheet/${timesheetId}`, authService.getHttpOptions());
    }

    addTimesheet(timesheet){
        //validation
        if(timesheet.userId === ''){
            return Promise.reject('User  is required');
        }

        //validation
        if(timesheet.jobNo === ''){
            return Promise.reject('Job  is required');
        }

        //validation
        if(timesheet.workedDate === ''){
            return Promise.reject('Worked Date is required');
        }

        timesheet.userCrt = authService.getAppUser();
        return axiosx.post(BASE_URL + '/timesheet', timesheet, authService.getHttpOptions());
    }

    updateTimesheet(timesheet){
        timesheet.userMod = authService.getAppUser();
        return axiosx.put(BASE_URL + '/timesheet', timesheet, authService.getHttpOptions());
    }

    deleteTimesheet(timesheetId){
        return axiosx.delete(BASE_URL + `/timesheet/${timesheetId}`, authService.getHttpOptions());
    }
}

export let timesheetService = new TimesheetService();
