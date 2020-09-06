const dbService = require('./dbService');
const invalidData = require('./errorService');

class JobResolutionService {

    getJobResolutions(){
        return dbService.getDB()
            .collection('jobResolutions')
            .find({})
            .sort({_id: 1})
            .toArray();
    }

    getJobResolution(id){
        return dbService.getDB()
            .collection('jobResolutions')
            .findOne({_id: id});
    }

    async addJobResolution(jobResolution){
        if(!jobResolution._id){
            return Promise.reject(invalidData('Job Resolution (_id) is required'));
        }

        const jResolution = await this.getJobResolution(jobResolution._id);
        if(jResolution){
            return Promise.reject(invalidData('Job Resolution already exists'));
        }

        jobResolution.dateCrt = new Date();
        jobResolution.userCrt = "";

        const res = await dbService.getDB()
            .collection('jobResolutions')
            .insertOne(jobResolution);

        return res.ops[0];
    }

    async updateJobResolution(id, jobResolution){
       
        const jResolution = await this.getJobResolution(id);
        if(!jResolution){
            return Promise.reject(invalidData('Job Resolution does not exist'));
        }

        if(jobResolution.hasOwnProperty('_id')){
            jobResolution._id = id;
        }

        jobResolution.dateMod = new Date();
        jobResolution.userMod = "";

        const res = await dbService.getDB()
            .collection('jobResolutions')
            .findOneAndUpdate(
                {_id: id},
                { $set: jobResolution },
                {returnOriginal: false}
            );

        return res.value;
    }

    async deleteJobResolution(id){
        const jResolution = await this.getJobResolution(id);
        if(!jResolution){
            return Promise.reject(invalidData('JobResolution does not exist'));
        }

        const res = await dbService.getDB()
            .collection('jobResolutions')
            .deleteOne({_id: id});

        return null;
    }
}

const jobResolutionService = new JobResolutionService();

module.exports = jobResolutionService;