import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';


export class RestManager{
    http: HttpClient;


    constructor(http: HttpClient){
        this.http = http;
    }

    //metodo generale che fa da scheletro N.B. la subscribe viene fatta nello scheletro
    private makeRequest(serverAddress: string, servicePath: string, method: string, callback: any, body?: any){
      const headers= new HttpHeaders({
        'Content-Type': 'application/json'
      });
        this.http.request(
            method,
            serverAddress + servicePath,
            {body,headers}
        )
        .subscribe({
            next: (response: any) => {
                callback(true, response);
            },
            error: (error: HttpErrorResponse) => {
                console.log(error);
                callback(false, error);
            },
        });
    }


    public makePostRequest(serverAddress: string, servicePath: string,callback:any, body: any){
      //return this.makeRequest(serverAddress,servicePath,"post",callback,body);
        this.http.post(serverAddress+servicePath,body).subscribe({
          next: (response: any) => {
              callback(true, response);
          },
          error: (error: HttpErrorResponse) => {
              console.log(error);
              callback(false, error);
          },
      });
    }

    public makeGetPathRequest(serverAddress: string, servicePath: string, callback:any, path: any){
      this.http.request(
        'get',
        serverAddress + servicePath + '/' + path,
      ).subscribe({
        next: (response: any) => {
          callback(true,response);
        },
        error: (error: HttpErrorResponse) => {
          console.log(error);
          callback(false,error);
        },
      })
    }

    public makeGetRequest(serverAddress: string, servicePath: string, callback: any, body?: any){
        var requestPath = servicePath + "?";
        for (let key in body) {
            let value = body[key];
            requestPath += key + "=" + value + "&"
        }
        this.http.request(
          'get',
          serverAddress + requestPath,
          body
        ).subscribe({
          next: (response: any) => {
            callback(true,response);
          },
          error: (error: HttpErrorResponse) => {
            console.log(error);
            callback(false,error);
          },
        })
    }


    public simpleMakeGetRequest(serverAddress: string, servicePath: string, callback?: any, body?: any){
      return this.makeRequest(serverAddress, servicePath, "get", callback, body)
    }

    public makePutRequest(serverAddress: string, servicePath: string,  body: any, callback?: any,){
        return this.makeRequest(serverAddress, servicePath, "put", callback, body)
    }

    public makeDeleteRequest(serverAddress: string, servicePath: string, callback: any, body?: any){
        return this.makeRequest(serverAddress, servicePath, "delete", callback, body)
    }


}
