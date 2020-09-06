const jobResolutionRouter = require('express').Router();
const jobResolutionService = require('../services/jobResolutionService');

jobResolutionRouter.get('/', (req, res) => {
    console.log('---------------------------------------------------------------');
    console.log('GET /jobResolutions');

    jobResolutionService.getJobResolutions()
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

jobResolutionRouter.get('/:id', (req, res) => {
    const id = req.params.id;

    console.log('---------------------------------------------------------------');
    console.log(`GET /jobResolutions/${id}`);

    jobResolutionService.getJobResolution(id)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

jobResolutionRouter.post('/', (req, res) => {
    const jobResolution = req.body;

    console.log('---------------------------------------------------------------');
    console.log('POST /jobResolutions -> jobResolution:', jobResolution);

    jobResolutionService.addJobResolution(jobResolution)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

jobResolutionRouter.put('/:id', (req,res) => {
    const id = req.params.id;
    const jobResolution = req.body;

    console.log('---------------------------------------------------------------');
    console.log(`PUT /jobResolutions/${id} -> jobResolution: `, jobResolution);

    jobResolutionService.updateJobResolution(id, jobResolution)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

jobResolutionRouter.delete('/:id', (req,res) => {
    const id = req.params.id;
    
    console.log('---------------------------------------------------------------');
    console.log(`DELETE /jobResolutions/${id}`);

    jobResolutionService.deleteJobResolution(id)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

module.exports = jobResolutionRouter;