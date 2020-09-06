const dbService = require('./dbService');
const invalidData = require('./errorService');
const { ObjectId } = require('mongodb');
const utils = require('./utils');

class TimesheetService {

    getTimesheets(queryParams){
        let query = {};
        let queryAnd = []

        if(!utils.isEmpty(queryParams.userId)){
            queryAnd.push({userId: queryParams.userId});
        }

        if(!utils.isEmpty(queryParams.workedDateFrom)){
            queryAnd.push({workedDate: {
                $gte: utils.toDate(queryParams.workedDateFrom)
            }});
        }

        if(!utils.isEmpty(queryParams.workedDateTo)){
            queryAnd.push({workedDate: {
                $lte: utils.toDate(queryParams.workedDateTo)
            }});
        }

        if(queryAnd.length > 0){
            query.$and = queryAnd;
        }

        console.log(query);

        return dbService.getDB()
            .collection('timesheets')
            .find(query)
            .sort({userId: 1, jobNo: 1, workedDate: 1})
            .toArray();
    }

    getTimesheet(id){
        const _id = ObjectId(id);
        return dbService.getDB()
            .collection('timesheets')
            .findOne({_id: _id});
    }

    async addTimesheet(timesheet){
    
        if(!timesheet.hasOwnProperty('userId') || utils.isEmpty(timesheet.userId)){
            return Promise.reject(invalidData('userId is required'));
        }

        if(!timesheet.hasOwnProperty('jobNo') || utils.isEmpty(timesheet.jobNo)){
            return Promise.reject(invalidData('jobNo is required'));
        }

        if(!timesheet.hasOwnProperty('workedDate') || utils.isEmpty(timesheet.workedDate)){
            return Promise.reject(invalidData('workedDate is required'));
        }

        timesheet.workedDate = utils.toDate(timesheet.workedDate);

        timesheet.dateCrt = new Date();
        timesheet.userCrt = "";

        const res = await dbService.getDB()
            .collection('timesheets')
            .insertOne(timesheet);

        this.refreshJob(timesheet.jobNo);

        return res.ops[0];
    }

    async updateTimesheet(id, timesheet){
       
        const tSheet = await this.getTimesheet(id);
        if(!tSheet){
            return Promise.reject(invalidData('Timesheet  does not exist'));
        }

        const _id = ObjectId(id);

        if(timesheet.hasOwnProperty('_id')){
            timesheet._id = _id;
        }

        timesheet.dateMod = new Date();
        timesheet.userMod = "";

        const res = await dbService.getDB()
            .collection('timesheets')
            .findOneAndUpdate(
                {_id: _id},
                { $set: timesheet },
                {returnOriginal: false}
            );

        this.refreshJob(timesheet.jobNo);

        return res.value;
    }

    async deleteTimesheet(id){

        const tSheet = await this.getTimesheet(id);
        if(!tSheet){
            return Promise.reject(invalidData('Timesheet does not exist'));
        }

        const _id = ObjectId(id);

        const res = await dbService.getDB()
            .collection('timesheets')
            .deleteOne({_id: _id});

        this.refreshJob(tSheet.jobNo);

        return null;
    }

    refreshJob(jobNo){
        console.log(`refreshJob(${jobNo})`);
    }
}

const timesheetService = new TimesheetService();

module.exports = timesheetService;