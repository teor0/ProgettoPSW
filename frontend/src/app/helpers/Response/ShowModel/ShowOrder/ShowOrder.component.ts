import { OrderDTO } from 'src/app/models/Order';
import { Component, Input, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { OrderService } from 'src/app/services/ModelServices/Order.service';
import { User } from 'src/app/models/User';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { ResponseService } from '../../ResponseService.service';

@Component({
  selector: 'app-ShowOrder',
  templateUrl: './ShowOrder.component.html',
  styleUrls: ['./ShowOrder.component.css']
})
export class ShowOrderComponent implements OnInit, AfterViewInit{

  userOrders!:OrderDTO[];
  @Input('user') user!:User;
  dataSource= new MatTableDataSource<OrderDTO>();
  displayedColumns: string[] = ['id','total','createDate','status','action'];
  paginator!: MatPaginator;
  sort!: MatSort;
  hideRecap: boolean=false;

  //risoluzione visualizzazione della tabella grazie a https://github.com/angular/components/issues/10205
  @ViewChild(MatSort) set matSort(ms: MatSort) {
    this.sort = ms;
    this.setDataSourceAttributes();
  }

  @ViewChild(MatPaginator) set matPaginator(mp: MatPaginator) {
    this.paginator = mp;
    this.setDataSourceAttributes();
  }

  constructor(private orderService:OrderService, private responseService:ResponseService){}

  ngOnInit(){
    this.dataSource=new MatTableDataSource<OrderDTO>();
    this.dataSource.paginator=this.paginator;
    this.dataSource.sort = this.sort;
  }

  ngAfterViewInit(){
    this.dataSource.sort = this.sort;
  }

  setDataSourceAttributes() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }


  //DELETE
  delete(id:number){
    this.orderService.deleteOrder(id,this.successDelete.bind(this));
  }

  private successDelete(status:boolean,response:any){
    if(status){
      this.responseService.openDialogOk(response);
      sessionStorage.removeItem('order');
      this.refreshTable();
    }
  }

  //SEARCH METHODS
  searchByUserPending(){
    this.orderService.getByUserAndPending(this.showResult.bind(this),this.user);
  }

  searchByUser(){
    this.orderService.getByUser(this.showResult.bind(this),this.user);
  }

  showResult(status:boolean, response:any){
    if(status){
      this.userOrders=response as OrderDTO[];
      this.dataSource=new MatTableDataSource<OrderDTO>(this.userOrders);
      this.dataSource.paginator=this.paginator;
      this.dataSource.sort=this.sort;
      if(!this.hideRecap)
        this.hideRecap=!this.hideRecap;
    }
  }

  private refreshTable(){
    this.orderService.getByUser((status:boolean,response:any)=>{
      if(status){
      this.userOrders=response as OrderDTO[];
      this.dataSource=new MatTableDataSource<OrderDTO>(this.userOrders);
      this.dataSource.paginator=this.paginator;
      this.dataSource.sort=this.sort;}},this.user);
}

  hideOrders(){
    this.hideRecap=!this.hideRecap;
  }



}
