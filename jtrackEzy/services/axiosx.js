class Axiosx {

    get(url, options){
        return axios.get(url, options)
            .then(res => {
                return Promise.resolve(res.data);
            })
            .catch(err => {
                return Promise.reject(this._processError(err));
            })
    }

    post(url, data, options){
        return axios.post(url, data, options)
            .then(res => {
                return Promise.resolve(res.data);
            })
            .catch(err => {
                return Promise.reject(this._processError(err));
            })
    }

    put(url, data, options){
        return axios.put(url, data, options)
            .then(res => {
                return Promise.resolve(res.data);
            })
            .catch(err => {
                return Promise.reject(this._processError(err));
            })
    }

    delete(url, options){
        return axios.delete(url, options)
            .then(res => {
                return Promise.resolve(res.data);
            })
            .catch(err => {
                return Promise.reject(this._processError(err));
            })
    }

    _processError(error){
        let err = error;
  
        if(err.response !== undefined ){
            err = err.response.data;
    
            if(err.error !== undefined){
                err = err.error;
                if(err === 'invalid_token'){
                    err = 'Session expired';
                }else if(err === 'invalid_grant'){
                    err = 'Bad credentials';    
                }
            }
        }else if(err.message !== undefined){
            err = err.message;
        }
    
        return err;
    }
}

export let axiosx = new Axiosx();