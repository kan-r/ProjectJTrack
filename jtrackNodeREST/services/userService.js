const dbService = require('./dbService');
const invalidData = require('./errorService');

class UserService {

    getUsers(){
        return dbService.getDB()
            .collection('users')
            .find({}, {projection: {pword: false}})
            .sort({_id: 1})
            .toArray();
    }

    getUser(id){
        return dbService.getDB()
            .collection('users')
            .findOne({_id: id});
    }

    async addUser(user){
        if(!user._id){
            return Promise.reject(invalidData('User (_id) is required'));
        }

        if(!user.pword){
            return Promise.reject(invalidData('Password (pword) is required'));
        }

        const usr = await this.getUser(user._id);
        if(usr){
            return Promise.reject(invalidData('User already exists'));
        }

        user.dateCrt = new Date();
        user.userCrt = "";

        const res = await dbService.getDB()
            .collection('users')
            .insertOne(user);

        return res.ops[0];
    }

    async updateUser(id, user){
        if(!user.pword){
            return Promise.reject(invalidData('Password (pword) is required'));
        }

        const usr = await this.getUser(id);
        if(!usr){
            return Promise.reject(invalidData('User does not exist'));
        }

        if(user.hasOwnProperty('_id')){
            user._id = id;
        }

        user.dateMod = new Date();
        user.userMod = "";

        const res = await dbService.getDB()
            .collection('users')
            .findOneAndUpdate(
                {_id: id},
                { $set: user },
                {returnOriginal: false}
            );

        return res.value;
    }

    async deleteUser(id){
        const usr = await this.getUser(id);
        if(!usr){
            return Promise.reject(invalidData('User does not exist'));
        }

        const res = await dbService.getDB()
            .collection('users')
            .deleteOne({_id: id});

        return null;
    }
}

const userService = new UserService();

module.exports = userService;