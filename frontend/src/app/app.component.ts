import { OrderService } from './services/ModelServices/Order.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { UserService } from './services/ModelServices/User.service';
import { User } from './models/User';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  links= ['User', 'Product'];
  activeLink = "";
  logged!: boolean;
  isAdmin!: boolean;
  hasPending!: boolean;

  constructor(private userService:UserService, private orderService:OrderService){
  }

  logout(){
    this.userService.logout(JSON.parse(sessionStorage.getItem('user') as string)as User);
  }

  ngOnInit() {
    this.userService.logStatusChange.subscribe(async status=>{
      this.logged=status;
    });
    this.userService.adminStatusChange.subscribe(async status=>{
      this.isAdmin=status;
    });
    this.orderService.pendingChange.subscribe(async status=>{
      this.hasPending=status;
    })
    sessionStorage.setItem('storedProducts',JSON.stringify([]));
  }

  ngOnDestroy(){
    this.userService.logStatusChange.unsubscribe();
    this.userService.adminStatusChange.unsubscribe();
  }


}
