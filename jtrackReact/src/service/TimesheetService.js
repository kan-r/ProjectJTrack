import axios from 'axios';
import AuthService from './AuthService';

const BASE_URL = AuthService.getBaseUrl();

class TimesheetService {

    getTimesheetList(){
        return axios.get(BASE_URL + '/timesheet', AuthService.getHttpOptions());
    }

    getTimesheetList2(timesheetSO){
        return axios.post(BASE_URL + '/timesheet/SO', timesheetSO, AuthService.getHttpOptions());
    }

    getTimesheet(timesheetId){
        return axios.get(BASE_URL + `/timesheet/${timesheetId}`, AuthService.getHttpOptions());
    }

    addTimesheet(timesheet){
        timesheet.userCrt = AuthService.getAppUser();
        return axios.post(BASE_URL + '/timesheet', timesheet, AuthService.getHttpOptions());
    }

    updateTimesheet(timesheet){
        timesheet.userMod = AuthService.getAppUser();
        return axios.put(BASE_URL + '/timesheet', timesheet, AuthService.getHttpOptions());
    }

    deleteTimesheet(timesheetId){
        return axios.delete(BASE_URL + `/timesheet/${timesheetId}`, AuthService.getHttpOptions());
    }
}

export default new TimesheetService();