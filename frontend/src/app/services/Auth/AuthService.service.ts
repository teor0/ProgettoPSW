import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GET_TOKEN, CLIENT_ID, CLIENT_SECRET, END_SESSION, POST_LOGOUT_REDIRECT } from '../../helpers/constants';
import { RestManager } from 'src/app/managers/RestManager';

@Injectable({
  providedIn: 'root'
})

export class AuthService {

  restManager: RestManager;

  constructor(private http:HttpClient) {
    this.restManager=new RestManager(http);
  }

  getToken(callback: any){
    sessionStorage.removeItem('token');
    var headers= {'Content-Type': 'application/x-www-form-urlencoded'};
    var body= new URLSearchParams({
      'client_id': CLIENT_ID,
      'client_secret': CLIENT_SECRET,
      'grant_type': 'client_credentials'
    });
    this.http.request('post',END_SESSION,{body,'headers':headers}).subscribe({
      next:(resp: any)=>{
        callback(true,resp);
      },
      error: (error: HttpErrorResponse) => {
        callback(false,error);
      },
    });
  }


  logout(callback: any){
    sessionStorage.removeItem('token');
    let token = sessionStorage.getItem('refresh_token');
    let id_token = sessionStorage.getItem('id_token');
    var headers= {'Content-Type': 'application/x-www-form-urlencoded'};
    var body= new URLSearchParams({
      'refresh_token': token as string,
      'post_logout_redirect_uri': POST_LOGOUT_REDIRECT,
      'id_token_hint': id_token as string,
      'client_id': CLIENT_ID,
      'client_secret': CLIENT_SECRET,
      'grant_type': 'refresh_token'
    });
    this.http.request('post',END_SESSION,{body,'headers':headers}).subscribe({
      next:(resp: any)=>{
        callback(true,resp);
      },
      error: (error: HttpErrorResponse) => {
        callback(false,error);
      },
    });
  }

  getTokenAsUser(username: string, password: string, callback: any){
    sessionStorage.removeItem('token');
    var headers= {'Content-Type': 'application/x-www-form-urlencoded'};
    var body= new URLSearchParams({
      'username': username,
      'password': password,
      'client_id': CLIENT_ID,
      'client_secret': CLIENT_SECRET,
      'grant_type': 'password',
      'scope': 'openid profile email'
    });
    this.http.request('post',GET_TOKEN,{body,'headers':headers}).subscribe({
      next: (response: any) => {
        sessionStorage.setItem('token',response.access_token);
        sessionStorage.setItem('refresh_token',response.refresh_token);
        sessionStorage.setItem('id_token', response.id_token);
        callback(true,response);
      },
      error: (error: HttpErrorResponse) => {
          console.log(error);
          callback(false, error);
      },
    });
  }

  useRefreshToken(){
    var token = sessionStorage.getItem('refresh_token');
    sessionStorage.setItem('noToken', 'yes');
    var headers= {'Content-Type': 'application/x-www-form-urlencoded'};
    var body= new URLSearchParams({
      'client_id': CLIENT_ID,
      'client_secret': CLIENT_SECRET,
      'grant_type': 'refresh_token',
      'refresh_token': token as string
    });
    return this.http.request('post',GET_TOKEN,{body,'headers':headers});
  }

}
