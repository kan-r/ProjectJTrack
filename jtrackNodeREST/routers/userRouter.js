const userRouter = require('express').Router();
const userService = require('../services/userService');

userRouter.get('/', (req, res) => {
    console.log('---------------------------------------------------------------');
    console.log('GET /users');

    userService.getUsers()
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

userRouter.get('/:id', (req, res) => {
    const id = req.params.id;

    console.log('---------------------------------------------------------------');
    console.log(`GET /users/${id}`);

    userService.getUser(id)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

userRouter.post('/', (req, res) => {
    const user = req.body;

    console.log('---------------------------------------------------------------');
    console.log('POST /users -> user:', user);

    userService.addUser(user)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

userRouter.put('/:id', (req,res) => {
    const id = req.params.id;
    const user = req.body;

    console.log('---------------------------------------------------------------');
    console.log(`PUT /users/${id} -> user: `, user);

    userService.updateUser(id, user)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

userRouter.delete('/:id', (req,res) => {
    const id = req.params.id;
    
    console.log('---------------------------------------------------------------');
    console.log(`DELETE /users/${id}`);

    userService.deleteUser(id)
        .then(result => {
            res.json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(400).send(err);
        })
});

module.exports = userRouter;