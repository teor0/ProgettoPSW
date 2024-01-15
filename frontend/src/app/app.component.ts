import { Component, OnDestroy, OnInit } from '@angular/core';
import { UserService } from './services/ModelServices/User.service';
import { CartService } from './services/ModelServices/Cart.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit,OnDestroy {
  links= ['User', 'Product'];
  activeLink = "";
  logged!: boolean;
  isAdmin!: boolean;

  constructor(private userService:UserService){
  }

  logout(){
    this.userService.logout(JSON.parse(sessionStorage.getItem('user') as string).username);
  }

  ngOnInit() {
    this.userService.logStatusChange.subscribe(async status=>{
      this.logged=status;
    });
    this.userService.adminStatusChange.subscribe(async status=>{
      this.isAdmin=status;
    });
    sessionStorage.setItem('storedProducts',JSON.stringify([]));
  }

  ngOnDestroy(){
    this.userService.logStatusChange.unsubscribe();
    this.userService.adminStatusChange.unsubscribe();
  }


}
