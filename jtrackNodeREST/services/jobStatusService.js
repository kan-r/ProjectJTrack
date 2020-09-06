const dbService = require('./dbService');
const invalidData = require('./errorService');

class JobStatusService {

    getJobStatuses(){
        return dbService.getDB()
            .collection('jobStatuses')
            .find({})
            .sort({_id: 1})
            .toArray();
    }

    getJobStatus(id){
        return dbService.getDB()
            .collection('jobStatuses')
            .findOne({_id: id});
    }

    async addJobStatus(jobStatus){
        if(!jobStatus._id){
            return Promise.reject(invalidData('Job Status (_id) is required'));
        }

        const jStatus = await this.getJobStatus(jobStatus._id);
        if(jStatus){
            return Promise.reject(invalidData('Job Status already exists'));
        }

        jobStatus.dateCrt = new Date();
        jobStatus.userCrt = "";

        const res = await dbService.getDB()
            .collection('jobStatuses')
            .insertOne(jobStatus);

        return res.ops[0];
    }

    async updateJobStatus(id, jobStatus){
       
        const jStatus = await this.getJobStatus(id);
        if(!jStatus){
            return Promise.reject(invalidData('Job Status does not exist'));
        }

        if(jobStatus.hasOwnProperty('_id')){
            jobStatus._id = id;
        }

        jobStatus.dateMod = new Date();
        jobStatus.userMod = "";

        const res = await dbService.getDB()
            .collection('jobStatuses')
            .findOneAndUpdate(
                {_id: id},
                { $set: jobStatus },
                {returnOriginal: false}
            );

        return res.value;
    }

    async deleteJobStatus(id){
        const jStatus = await this.getJobStatus(id);
        if(!jStatus){
            return Promise.reject(invalidData('JobStatus does not exist'));
        }

        const res = await dbService.getDB()
            .collection('jobStatuses')
            .deleteOne({_id: id});

        return null;
    }
}

const jobStatusService = new JobStatusService();

module.exports = jobStatusService;