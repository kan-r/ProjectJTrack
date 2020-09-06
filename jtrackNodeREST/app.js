const express = require('express');
const app = express();
const dotenv = require('dotenv');

const dbService = require('./services/dbService');
const jobPriorityRouter = require('./routers/jobPriorityRouter');
const jobResolutionRouter = require('./routers/jobResolutionRouter');
const jobRouter = require('./routers/jobRouter');
const jobStageRouter = require('./routers/jobStageRouter');
const jobStatusRouter = require('./routers/jobStatusRouter');
const jobTypeRouter = require('./routers/jobTypeRouter');
const timesheetCodeRouter = require('./routers/timesheetCodeRouter');
const timesheetRouter = require('./routers/timesheetRouter');
const userRouter = require('./routers/userRouter');

// load config
dotenv.config({path: './config/config.env'});

const port = process.env.PORT || 4000; //if PORT env variable is set or 4000
const mode = process.env.NODE_ENV;

app.use(express.json()) // req.body

app.use('/jobPriorities', jobPriorityRouter);
app.use('/jobResolutions', jobResolutionRouter);
app.use('/jobs', jobRouter);
app.use('/jobStages', jobStageRouter);
app.use('/jobStatuses', jobStatusRouter);
app.use('/jobTypes', jobTypeRouter);
app.use('/timesheetCodes', timesheetCodeRouter);
app.use('/timesheets', timesheetRouter);
app.use('/users', userRouter);

// connect to db
dbService.connect( err => {
    if(err){
        console.log(err);
        process.exit(1);
    }else{
        app.listen(port, () => {
            console.log(`Server is running in ${mode} mode on port ${port}`);
        });
    }
})

app.get('/', (req, res) => {
    res.send('JTrack REST API');
});