const dbService = require('./dbService');
const invalidData = require('./errorService');

class JobTypeService {

    getJobTypes(){
        return dbService.getDB()
            .collection('jobTypes')
            .find({})
            .sort({_id: 1})
            .toArray();
    }

    getJobType(id){
        return dbService.getDB()
            .collection('jobTypes')
            .findOne({_id: id});
    }

    async addJobType(jobType){
        if(!jobType._id){
            return Promise.reject(invalidData('Job Type (_id) is required'));
        }

        const jType = await this.getJobType(jobType._id);
        if(jType){
            return Promise.reject(invalidData('Job Type already exists'));
        }

        jobType.dateCrt = new Date();
        jobType.userCrt = "";

        const res = await dbService.getDB()
            .collection('jobTypes')
            .insertOne(jobType);

        return res.ops[0];
    }

    async updateJobType(id, jobType){
       
        const jType = await this.getJobType(id);
        if(!jType){
            return Promise.reject(invalidData('Job Type does not exist'));
        }

        if(jobType.hasOwnProperty('_id')){
            jobType._id = id;
        }

        jobType.dateMod = new Date();
        jobType.userMod = "";

        const res = await dbService.getDB()
            .collection('jobTypes')
            .findOneAndUpdate(
                {_id: id},
                { $set: jobType },
                {returnOriginal: false}
            );

        return res.value;
    }

    async deleteJobType(id){
        const jType = await this.getJobType(id);
        if(!jType){
            return Promise.reject(invalidData('JobType does not exist'));
        }

        const res = await dbService.getDB()
            .collection('jobTypes')
            .deleteOne({_id: id});

        return null;
    }
}

const jobTypeService = new JobTypeService();

module.exports = jobTypeService;