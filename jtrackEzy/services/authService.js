import { axiosx } from './axiosx.js';

// const BASE_URL = 'http://localhost:8082';
// const BASE_URL = 'http://kan-r.com:8082';
const BASE_URL = 'https://kan-r.com/jtrackREST';

const USER_SESSION_ATTRIBUTE = 'USER';
const TOKEN_SESSION_ATTRIBUTE = 'TOKEN';

class AuthService {

    login(user, password, loginCallback){
        if(user == null || user === ''){
            loginCallback(false, 'User is required');
            return;
        }

        if(password == null || password === ''){
            loginCallback(false, 'Password is required');
            return;
        }

        const options = {
           mode: 'cores',
           headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': 'Basic ' + window.btoa("jtrackAdmin:admin")
           },
           params: {
             username: user,
             password: password,
             grant_type:'password'
           } 
        }

        axiosx.post(BASE_URL + '/oauth/token', {}, options)
        .then(res => {
            this.addToSessionStorage(user, res);
            this.postLoginHist(user);
            loginCallback(true);
        })
        .catch(err => {
            loginCallback(false, err);
        });
    }

    postLoginHist(userId){
        const loginHist = {
            userId: userId.toUpperCase()
        }

        axiosx.post(BASE_URL + '/loginHist', loginHist, this.getHttpOptions())
        .then(res => {
        
        })
        .catch(err => {
        
        });
    }

    isUserLoggedIn() {
        const token = this.getAccessToken();
        return (token !== null && token !== '');
    }

    isSessionExpired(){
        const sessObj = sessionStorage.getItem(TOKEN_SESSION_ATTRIBUTE);
        if(sessObj !== null){
            const sessJSON = JSON.parse(sessObj);

            if(new Date().getTime() >= sessJSON.expires_at){
                this.logout();
                return true;
            }
        }
        return false;
    }

    isUserAdmin(){
        const user = this.getAppUser();
        return (user === 'ADMIN');
    }

    logout(){
        this.removeFromSessionStorage();
    }

    getAccessToken(){
        const sessObj = sessionStorage.getItem(TOKEN_SESSION_ATTRIBUTE);
        if(sessObj !== null){
            const sessJSON = JSON.parse(sessObj);
            return sessJSON.access_token;
        }
        return null;
    }

    getAppUser(){
        return sessionStorage.getItem(USER_SESSION_ATTRIBUTE);
    }

    getBaseUrl(){
        return BASE_URL;
    }

    getHttpOptions(){
        const options = {
            mode: 'cors',
            headers: {
             'Content-Type': 'application/json',
             'Authorization': 'Bearer ' + this.getAccessToken()
            }
         }

         return options;
    }

    addToSessionStorage(user, token){
        
        const  expireDate = new Date().getTime() + (1000 * token.expires_in);

        const sessObj = {
            'access_token': token.access_token, 
            'token_type': token.token_type,
            'expires_at': expireDate,
            'scope': token.scope
        };
       
        sessionStorage.setItem(USER_SESSION_ATTRIBUTE, user.toUpperCase());
        sessionStorage.setItem(TOKEN_SESSION_ATTRIBUTE, JSON.stringify(sessObj));
    }

    removeFromSessionStorage(){
        sessionStorage.removeItem(USER_SESSION_ATTRIBUTE);
        sessionStorage.removeItem(TOKEN_SESSION_ATTRIBUTE);
    }
}

export let authService = new AuthService();