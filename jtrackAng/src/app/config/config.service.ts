import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  static debug: boolean = false;
  // static baseUrl: string = "http://localhost:8082";
  static baseUrl: string = "http://kan-r.com:8082";

  constructor() { }
}
