const dbService = require('./dbService');
const invalidData = require('./errorService');

class JobStageService {

    getJobStages(){
        return dbService.getDB()
            .collection('jobStages')
            .find({})
            .sort({_id: 1})
            .toArray();
    }

    getJobStage(id){
        return dbService.getDB()
            .collection('jobStages')
            .findOne({_id: id});
    }

    async addJobStage(jobStage){
        if(!jobStage._id){
            return Promise.reject(invalidData('Job Stage (_id) is required'));
        }

        const jStage = await this.getJobStage(jobStage._id);
        if(jStage){
            return Promise.reject(invalidData('Job Stage already exists'));
        }

        jobStage.dateCrt = new Date();
        jobStage.userCrt = "";

        const res = await dbService.getDB()
            .collection('jobStages')
            .insertOne(jobStage);

        return res.ops[0];
    }

    async updateJobStage(id, jobStage){
       
        const jStage = await this.getJobStage(id);
        if(!jStage){
            return Promise.reject(invalidData('Job Stage does not exist'));
        }

        if(jobStage.hasOwnProperty('_id')){
            jobStage._id = id;
        }

        jobStage.dateMod = new Date();
        jobStage.userMod = "";

        const res = await dbService.getDB()
            .collection('jobStages')
            .findOneAndUpdate(
                {_id: id},
                { $set: jobStage },
                {returnOriginal: false}
            );

        return res.value;
    }

    async deleteJobStage(id){
        const jStage = await this.getJobStage(id);
        if(!jStage){
            return Promise.reject(invalidData('JobStage does not exist'));
        }

        const res = await dbService.getDB()
            .collection('jobStages')
            .deleteOne({_id: id});

        return null;
    }
}

const jobStageService = new JobStageService();

module.exports = jobStageService;