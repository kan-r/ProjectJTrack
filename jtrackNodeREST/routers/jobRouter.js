const jobRouter = require('express').Router();
const jobService = require('../services/jobService');

jobRouter.get('/', (req, res) => {
    console.log('---------------------------------------------------------------');
    console.log('GET /jobs');

    jobService.getJobs(req.query)
    .then(result => {
        res.json(result);
    })
    .catch(err => {
        console.log(err);
        res.status(400).send(err);
    })
});

jobRouter.get('/:id', (req, res) => {
    const id = req.params.id;

    console.log('---------------------------------------------------------------');
    console.log(`GET /jobs/${id}`);

    jobService.getJob(id)
        .then(result => {
            console.log(result);
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

jobRouter.post('/', (req, res) => {
    const job = req.body;

    console.log('---------------------------------------------------------------');
    console.log('POST /jobs -> job:', job);

    jobService.addJob(job)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

jobRouter.put('/:id', (req,res) => {
    const id = req.params.id;
    const job = req.body;

    console.log('---------------------------------------------------------------');
    console.log(`PUT /jobs/${id} -> job: `, job);

    jobService.updateJob(id, job)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

jobRouter.delete('/:id', (req,res) => {
    const id = req.params.id;
    
    console.log('---------------------------------------------------------------');
    console.log(`DELETE /jobs/${id}`);

    jobService.deleteJob(id)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

module.exports = jobRouter;