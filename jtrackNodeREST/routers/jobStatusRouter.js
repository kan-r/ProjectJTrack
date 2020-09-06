const jobStatusRouter = require('express').Router();
const jobStatusService = require('../services/jobStatusService');

jobStatusRouter.get('/', (req, res) => {
    console.log('---------------------------------------------------------------');
    console.log('GET /jobStatuses');

    jobStatusService.getJobStatuses()
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

jobStatusRouter.get('/:id', (req, res) => {
    const id = req.params.id;

    console.log('---------------------------------------------------------------');
    console.log(`GET /jobStatuses/${id}`);

    jobStatusService.getJobStatus(id)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

jobStatusRouter.post('/', (req, res) => {
    const jobStatus = req.body;

    console.log('---------------------------------------------------------------');
    console.log('POST /jobStatuses -> jobStatus:', jobStatus);

    jobStatusService.addJobStatus(jobStatus)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

jobStatusRouter.put('/:id', (req,res) => {
    const id = req.params.id;
    const jobStatus = req.body;

    console.log('---------------------------------------------------------------');
    console.log(`PUT /jobStatuses/${id} -> jobStatus: `, jobStatus);

    jobStatusService.updateJobStatus(id, jobStatus)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

jobStatusRouter.delete('/:id', (req,res) => {
    const id = req.params.id;
    
    console.log('---------------------------------------------------------------');
    console.log(`DELETE /jobStatuses/${id}`);

    jobStatusService.deleteJobStatus(id)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

module.exports = jobStatusRouter;