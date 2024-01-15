import { UserService } from './../../../services/ModelServices/User.service';
import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Product } from 'src/app/models/Product';
import { User } from 'src/app/models/User';

@Component({
  selector: 'app-ShowModel',
  templateUrl: './ShowModel.component.html',
  styleUrls: ['./ShowModel.component.css']
})
export class ShowModelComponent{

  userToDisplay: User | undefined;
  users!: User[];
  product!: Product;
  search: FormGroup;
  hideSearchUser=true;

  constructor(private userService:UserService){
    this.search= new FormGroup({
      searchControl: new FormControl(null)
    })
  }

  searchByUsername(){
    this.userService.getUserByUsername(this.search.controls['searchControl'].value,this.showUser.bind(this));
  }

  searchByEmail(){
    this.userService.getUserByEmail(this.showUser.bind(this),this.search.controls['searchControl'].value);
  }

  searchByName(){
    this.userService.getUsersByName(this.showUsers.bind(this),this.search.controls['searchControl'].value);
  }

  showUser(status:boolean, response:any){
    if(status)
      this.userToDisplay=response;
  }

  showUsers(status:boolean, response:any){
    if(status)
      this.users=response;
  }

  showSearchUser(){
    this.hideSearchUser=!this.hideSearchUser;
  }




}
