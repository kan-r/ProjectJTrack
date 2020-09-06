const jobTypeRouter = require('express').Router();
const jobTypeService = require('../services/jobTypeService');

jobTypeRouter.get('/', (req, res) => {
    console.log('---------------------------------------------------------------');
    console.log('GET /jobTypes');

    jobTypeService.getJobTypes()
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

jobTypeRouter.get('/:id', (req, res) => {
    const id = req.params.id;

    console.log('---------------------------------------------------------------');
    console.log(`GET /jobTypes/${id}`);

    jobTypeService.getJobType(id)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

jobTypeRouter.post('/', (req, res) => {
    const jobType = req.body;

    console.log('---------------------------------------------------------------');
    console.log('POST /jobTypes -> jobType:', jobType);

    jobTypeService.addJobType(jobType)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

jobTypeRouter.put('/:id', (req,res) => {
    const id = req.params.id;
    const jobType = req.body;

    console.log('---------------------------------------------------------------');
    console.log(`PUT /jobTypes/${id} -> jobType: `, jobType);

    jobTypeService.updateJobType(id, jobType)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

jobTypeRouter.delete('/:id', (req,res) => {
    const id = req.params.id;
    
    console.log('---------------------------------------------------------------');
    console.log(`DELETE /jobTypes/${id}`);

    jobTypeService.deleteJobType(id)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

module.exports = jobTypeRouter;