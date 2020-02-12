
class AuthService {

    formatDateTime(dtTime){
        let options = {
            year: 'numeric', month: 'numeric', day: 'numeric',
            hour: 'numeric', minute: 'numeric', second: 'numeric',
            hour12: false,
            timeZone: 'Australia/Melbourne' 
        };

        let dt = '';

        if(dtTime !== null && dtTime !== '') {
            dt = new Intl.DateTimeFormat('en-AU', options).format(new Date(dtTime));
        }

        return dt;
    }

    formatDate(dtTime){
        let options = {
            year: 'numeric', month: 'numeric', day: 'numeric'
        };

        let dt = '';

        if(dtTime !== null && dtTime !== '') {
            dt = new Intl.DateTimeFormat('en-AU', options).format(new Date(dtTime));
        }

        return dt;
    }

    formatDay(dtTime){
        let options = {
            weekday: 'short'
        };

        let dt = '';

        if(dtTime !== null && dtTime !== '') {
            dt = new Intl.DateTimeFormat('en-AU', options).format(new Date(dtTime));
        }

        return dt;
    }
}

export default new AuthService();
