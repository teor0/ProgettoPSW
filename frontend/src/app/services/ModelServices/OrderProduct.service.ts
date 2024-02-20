import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ResponseService } from 'src/app/helpers/Response/ResponseService.service';
import { ADDRESS_SERVER, REQUEST_ORDERPRODUCTS, REQUEST_SEARCH } from 'src/app/helpers/constants';
import { RestManager } from 'src/app/managers/RestManager';
import { Order } from 'src/app/models/Order';
import { OrderProducts, OrderProductsImpl } from 'src/app/models/Orderproducts';
import { Product } from 'src/app/models/Product';

@Injectable({
  providedIn: 'root'
})
export class OrderProductService {

  restManager: RestManager;

  constructor(private http:HttpClient, private responseService:ResponseService) {
    this.restManager=new RestManager(http);
  }

  //CREATE & DELETE
  create(order:Order,product:Product, desiredQuantity:number){
    this.getByOrderId(this.createOP.bind(this,order,product,desiredQuantity),order);
  }

  private createOP(order:Order,product:Product, desiredQuantity:number, status:boolean, response:OrderProducts[]){
    if(status){
      if(response.some(op=> op.productId === product.id))
          response.some((op)=>{ if(op.productId === product.id)
                                this.addQuantity(op,desiredQuantity);
                              });
      else{
        var orderProducts=new OrderProductsImpl(order,product,desiredQuantity);
        this.restManager.makePostRequest(ADDRESS_SERVER,REQUEST_ORDERPRODUCTS,this.creationSuccess.bind(this),orderProducts);
      }
    }
  }

  private addQuantity(orderProducts: OrderProducts, toAdd: number){
    orderProducts.quantity+=toAdd;
    this.updateOP(orderProducts);
  }


  deleteOP(id:number,callback:any){
    this.restManager.makeDeleteRequest(ADDRESS_SERVER,REQUEST_ORDERPRODUCTS+'/delete/'+id,callback)
  }

  private creationSuccess(order:Order,status:boolean,response:any){
    if(status){
      this.responseService.openDialogOk(response);
    }
  }

  //GETTERS
  getByOrderId(callback:any,order:Order){
    this.restManager.makeGetRequest(ADDRESS_SERVER,REQUEST_ORDERPRODUCTS+REQUEST_SEARCH,callback,{order:order.id});
  }

  getById(callback:any,id:number){
    this.restManager.makeGetPathRequest(ADDRESS_SERVER,REQUEST_ORDERPRODUCTS,callback,id);
  }

  //UPDATE
  updateOP(orderProducts:OrderProducts){
    this.restManager.makePutRequest(ADDRESS_SERVER,REQUEST_ORDERPRODUCTS+'/update',orderProducts);
  }




}
