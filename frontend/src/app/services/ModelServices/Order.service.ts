import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';
import { ResponseService } from 'src/app/helpers/Response/ResponseService.service';
import { ADDRESS_SERVER, REQUEST_ORDER, REQUEST_SEARCH } from 'src/app/helpers/constants';
import { RestManager } from 'src/app/managers/RestManager';
import { Order, OrderDTO, OrderImpl } from 'src/app/models/Order';
import { User, UserDTOImpl } from 'src/app/models/User';
import { CartService } from './Cart.service';
import { Product } from 'src/app/models/Product';
import { OrderProductService } from './OrderProduct.service';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  restManager: RestManager;
  hasPendingOrder: boolean = false;
  pendingChange: Subject<boolean> = new BehaviorSubject<boolean>(this.hasPendingOrder);

  constructor(private http:HttpClient, private responseService:ResponseService, private cartService:CartService,
              private orderProductsService:OrderProductService) {
    this.restManager=new RestManager(http);
    this.pendingChange.subscribe(async status=>{
    this.hasPendingOrder=status;
    });
  }


  //CREATION & DELETE
  createOrder(order:Order){
    this.restManager.makePostRequest(ADDRESS_SERVER,REQUEST_ORDER,this.creationSuccess.bind(this,order),order)
  }

  private creationSuccess(order:Order,status:boolean,response:any){
    if(status){
      this.responseService.openDialogOk(response);
      this.getByUserAndPendingNoCallback(JSON.parse(sessionStorage.getItem('user') as string) as User);
    }
  }

  deleteOrder(id:number,callback:any){
    this.restManager.makeDeleteRequest(ADDRESS_SERVER,REQUEST_ORDER+'/delete/'+id,callback)
    if(this.hasPendingOrder)
      this.pendingChange.next(!this.hasPendingOrder);
    sessionStorage.removeItem('order');
    sessionStorage.removeItem('orderDTO');
  }

  //GETTERS
  getByUser(callback:any,user:User){
    this.restManager.makeGetRequest(ADDRESS_SERVER,REQUEST_ORDER+REQUEST_SEARCH,callback,{user:user.id});
  }

  getByUserAndPending(callback:any,user:User){
    this.restManager.makeGetRequest(ADDRESS_SERVER,REQUEST_ORDER+REQUEST_SEARCH,callback,{user:user.id, status:'Pending'});
  }

  getByUserAndPendingNoCallback(user:User){
    this.restManager.makeGetRequest(ADDRESS_SERVER,REQUEST_ORDER+REQUEST_SEARCH,this.retrieveSuccess.bind(this),{user:user.id, status:'Pending'});
  }

  private retrieveSuccess(status:boolean,response:OrderDTO[]){
    if(status && response.length!=0){
      var orderD=response[0];
      var order=new OrderImpl();
      order=order.copyDTO(orderD);
      sessionStorage.setItem('order',JSON.stringify(order));
      sessionStorage.setItem('orderDTO',JSON.stringify(orderD));
      if(!this.hasPendingOrder)
        this.pendingChange.next(!this.hasPendingOrder);
      var storedProducts=JSON.parse(sessionStorage.getItem('storedProducts') as string) as Product[];
      if(storedProducts.length!=0){
        for(let p of storedProducts)
          this.orderProductsService.createOP(order,p,p.quantity);
        sessionStorage.setItem('storedProducts',JSON.stringify([]));
      }
      this.cartService.initialGetCart(JSON.parse(sessionStorage.getItem('user') as string) as User);
    }
  }

}
