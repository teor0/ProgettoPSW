import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { User } from 'src/app/models/User';
import { UserService } from 'src/app/services/ModelServices/User.service';

@Component({
  selector: 'app-Login',
  templateUrl: './Login.component.html',
  styleUrls: ['./Login.component.css']
})
export class LoginComponent implements OnInit {

  hide = true;
  regForm!: FormGroup;
  logForm!: FormGroup;
  roles= ['User','Vendor','Admin'];
  user!: User;
  resultlogout!: String;
  showRegister= true;
  showLogin= false;

  constructor(private userService: UserService){}

  ngOnInit(): void {
    this.regForm=new FormGroup({
      id: new FormControl(1),
      username: new FormControl(null),
      name: new FormControl(null),
      email: new FormControl(null),
      role: new FormControl(null),
      password: new FormControl(null)
    });
    this.logForm=new FormGroup({
      username: new FormControl(null),
      password: new FormControl(null)
    });
  }

  register(){
    this.user=this.regForm.value;
    this.userService.register(this.user);
  }

  login(){
    this.user=this.logForm.value;
    this.userService.login(this.user);
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
