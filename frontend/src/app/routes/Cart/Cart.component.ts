import { OrderDTOImpl } from './../../models/Order';
import { Component, OnInit } from '@angular/core';
import { ResponseService } from 'src/app/helpers/Response/ResponseService.service';
import { Cart } from 'src/app/models/Cart';
import { Order } from 'src/app/models/Order';
import { User } from 'src/app/models/User';
import { CartService } from 'src/app/services/ModelServices/Cart.service';
import { OrderService } from 'src/app/services/ModelServices/Order.service';

@Component({
  selector: 'app-Cart',
  templateUrl: './Cart.component.html',
  styleUrls: ['./Cart.component.css']
})
export class CartComponent implements OnInit{


  user!:User;
  hasOrder!: boolean;
  constructor(private cartService:CartService, private responseService:ResponseService, private orderService:OrderService){
    this.user=JSON.parse(sessionStorage.getItem('user') as string) as User;
  }

  ngOnInit() {
    this.retrieveOrder();
    this.refreshCart();
  }

  acquire(){
    this.cartService.checkout(this.acquireSuccess.bind(this),this.user);
  }

  private acquireSuccess(status:boolean,response:any){
    if(status){
      this.responseService.openDialogOk(response);
      sessionStorage.removeItem('order');
      sessionStorage.removeItem('orderDTO');
      this.hasOrder=false;
    }
  }

  delete(){
    this.cartService.deleteCart(this.user.id);
    this.hasOrder=false;
  }

  private retrieveOrder(){
    this.orderService.getByUserAndPendingNoCallback(this.user);
  }

  private refreshCart(){
    var order=new OrderDTOImpl();
    order=order.copyOrder(JSON.parse(sessionStorage.getItem('order') as string) as Order);
    this.cartService.setCart(this.retrieveSuccess.bind(this),order);
  }

  private retrieveSuccess(status:boolean,response:Cart){
    if(status){
      sessionStorage.setItem('cart',JSON.stringify(response));
      this.hasOrder=true;
    }
  }


}
