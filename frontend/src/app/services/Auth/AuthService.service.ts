import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { KeycloakProfile } from 'keycloak-js';
import { UserImpl } from 'src/app/models/User';
import { UserService } from '../ModelServices/User.service';
import { Router } from '@angular/router';
import { ResponseService } from 'src/app/helpers/Response/ResponseService.service';
import { OrderService } from '../ModelServices/Order.service';

@Injectable({
  providedIn: 'root'
})

export class AuthService {

  private profile: KeycloakProfile | undefined;

  //possible event handling, look https://github.com/mauriciovigolo/keycloak-angular/issues/291
  constructor(private keycloakService: KeycloakService, private userService:UserService,
              private router:Router, private responseService:ResponseService, private orderService:OrderService){
  }

  //LOGIN & REGISTRATION
  login(){
    this.keycloakService.login({redirectUri: "http://localhost:4200/complete-login"});
  }

  logout(){
    this.keycloakService.logout("http://localhost:4200").then(() =>{
    },
    error =>{
      console.error(error);
    });
    sessionStorage.clear();
    this.profile = undefined;
  }

  register(): void{
    this.keycloakService.register({redirectUri: "http://localhost:4200/complete-registration"});
  }


  createUserProfile(){
    this.keycloakService.loadUserProfile().then(profile =>{
      this.profile = profile;
      this.createUser(this.profile!)
    });
  }

  loadUserProfile(){
    this.keycloakService.loadUserProfile().then(profile =>{
      this.profile = profile;
      this.constructUser(this.profile.username!)
    });
  }

  private constructUser(username: string){
    this.userService.getUserByUsername(username!,this.setUser.bind(this));
  }

  private setUser(status:boolean, response:any){
    if(status){
      sessionStorage.setItem('user',JSON.stringify(response));
      this.orderService.getByUserAndPendingNoCallback(response);
      this.router.navigateByUrl('');
      this.responseService.openDialogCustom('Redirected to home page');
    }
  }

  private createUser(profile:KeycloakProfile){
    var user=new UserImpl();
    user.name=profile.firstName! +' '+ profile.lastName!;
    user.email=profile.email!;
    user.username=profile.username!;
    if(this.isUser())
      user.role='User';
    else if(this.isVendor())
      user.role='Vendor';
    else if(this.isAdmin())
      user.role='Admin';
    this.userService.createUser(user,this.constructUser.bind(this,user.username));
  }



  //GETTERS
  isAdmin():boolean{
    return this.keycloakService.getUserRoles().includes('Admin');
  }

  isVendor():boolean{
    return this.keycloakService.getUserRoles().includes('Vendor');
  }

  isUser():boolean{
    return this.keycloakService.getUserRoles().includes('User');
  }

}
