import { OrderProducts } from 'src/app/models/Orderproducts';
import { Component, ViewChild } from '@angular/core';
import { OrderProductService } from 'src/app/services/ModelServices/OrderProduct.service';
import { ResponseService } from '../../ResponseService.service';
import { Order } from 'src/app/models/Order';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { ProductService } from 'src/app/services/ModelServices/Product.service';
import { Product } from 'src/app/models/Product';

@Component({
  selector: 'app-ShowOrderProducts',
  templateUrl: './ShowOrderProducts.component.html',
  styleUrls: ['./ShowOrderProducts.component.css']
})
export class ShowOrderProductsComponent{

  orderProducts!:OrderProducts[];
  dataSource= new MatTableDataSource<OrderProducts>();
  displayedColumns: string[] = ['orderProductId','productId','quantity','price','action'];
  paginator!: MatPaginator;
  sort=new MatSort();
  hideRecap: boolean=true;
  hideMoreInfo: boolean=true;
  productToShow!: Product;

  //risoluzione visualizzazione della tabella grazie a https://github.com/angular/components/issues/10205
  @ViewChild(MatSort) set matSort(ms: MatSort) {
    this.sort = ms;
    this.setDataSourceAttributes();
  }

  @ViewChild(MatPaginator) set matPaginator(mp: MatPaginator) {
    this.paginator = mp;
    this.setDataSourceAttributes();
  }

  constructor(private orderProductsService:OrderProductService,private responseService:ResponseService,
            private productService:ProductService){
  }

  setDataSourceAttributes() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  //GETTERS
  getOPById(id:number){
    this.orderProductsService.getById(this.successShowInfo.bind(this),id);
  }

  getOrderProducts(){
    if(sessionStorage.getItem('order')!=null){
    var order=JSON.parse(sessionStorage.getItem('order') as string) as Order;
    this.orderProductsService.getByOrderId(this.success.bind(this),order);
    }
    else
      this.responseService.openDialogCustom('There aren\'t products in your order');
  }

  getTotal(): number{
    var tot=0.0;
    for(let op of this.orderProducts){
      tot+=op.price*op.quantity;
    }
    return tot;
  }

  private success(status:boolean,response:any){
    if(status){
      this.orderProducts=response;
      this.dataSource=new MatTableDataSource<OrderProducts>(this.orderProducts);
      this.dataSource.paginator=this.paginator;
      this.dataSource.sort=this.sort;
      if(this.hideRecap)
        this.hideRecap=!this.hideRecap;
    }
  }

  //DELETE
  delete(id:number){
    this.orderProductsService.deleteOP(id,this.successDelete.bind(this));
  }

  private successDelete(status:boolean,response:any){
    if(status){
      this.responseService.openDialogOk(response);
      this.getOrderProducts();
    }
  }


  //UPDATE
  updateQuantity(orderProducts: OrderProducts){
    this.responseService.openDialogUpdateOP(orderProducts);
  }

  //HIDE & SHOW
  showInfo(id:number){
    this.productService.getProductById(this.successShowInfo.bind(this),id);
  }

  private successShowInfo(status:boolean,response:any){
    if(status){
      this.productToShow=response;
      if(this.hideMoreInfo)
      this.hideMoreInfo=!this.hideMoreInfo;
    }
  }

  showRecap(){
    this.hideRecap=!this.hideRecap;
  }

  showMoreInfo(){
    this.hideMoreInfo=!this.hideMoreInfo;
  }



}
