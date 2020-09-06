const MongoClient = require('mongodb').MongoClient;

let db = null;

const connect = (cb) => {
    if(db){
        cb();
        return;
    }

    MongoClient.connect(process.env.MONGO_URI, {useUnifiedTopology: true}, (err, cl) => {
        if(err){
            cb(err);
        }else{
            db = cl.db();
            cb();
        }
    })
}

const disconnect = () => {
    return db.close();
}

const getDB = () => {
    return db;
}

module.exports = {connect, disconnect, getDB};