import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ADDRESS_SERVER, DELETE_USER, GET_USER_ID, REQUEST_LOGIN, REQUEST_SEARCH, REQUEST_USER } from 'src/app/helpers/constants';
import { RestManager } from 'src/app/managers/RestManager';
import { User } from 'src/app/models/User';
import { AuthService } from '../Auth/AuthService.service';
import { ResponseService } from 'src/app/helpers/Response/ResponseService.service';
import { Observable, Subject, async } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  restManager: RestManager;
  isLogged: boolean=false;
  logStatusChange: Subject<boolean> = new Subject<boolean>();
  isAdmin: boolean=false;
  adminStatusChange: Subject<boolean> = new Subject<boolean>();

  constructor(private http:HttpClient, private authService: AuthService,
    private responseService:ResponseService, private router:Router){
    this.restManager= new RestManager(http);
    this.logStatusChange.subscribe(async status=>{
      this.isLogged=status;
    });
    this.adminStatusChange.subscribe(async status=>{
      this.isAdmin=status;
    });
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


  //LOGIN METHODS
  register(user: User ){
    this.restManager.makePostRequest(ADDRESS_SERVER,REQUEST_LOGIN,this.registerSuccess.bind(this,user),user);
  }

  login(user: User){
    this.authService.getTokenAsUser(user.username,user.password,this.loginProcess.bind(this,user));
  }

  logout(user: User){
    this.authService.logout(this.logoutSuccess.bind(this));
  }

  private registerSuccess(user: User, status: boolean, response : any){
    if(status){
      this.responseService.openDialogOk(response);
      this.login(user);
    }
  }

  private loginProcess(user:User,status: boolean, response:User){
    if(status)
      this.getUserByUsername(user.username,this.loginSuccess.bind(this));
  }

  private loginSuccess(status: boolean, user: any){
    if(status){
      this.responseService.openDialogOk(user);
      sessionStorage.setItem('user',JSON.stringify(user));
      this.logStatusChange.next(!this.isLogged);
      if(user.role==='Admin')
        this.adminStatusChange.next(!this.isAdmin);
      this.router.navigateByUrl('/User')
    }
  }

  private logoutSuccess(status: boolean, response: any){
    if(status){
      this.logStatusChange.next(!this.isLogged);
      let user=JSON.parse(sessionStorage.getItem('user') as string) as User;
      if(user.role==='Admin')
        this.adminStatusChange.next(!this.isAdmin);
      sessionStorage.clear();
      sessionStorage.setItem('storedProducts',JSON.stringify([]));
      this.router.navigateByUrl('');
    }
  }

  //DELETE METHOD

  deleteUser(id:number,username:string,callback:any){
    this.restManager.makeDeleteRequest(ADDRESS_SERVER,REQUEST_USER+'/'+id,callback);
  }

}

