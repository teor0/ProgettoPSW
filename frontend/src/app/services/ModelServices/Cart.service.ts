import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ResponseService } from 'src/app/helpers/Response/ResponseService.service';
import { ADDRESS_SERVER, REQUEST_CART } from 'src/app/helpers/constants';
import { RestManager } from 'src/app/managers/RestManager';
import { Cart } from 'src/app/models/Cart';
import { Order, OrderDTO, OrderDTOImpl } from 'src/app/models/Order';
import { User } from 'src/app/models/User';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  restManager:RestManager;

  constructor(private http:HttpClient, private responseService:ResponseService){
    this.restManager=new RestManager(http);
  }

  //INITIALIZATION METHODS
  initialSetCart(order:OrderDTO){
    this.restManager.makePutRequest(ADDRESS_SERVER,REQUEST_CART+'/refresh',order,this.setSuccess.bind(this));
  }

  private setSuccess(status:boolean, response:any){
    if(status)
      sessionStorage.setItem('cart',JSON.stringify(response));
  }

  initialGetCart(user:User){
    this.restManager.makeGetPathRequest(ADDRESS_SERVER,REQUEST_CART+'/show',this.retrieveSuccess.bind(this,user),user.id);
  }

  private retrieveSuccess(user:User,status:boolean, response:Cart){
    if(status){
      if(response.id===0){
        this.createCart(user,JSON.parse(sessionStorage.getItem('order') as string) as Order);
      }
      else{
        this.initialSetCart(JSON.parse(sessionStorage.getItem('orderDTO') as string) as OrderDTO);
      }
    }
  }

  //CREATE & DELETE
  createCart(user:User,order:Order){
    this.restManager.makePostRequest(ADDRESS_SERVER,REQUEST_CART,this.creationSuccess.bind(this,order),user)
  }

  private creationSuccess(order:Order,status:boolean,response:any){
    if(status){
      var orderDTO=new OrderDTOImpl();
      orderDTO=orderDTO.copyOrder(order);
      this.initialSetCart(orderDTO);
    }
  }

  deleteCart(id:number){
    this.restManager.makeDeleteRequest(ADDRESS_SERVER,REQUEST_CART+'/delete/'+id,this.deleteSuccess.bind(this));
  }

  private deleteSuccess(status:boolean,response:any){
    if(status){
      this.responseService.openDialogNoMsg();
      sessionStorage.removeItem('order');
      sessionStorage.removeItem('orderDTO');
      sessionStorage.setItem('cart',JSON.stringify(response));
    }
  }

  //GETTERS & SETTERS
  getCart(callback:any,user:User){
    this.restManager.makeGetPathRequest(ADDRESS_SERVER,REQUEST_CART+'/show',callback,user.id);
  }

  setCart(callback:any,order:OrderDTO){
    this.restManager.makePutRequest(ADDRESS_SERVER,REQUEST_CART+'/refresh',order,callback);
  }

  //CHECKOUT METHOD
  checkout(callback:any,user:User){
    this.restManager.makePostRequest(ADDRESS_SERVER,REQUEST_CART+'/checkout',callback,user);
  }

}
