const dbService = require('./dbService');

class SeqGenService {

    async getSequenceNextValue(seqName){
       
        const res = await dbService.getDB()
            .collection('seqGen')
            .findOneAndUpdate(
                { _id: seqName },
                { $inc: {value: 1} },
                { returnOriginal: false }
            );

        return res.value.value;
    }
}

const seqGenService = new SeqGenService();

module.exports = seqGenService;