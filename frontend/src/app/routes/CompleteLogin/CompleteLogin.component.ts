import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/Auth/AuthService.service';

@Component({
  selector: 'app-CompleteLogin',
  templateUrl: './CompleteLogin.component.html',
  styleUrls: ['./CompleteLogin.component.css']
})
export class CompleteLoginComponent implements OnInit {

  constructor(private authService:AuthService){}

  ngOnInit() {
    this.authService.loadUserProfile();
  }

}
