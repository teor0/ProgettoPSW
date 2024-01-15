import { Component, OnInit, AfterViewInit } from '@angular/core';
import { Order, OrderImpl } from 'src/app/models/Order';
import { User } from 'src/app/models/User';
import { OrderService } from 'src/app/services/ModelServices/Order.service';

@Component({
  selector: 'app-Order',
  templateUrl: './Order.component.html',
  styleUrls: ['./Order.component.css']
})
export class OrderComponent implements OnInit, AfterViewInit{

  user!: User;
  order!:Order;
  hasPending!: boolean;

  constructor(private orderService:OrderService) {
    this.order=new OrderImpl();
    this.user=JSON.parse(sessionStorage.getItem('user') as string) as User;
    this.order.user=this.user;
    this.retrievePreviousOrder();
  }

  ngOnInit(){
  }

  ngAfterViewInit(){
    this.orderService.pendingChange.subscribe(async status=>{
      this.hasPending=status;
    })
  }

  newOrder(){
    this.orderService.createOrder(this.order);
  }

  retrievePreviousOrder(){
    this.orderService.getByUserAndPendingNoCallback(this.user);
  }




}
