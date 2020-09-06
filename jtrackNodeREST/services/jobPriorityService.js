const dbService = require('./dbService');
const invalidData = require('./errorService');

class JobPriorityService {

    getJobPriorities(){
        return dbService.getDB()
            .collection('jobPriorities')
            .find({})
            .sort({_id: 1})
            .toArray();
    }

    getJobPriority(id){
        return dbService.getDB()
            .collection('jobPriorities')
            .findOne({_id: id});
    }

    async addJobPriority(jobPriority){
        if(!jobPriority._id){
            return Promise.reject(invalidData('Job Priority (_id) is required'));
        }

        const jPriority = await this.getJobPriority(jobPriority._id);
        if(jPriority){
            return Promise.reject(invalidData('Job Priority already exists'));
        }

        jobPriority.dateCrt = new Date();
        jobPriority.userCrt = "";

        const res = await dbService.getDB()
            .collection('jobPriorities')
            .insertOne(jobPriority);

        return res.ops[0];
    }

    async updateJobPriority(id, jobPriority){
       
        const jPriority = await this.getJobPriority(id);
        if(!jPriority){
            return Promise.reject(invalidData('Job Priority does not exist'));
        }

        if(jobPriority.hasOwnProperty('_id')){
            jobPriority._id = id;
        }

        jobPriority.dateMod = new Date();
        jobPriority.userMod = "";

        const res = await dbService.getDB()
            .collection('jobPriorities')
            .findOneAndUpdate(
                {_id: id},
                { $set: jobPriority },
                {returnOriginal: false}
            );

        return res.value;
    }

    async deleteJobPriority(id){
        const jPriority = await this.getJobPriority(id);
        if(!jPriority){
            return Promise.reject(invalidData('JobPriority does not exist'));
        }

        const res = await dbService.getDB()
            .collection('jobPriorities')
            .deleteOne({_id: id});

        return null;
    }
}

const jobPriorityService = new JobPriorityService();

module.exports = jobPriorityService;