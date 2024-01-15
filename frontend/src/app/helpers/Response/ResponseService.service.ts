import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ResponseComponent } from './Response.component';
import { Product } from 'src/app/models/Product';
import { ProductDialogComponent } from './ProductDialog/ProductDialog.component';
import { RestResponse } from 'src/app/models/RestResponse';
import { UpdateDialogComponent } from '../UpdateDialog/UpdateDialog.component';

@Injectable({
  providedIn: 'root'
})

export class ResponseService {

  constructor(private dialog: MatDialog) {}

  openDialogError(error: HttpErrorResponse): void {
    this.dialog.open(ResponseComponent,{
      data: {title:'ERROR',msg:error.error.msg},
    });
  }

  openDialogOk(response: RestResponse): void{
    if(response){
      this.dialog.open(ResponseComponent,{
        data:{title:'Success',msg:response.msg},
      })
    }
  }

  openDialogCustom(customMsg:string): void{
    this.dialog.open(ResponseComponent,{
      data:{msg:customMsg},
    })
  }

  openDialogNoMsg(): void{
    this.dialog.open(ResponseComponent,{
      data:{title:'Success'},
    })
  }

  openDialogCartOk(): void{
    this.dialog.open(ResponseComponent,{
      data:{title:'Added to cart'},
    })
  }

  openDialogAddCart(product: Product,):void {
    this.dialog.open(ProductDialogComponent,{
      data:{productToAdd:product}
    }).afterClosed().subscribe((resp)=>this.openDialogCartOk());
  }

  openDialogUpdate(product: Product, property:string, callback:any):void {
    this.dialog.open(UpdateDialogComponent,{
      data:{productToUpdate:product, propertyToUpdate:property}
    }).afterClosed().subscribe((response)=>{callback()});
  }



}
