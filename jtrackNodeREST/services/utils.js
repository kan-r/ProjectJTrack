class Utils {

    isEmpty(str) { 
        if (str === null || str == undefined || str.length == 0) { 
            return true; 
        } else { 
            return false; 
        } 
    } 

    toBoolean(str) { 
        return str !== null && str.toLowerCase() === 'true';
    } 

    toDate(str){
        if(this.isEmpty(str)){
            return null;
        }
    
        return new Date(str);
    }

}

const utils = new Utils();

module.exports = utils;