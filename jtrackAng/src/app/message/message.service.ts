import { Injectable } from '@angular/core';
import { ConfigService } from '../config/config.service';
import { Observable, throwError } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(
    private router: Router) { }

  private debug: boolean = ConfigService.debug;

  messages: string[] = [];
  errors: string[] = [];

  log(message: string) {
    if (this.debug) {
      this.messages.push(message);
    }
  }

  logError(error: string){
    if (!this.debug) {
      this.clearError();
    }
    this.errors.push(error);  
  }

  clear() {
    this.messages = [];
  }

  clearError() {
    this.errors = [];
  }

  /**
     * Handle Http operation that failed.
     * Let the app continue.
     * @param operation - name of the operation that failedS
     * @param result - optional value to return as the observable result
     */
  handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      let err = this.extractError(error);
      this.logError(`${operation} failed: ${err}`);
      return throwError(error);
    };
  }

  extractError(error: any){
    let err = error;
       
    if(err === null || err === ''){
        return '';
    }

    if(typeof(err) !== 'object'){
      return err;
    }

    // console.log(err);

    if(err.status === 0){
      err = 'Service is not running';
    }else{
      if(typeof(err.error) === 'object'){
        err = error.error.error;
        if(err === 'invalid_token'){
          err = 'Session expired';
          this.router.navigate(['/Login']);
          // this.logout();   
        }else if(err === 'invalid_grant'){
          err = 'Bad credentials';    
        }
      }else{
        err = error.error;  
      }
    }

    return err;
  }
}
