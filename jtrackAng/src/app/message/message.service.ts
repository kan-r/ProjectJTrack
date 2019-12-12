import { Injectable } from '@angular/core';
import { ConfigService } from '../config/config.service';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor() { }

  private debug: boolean = ConfigService.debug;

  messages: string[] = [];
  errors: string[] = [];

  log(message: string) {
    if (this.debug) {
      this.messages.push(message);
    }
  }

  logError(error: string){
    this.errors.push(error);  
  }

  clear() {
    this.messages = [];
  }

  clearError() {
    this.errors = [];
  }
}
