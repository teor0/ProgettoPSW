import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/Auth/AuthService.service';

@Component({
  selector: 'app-Login',
  templateUrl: './Login.component.html',
  styleUrls: ['./Login.component.css']
})
export class LoginComponent {


  showRegister= true;
  showLogin= false;


  constructor(private authService:AuthService){}

  login(){
    this.authService.login();
  }

  register(){
    this.authService.register();
  }

  showRegisterButton(){
    this.showRegister=true;
    this.showLogin=false;
  }

  showLoginButton(){
    this.showRegister=false;
    this.showLogin=true;
  }

}
