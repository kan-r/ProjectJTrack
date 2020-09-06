const timesheetRouter = require('express').Router();
const timesheetService = require('../services/timesheetService');

timesheetRouter.get('/', (req, res) => {
    console.log('---------------------------------------------------------------');
    console.log('GET /timesheets');

    timesheetService.getTimesheets(req.query)
    .then(result => {
        res.json(result);
    })
    .catch(err => {
        console.log(err);
        res.status(400).send(err);
    })
});

timesheetRouter.get('/:id', (req, res) => {
    const id = req.params.id;

    console.log('---------------------------------------------------------------');
    console.log(`GET /timesheets/${id}`);

    timesheetService.getTimesheet(id)
        .then(result => {
            console.log(result);
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

timesheetRouter.post('/', (req, res) => {
    const timesheet = req.body;

    console.log('---------------------------------------------------------------');
    console.log('POST /timesheets -> timesheet:', timesheet);

    timesheetService.addTimesheet(timesheet)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

timesheetRouter.put('/:id', (req,res) => {
    const id = req.params.id;
    const timesheet = req.body;

    console.log('---------------------------------------------------------------');
    console.log(`PUT /timesheets/${id} -> timesheet: `, timesheet);

    timesheetService.updateTimesheet(id, timesheet)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

timesheetRouter.delete('/:id', (req,res) => {
    const id = req.params.id;
    
    console.log('---------------------------------------------------------------');
    console.log(`DELETE /timesheets/${id}`);

    timesheetService.deleteTimesheet(id)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

module.exports = timesheetRouter;