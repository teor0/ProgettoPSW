import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/Auth/AuthService.service';

@Component({
  selector: 'app-CompleteRegistration',
  templateUrl: './CompleteRegistration.component.html',
  styleUrls: ['./CompleteRegistration.component.css']
})
export class CompleteRegistrationComponent{

  constructor(private authService:AuthService){}


  completeRegistration(){
    this.authService.createUserProfile();
  }



}
