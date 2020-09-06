const jobStageRouter = require('express').Router();
const jobStageService = require('../services/jobStageService');

jobStageRouter.get('/', (req, res) => {
    console.log('---------------------------------------------------------------');
    console.log('GET /jobStages');

    jobStageService.getJobStages()
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

jobStageRouter.get('/:id', (req, res) => {
    const id = req.params.id;

    console.log('---------------------------------------------------------------');
    console.log(`GET /jobStages/${id}`);

    jobStageService.getJobStage(id)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

jobStageRouter.post('/', (req, res) => {
    const jobStage = req.body;

    console.log('---------------------------------------------------------------');
    console.log('POST /jobStages -> jobStage:', jobStage);

    jobStageService.addJobStage(jobStage)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

jobStageRouter.put('/:id', (req,res) => {
    const id = req.params.id;
    const jobStage = req.body;

    console.log('---------------------------------------------------------------');
    console.log(`PUT /jobStages/${id} -> jobStage: `, jobStage);

    jobStageService.updateJobStage(id, jobStage)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

jobStageRouter.delete('/:id', (req,res) => {
    const id = req.params.id;
    
    console.log('---------------------------------------------------------------');
    console.log(`DELETE /jobStages/${id}`);

    jobStageService.deleteJobStage(id)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

module.exports = jobStageRouter;