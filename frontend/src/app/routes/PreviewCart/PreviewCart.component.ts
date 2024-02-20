import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ResponseService } from 'src/app/helpers/Response/ResponseService.service';
import { Product } from 'src/app/models/Product';
import { OrderProductService } from 'src/app/services/ModelServices/OrderProduct.service';

@Component({
  selector: 'app-PreviewCart',
  templateUrl: './PreviewCart.component.html',
  styleUrls: ['./PreviewCart.component.css']
})
export class PreviewCartComponent implements OnInit{

  products!:Product[];
  dataSource= new MatTableDataSource<Product>();
  displayedColumns: string[] = ['id','barCode','name','category','description','quantity','price','action'];
  paginator!: MatPaginator;
  sort!: MatSort;
  hideRecap: boolean=true;

  //risoluzione visualizzazione della tabella grazie a https://github.com/angular/components/issues/10205
  @ViewChild(MatSort) set matSort(ms: MatSort) {
    this.sort = ms;
    this.setDataSourceAttributes();
  }

  @ViewChild(MatPaginator) set matPaginator(mp: MatPaginator) {
    this.paginator = mp;
    this.setDataSourceAttributes();
  }

  constructor(private orderProductsService:OrderProductService,private responseService:ResponseService){
    this.products=JSON.parse(sessionStorage.getItem('storedProducts') as string) as Product[];
  }

  ngOnInit(): void {
    this.dataSource=new MatTableDataSource<Product>(this.products);
    this.setDataSourceAttributes();
  }

  setDataSourceAttributes() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  //GETTER
  getTotal(): number{
    var tot=0.0;
    for(let op of this.products){
      tot+=op.price*op.quantity;
    }
    return tot;
  }


  //DELETE
  delete(id:number){
    var temp= [] as Product[];
    for(let p of this.products){
      if(p.id!=id)
        temp.push(p);
    }
    this.products=temp;
    sessionStorage.setItem('storedProducts', JSON.stringify(this.products));
    this.refresh();
  }

  //UPDATE
  updateQuantity(product:Product){
    this.responseService.openDialogAddCartCB(product,this.refresh.bind(this));

  }

  private refresh(){
    this.products=JSON.parse(sessionStorage.getItem('storedProducts') as string) as Product[];
    this.dataSource=new MatTableDataSource<Product>(this.products);
    this.setDataSourceAttributes();
  }



}
