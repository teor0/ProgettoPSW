import { Injectable } from '@angular/core';
import { HttpErrorResponse, HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable, catchError, switchMap, throwError } from 'rxjs';
import { AuthService } from '../services/Auth/AuthService.service';


@Injectable()
export class AuthInterceptor implements HttpInterceptor {


  constructor(private authService: AuthService){}

  private handleError(request: HttpRequest<any>, next: HttpHandler){
    return this.authService.useRefreshToken().pipe(switchMap((response:any) =>{
      sessionStorage.setItem('token',response.access_token);
      sessionStorage.setItem('refresh_token',response.refresh_token);
      sessionStorage.removeItem('noToken');
      var token=sessionStorage.getItem("token") as string;
      var newHeaders= new HttpHeaders({
        'Authorization': `Bearer ${token}`,
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE',
        'Access-Controll-Allow-Headers': 'Content-Type, X-Auth-Token, Origin, Authorization'
    });
      return next.handle(request.clone({headers:newHeaders}));
    }),catchError((error)=>{
      return throwError(()=> error);
    }))
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    var token = sessionStorage.getItem("token") as string;
    var useToken = sessionStorage.getItem('noToken');

    if (!token || useToken) {
      return next.handle(request);
    }

    var headers= new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE',
      'Access-Controll-Allow-Headers': 'Content-Type, X-Auth-Token, Origin, Authorization'
    });

    const authorizedRequest = request.clone({
      headers,});

    return next.handle(authorizedRequest).pipe(
      catchError((error: HttpErrorResponse)=>{
        if(error.status === 401){
          return this.handleError(request,next);
        }
        return throwError(()=> error);
      })
    );
  }


}
