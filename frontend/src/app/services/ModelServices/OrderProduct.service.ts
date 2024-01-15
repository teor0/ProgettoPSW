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
  createOP(order:Order,product:Product, desiredQuantity:number){
    var orderProducts=new OrderProductsImpl(order,product,desiredQuantity);
    this.restManager.makePostRequest(ADDRESS_SERVER,REQUEST_ORDERPRODUCTS,this.creationSuccess.bind(this),orderProducts);
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



}
