const jobPriorityRouter = require('express').Router();
const jobPriorityService = require('../services/jobPriorityService');

jobPriorityRouter.get('/', (req, res) => {
    console.log('---------------------------------------------------------------');
    console.log('GET /jobPriorities');

    jobPriorityService.getJobPriorities()
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

jobPriorityRouter.get('/:id', (req, res) => {
    const id = req.params.id;

    console.log('---------------------------------------------------------------');
    console.log(`GET /jobPriorities/${id}`);

    jobPriorityService.getJobPriority(id)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

jobPriorityRouter.post('/', (req, res) => {
    const jobPriority = req.body;

    console.log('---------------------------------------------------------------');
    console.log('POST /jobPriorities -> jobPriority:', jobPriority);

    jobPriorityService.addJobPriority(jobPriority)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

jobPriorityRouter.put('/:id', (req,res) => {
    const id = req.params.id;
    const jobPriority = req.body;

    console.log('---------------------------------------------------------------');
    console.log(`PUT /jobPriorities/${id} -> jobPriority: `, jobPriority);

    jobPriorityService.updateJobPriority(id, jobPriority)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

jobPriorityRouter.delete('/:id', (req,res) => {
    const id = req.params.id;
    
    console.log('---------------------------------------------------------------');
    console.log(`DELETE /jobPriorities/${id}`);

    jobPriorityService.deleteJobPriority(id)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

module.exports = jobPriorityRouter;