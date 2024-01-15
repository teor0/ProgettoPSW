import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ResponseService } from 'src/app/helpers/Response/ResponseService.service';
import { ADDRESS_SERVER, REQUEST_PRODUCT, REQUEST_SEARCH } from 'src/app/helpers/constants';
import { RestManager } from 'src/app/managers/RestManager';
import { Product } from 'src/app/models/Product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  restManager: RestManager;

  constructor(private http:HttpClient, private responseService:ResponseService) {
    this.restManager=new RestManager(http);
  }

  //GETTERS

  getProductById(callback:any, id:number){
    this.restManager.makeGetPathRequest(ADDRESS_SERVER,REQUEST_PRODUCT+REQUEST_SEARCH,callback,id);
  }

  getProductByBarcode(callback: any,barcode:string){
    this.restManager.makeGetRequest(ADDRESS_SERVER,REQUEST_PRODUCT+REQUEST_SEARCH,callback,{barcode:barcode});
  }

  getProducts(): Observable<Product[]>{
    return this.http.request<Product[]>('get',ADDRESS_SERVER+REQUEST_PRODUCT+'/all');
  }

  getAllProducts(callback?:any){
    this.restManager.simpleMakeGetRequest(ADDRESS_SERVER,REQUEST_PRODUCT+'/all',callback);
  }

  getProductsByName(callback:any,name:string){
    this.restManager.makeGetRequest(ADDRESS_SERVER,REQUEST_PRODUCT+REQUEST_SEARCH,callback,{name:name});
  }

  getProductsByCategory(callback:any,category:string){
    this.restManager.makeGetRequest(ADDRESS_SERVER,REQUEST_PRODUCT+REQUEST_SEARCH,callback,{category:category});
  }

  getProductsByPriceGE(callback:any,priceGreaterEq:number){
    this.restManager.makeGetRequest(ADDRESS_SERVER,REQUEST_PRODUCT+REQUEST_SEARCH,callback,{priceGreaterEq:priceGreaterEq});
  }

  getProductsByPriceBetween(callback:any,min:number,max:number){
    this.restManager.makeGetRequest(ADDRESS_SERVER,REQUEST_PRODUCT+REQUEST_SEARCH,callback,{min:min, max:max});
  }

  getProductsByPriceLE(callback:any,priceLessEq:string){
    this.restManager.makeGetRequest(ADDRESS_SERVER,REQUEST_PRODUCT+REQUEST_SEARCH,callback,{priceLessEq:priceLessEq});
  }

  //PUT
  update(callback:any,product:Product,path:string){
    this.restManager.makePutRequest(ADDRESS_SERVER,REQUEST_PRODUCT+'/update/'+path,callback,product);
  }

  //CREATE & DELETE
  createProduct(product: Product){
    this.restManager.makePostRequest(ADDRESS_SERVER,REQUEST_PRODUCT,this.creationSuccess.bind(this),product);
  }

  private creationSuccess(status:boolean,response:any){
    if(status)
      this.responseService.openDialogOk(response);
  }

  deleteProduct(id:number,callback:any){
    this.restManager.makeDeleteRequest(ADDRESS_SERVER,REQUEST_PRODUCT+'/delete/'+id,callback);
  }



}
