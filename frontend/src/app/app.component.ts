import { OrderService } from './services/ModelServices/Order.service';
import { Component, OnInit } from '@angular/core';
import { UserService } from './services/ModelServices/User.service';
import { AuthService } from './services/Auth/AuthService.service';
import { KeycloakService } from 'keycloak-angular';
import { User } from './models/User';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  activeLink = "";
  logged!: boolean;
  isAdmin!: boolean;
  hasPending!: boolean;

  constructor(private keycloakService: KeycloakService, private orderService:OrderService, private authService:AuthService){
  }

  async ngOnInit() {
    this.orderService.pendingChange.subscribe(async status=>{
      this.hasPending=status;
    });
    sessionStorage.setItem('storedProducts',JSON.stringify([]));
    this.logged = await this.keycloakService.isLoggedIn();
    if(this.logged){
      this.isAdmin=this.authService.isAdmin();
    }
  }

  logout(){
    this.authService.logout();
    console.log(this.logged);
    //this.logged=false;
  }

}
