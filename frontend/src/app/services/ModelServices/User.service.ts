import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ADDRESS_SERVER, REQUEST_LOGIN, REQUEST_SEARCH, REQUEST_USER } from 'src/app/helpers/constants';
import { RestManager } from 'src/app/managers/RestManager';
import { User } from 'src/app/models/User';
import { Observable } from 'rxjs';
import { ResponseService } from 'src/app/helpers/Response/ResponseService.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  restManager: RestManager;

  constructor(private http:HttpClient, private responseService:ResponseService){
    this.restManager= new RestManager(http);
  }

  //CREATE & DELETE
  createUser(user:User, callback:any){
    this.restManager.makePostRequest(ADDRESS_SERVER,REQUEST_LOGIN,callback,user);
  }

  private creationSuccess(status:boolean,response:any){
    if(status)
      this.responseService.openDialogOk(response);
  }


  deleteUser(id:number,callback:any){
    this.restManager.makeDeleteRequest(ADDRESS_SERVER,REQUEST_USER+'/'+id,callback);
  }

  //GETTERS
  getUserById(callback: any, id: number){
    this.restManager.makeGetPathRequest(ADDRESS_SERVER,REQUEST_USER+REQUEST_SEARCH,callback,id);
  }

  getUsersByName(callback: any, name: string ){
    this.restManager.makeGetRequest(ADDRESS_SERVER,REQUEST_USER+REQUEST_SEARCH,callback,{name: name});
  }

  getUserByEmail(callback: any, email: string){
    this.restManager.makeGetRequest(ADDRESS_SERVER,REQUEST_USER+REQUEST_SEARCH,callback,{email: email});
  }

  getUserByUsername(username: string,callback?: any){
    this.restManager.makeGetPathRequest(ADDRESS_SERVER,REQUEST_USER,callback,username);
  }

  getUsersByUsername(callback: any, username: string ){
    this.restManager.makeGetRequest(ADDRESS_SERVER,REQUEST_USER+REQUEST_SEARCH,callback,{username: username});
  }

  getUsers(): Observable<User[]>{
    return this.http.request<User[]>('get',ADDRESS_SERVER+REQUEST_USER+'/all');
  }

  getAllUsers(callback: any){
    this.restManager.simpleMakeGetRequest(ADDRESS_SERVER,REQUEST_USER+'/all',callback);
  }

  getOrdersByUserId(callback: any, id: number){
    this.restManager.makeGetRequest(ADDRESS_SERVER,REQUEST_USER+'/orders',callback,id);
  }


}

