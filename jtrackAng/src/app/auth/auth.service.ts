import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Subject }    from 'rxjs';
import { MessageService } from '../message/message.service';
import { ConfigService } from '../config/config.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private currentUser = new Subject<string>();
  currentUserObservable = this.currentUser.asObservable();

  private successUrl: string = "/Job";

  constructor(
    private httpClient: HttpClient, 
    private router: Router, 
    private messageService: MessageService) { }

  authenticate(user: string, password: string, successUrl: string): void {
    this.successUrl = successUrl;
    this.obtainAccessToken(user, password);
  }

  isUserLoggedIn(): boolean  {
    return (this.getAccessToken() !== null);
  }

  logOut(): void {
    this.log("Logged out");
    this.removeFromSessionStorage();
    this.router.navigate(['/Login']);
  }

  getHttpOptions(){
    let httpHeaders = 
      new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + this.getAccessToken()
      });

    return { headers: httpHeaders };
  }

  private obtainAccessToken(user: string, password: string): void {

    this.clearError();

    let  tokenUrl: string = ConfigService.baseUrl + "/oauth/token";

    let params = new URLSearchParams();
    params.append('username', user);
    params.append('password', password);    
    params.append('grant_type','password');

    let httpHeaders = 
      new HttpHeaders({
        'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8',
        'Authorization': 'Basic ' + btoa("jtrackAdmin:admin")
      });

    let httpOptions = {
      headers: httpHeaders
    };
  
    this.httpClient.post(tokenUrl, params.toString(), httpOptions)
      .subscribe(
        data => { this.addToSessionStorage(user, data) },
        error => { this.logError(error.error.error_description) }
      ); 
  }

  private addToSessionStorage(user: string, token: any): void {
    let  expireDate = new Date().getTime() + (1000 * token.expires_in);
    let sessObj = {
      'access_token': token.access_token, 
      'token_type': token.token_type,
      'expires_at': expireDate,
      'scope': token.scope
    };
    sessionStorage.setItem("sess_obj", JSON.stringify(sessObj));
    this.setCurrentUser(user);
    this.router.navigate([this.successUrl]);
    this.log(token.access_token);
  }

  private removeFromSessionStorage(): void {
    sessionStorage.removeItem('sess_obj');
    this.setCurrentUser("");
  }

  private setCurrentUser(user: string){
    this.currentUser.next(user);
  }

  private getAccessToken(): string {
    let sessObj = sessionStorage.getItem('sess_obj');
    if(sessObj !== null){
      let sessJSON = JSON.parse(sessObj);

      if(new Date().getTime() > sessJSON.expires_at){
        this.logError("Access token expired");
        this.logOut();
        return;
      }

      return sessJSON.access_token;
    }
    return null;
  }

  private log(message: string) {
    this.messageService.log(`AuthService: ${message}`);
  }

  private logError(error: string) {
    this.messageService.logError(`AuthService: ${error}`);
  }

  private clearError(){
    this.messageService.clearError();
  }
}