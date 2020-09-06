const timesheetCodeRouter = require('express').Router();
const timesheetCodeService = require('../services/timesheetCodeService');

timesheetCodeRouter.get('/', (req, res) => {
    console.log('---------------------------------------------------------------');
    console.log('GET /timesheetCodes');

    timesheetCodeService.getTimesheetCodes()
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

timesheetCodeRouter.get('/:id', (req, res) => {
    const id = req.params.id;

    console.log('---------------------------------------------------------------');
    console.log(`GET /timesheetCodes/${id}`);

    timesheetCodeService.getTimesheetCode(id)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

timesheetCodeRouter.post('/', (req, res) => {
    const timesheetCode = req.body;

    console.log('---------------------------------------------------------------');
    console.log('POST /timesheetCodes -> timesheetCode:', timesheetCode);

    timesheetCodeService.addTimesheetCode(timesheetCode)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

timesheetCodeRouter.put('/:id', (req,res) => {
    const id = req.params.id;
    const timesheetCode = req.body;

    console.log('---------------------------------------------------------------');
    console.log(`PUT /timesheetCodes/${id} -> timesheetCode: `, timesheetCode);

    timesheetCodeService.updateTimesheetCode(id, timesheetCode)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

timesheetCodeRouter.delete('/:id', (req,res) => {
    const id = req.params.id;
    
    console.log('---------------------------------------------------------------');
    console.log(`DELETE /timesheetCodes/${id}`);

    timesheetCodeService.deleteTimesheetCode(id)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

module.exports = timesheetCodeRouter;