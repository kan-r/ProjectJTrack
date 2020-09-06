const dbService = require('./dbService');
const invalidData = require('./errorService');

class TimesheetCodeService {

    getTimesheetCodes(){
        return dbService.getDB()
            .collection('timesheetCodes')
            .find({})
            .sort({_id: 1})
            .toArray();
    }

    getTimesheetCode(id){
        return dbService.getDB()
            .collection('timesheetCodes')
            .findOne({_id: id});
    }

    async addTimesheetCode(timesheetCode){
        if(!timesheetCode._id){
            return Promise.reject(invalidData('Timesheet Code (_id) is required'));
        }

        const jType = await this.getTimesheetCode(timesheetCode._id);
        if(jType){
            return Promise.reject(invalidData('Timesheet Code already exists'));
        }

        timesheetCode.dateCrt = new Date();
        timesheetCode.userCrt = "";

        const res = await dbService.getDB()
            .collection('timesheetCodes')
            .insertOne(timesheetCode);

        return res.ops[0];
    }

    async updateTimesheetCode(id, timesheetCode){
       
        const jType = await this.getTimesheetCode(id);
        if(!jType){
            return Promise.reject(invalidData('Timesheet Code does not exist'));
        }

        if(timesheetCode.hasOwnProperty('_id')){
            timesheetCode._id = id;
        }

        timesheetCode.dateMod = new Date();
        timesheetCode.userMod = "";

        const res = await dbService.getDB()
            .collection('timesheetCodes')
            .findOneAndUpdate(
                {_id: id},
                { $set: timesheetCode },
                {returnOriginal: false}
            );

        return res.value;
    }

    async deleteTimesheetCode(id){
        const jType = await this.getTimesheetCode(id);
        if(!jType){
            return Promise.reject(invalidData('TimesheetCode does not exist'));
        }

        const res = await dbService.getDB()
            .collection('timesheetCodes')
            .deleteOne({_id: id});

        return null;
    }
}

const timesheetCodeService = new TimesheetCodeService();

module.exports = timesheetCodeService;