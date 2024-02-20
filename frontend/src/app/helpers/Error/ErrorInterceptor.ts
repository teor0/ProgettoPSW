import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators'
import { ResponseService } from '../Response/ResponseService.service';
import { Router } from '@angular/router';


@Injectable()
export class ErrorHandlerInterceptor implements HttpInterceptor {
  constructor(private responseService: ResponseService, private router:Router) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const req= request.clone();

    return next.handle(req).pipe(
      catchError((error) => this.handleError(error)));
    }

  // HANDLING
  private handleError(error: HttpEvent<any>): Observable<HttpEvent<any>> {
      if (error instanceof HttpErrorResponse) {
        if(error.status!=401 && error.status!=403)
          this.responseService.openDialogError(error);
        if(error.status===403 || error.status===401){
          this.router.navigateByUrl('');
          this.responseService.openDialogCustom('FORBIDDEN URL. REDIRECTED TO HOMEPAGE');
        }
      }
      throw error;
  }
}

