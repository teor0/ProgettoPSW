import { OrderService } from './services/ModelServices/Order.service';
import { Component, OnInit } from '@angular/core';
import { AuthService } from './services/Auth/AuthService.service';
import { KeycloakService } from 'keycloak-angular';

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
    if(sessionStorage.getItem('storedProducts')===null)
      sessionStorage.setItem('storedProducts',JSON.stringify([]));
  }

  async ngOnInit() {
    this.orderService.pendingChange.subscribe(async status=>{
      this.hasPending=status;
    });
    this.logged = await this.keycloakService.isLoggedIn();
    if(this.logged){
      this.isAdmin=this.authService.isAdmin();
    }
  }

  logout(){
    this.authService.logout();
  }

}
