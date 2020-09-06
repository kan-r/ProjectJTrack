const dbService = require('./dbService');
const seqGenService = require('./seqGenService');
const invalidData = require('./errorService');
const utils = require('./utils');

class JobService {

    async getJobs(queryParams){
        let query = {};

        if('name' in queryParams){
            const inclChild = utils.toBoolean(queryParams.includeChild);

            if(inclChild){
                if(!utils.isEmpty(queryParams.name)){
                    query.jobName = queryParams.name;
                }

                if(!utils.isEmpty(queryParams.type)){
                    query.jobType = queryParams.type;
                }

                if(!utils.isEmpty(queryParams.status)){
                    query.jobStatus = queryParams.status;
                }

                if(!utils.isEmpty(queryParams.assignedTo)){
                    query.assignedTo = queryParams.assignedTo;
                }

                let jobNos = [];
                const jobs = await this.getJobsByQuery(query);

                jobs.forEach(job => {
                    jobNos.push(job._id);
                });

                let queryC = {};

                if(!utils.isEmpty(queryParams.nameC)){
                    queryC.jobName = queryParams.nameC;
                }

                if(!utils.isEmpty(queryParams.typeC)){
                    queryC.jobType = queryParams.typeC;
                }

                if(!utils.isEmpty(queryParams.statusC)){
                    queryC.jobStatus = queryParams.statusC;
                }

                if(!utils.isEmpty(queryParams.assignedToC)){
                    queryC.assignedTo = queryParams.assignedToC;
                }

                query = {
                    $or: [
                        { _id: { $in: jobNos } },
                        { $and: [
                            { parentJobNo: { $in: jobNos } },
                            queryC
                        ] }
                    ]
                }
            }else{
                if(!utils.isEmpty(queryParams.name)){
                    query.jobName = queryParams.name;
                }

                if(!utils.isEmpty(queryParams.type)){
                    query.jobType = queryParams.type;
                }

                if(!utils.isEmpty(queryParams.status)){
                    query.jobStatus = queryParams.status;
                }

                if(!utils.isEmpty(queryParams.assignedTo)){
                    query.assignedTo = queryParams.assignedTo;
                }
            }

        } else if('type' in queryParams){
            query.jobType = queryParams.type;
        }

        return this.getJobsByQuery(query);
    }

    getJobsByQuery(query){
        return dbService.getDB()
            .collection('jobs')
            .find(query)
            .sort({_id: 1})
            .toArray();
    }

    getJob(id){
        const _id = parseInt(id);
        return dbService.getDB()
            .collection('jobs')
            .findOne({_id: _id});
    }

    async addJob(job){
       
        job._id = await seqGenService.getSequenceNextValue("job");

        job.dateCrt = new Date();
        job.userCrt = "";

        const res = await dbService.getDB()
            .collection('jobs')
            .insertOne(job);

        return res.ops[0];
    }

    async updateJob(id, job){

        const _id = parseInt(id);
       
        const j = await this.getJob(_id);
        if(!j){
            return Promise.reject(invalidData('Job  does not exist'));
        }

        if(job.hasOwnProperty('_id')){
            job._id = _id;
        }

        job.dateMod = new Date();
        job.userMod = "";

        const res = await dbService.getDB()
            .collection('jobs')
            .findOneAndUpdate(
                {_id: _id},
                { $set: job },
                {returnOriginal: false}
            );

        return res.value;
    }

    async deleteJob(id){

        const _id = parseInt(id);

        const j = await this.getJob(_id);
        if(!j){
            return Promise.reject(invalidData('Job does not exist'));
        }

        const res = await dbService.getDB()
            .collection('jobs')
            .deleteOne({_id: _id});

        return null;
    }
}

const jobService = new JobService();

module.exports = jobService;