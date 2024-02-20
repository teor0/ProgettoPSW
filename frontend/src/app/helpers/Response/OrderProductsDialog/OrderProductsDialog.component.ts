import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { OrderProducts } from 'src/app/models/Orderproducts';
import { OrderProductService } from 'src/app/services/ModelServices/OrderProduct.service';

@Component({
  selector: 'app-OrderProductsDialog',
  templateUrl: './OrderProductsDialog.component.html',
  styleUrls: ['./OrderProductsDialog.component.css']
})
export class OrderProductsDialogComponent{

  title='How many item?';
  orderProductsToUpdate!: OrderProducts;
  value=1;

  constructor(@Inject(MAT_DIALOG_DATA) private data: {orderProductsToUpdate: OrderProducts;},
                    private dialogRef: MatDialogRef<OrderProductsDialogComponent>,
                    private orderProductsService: OrderProductService){
        this.orderProductsToUpdate = data.orderProductsToUpdate;
  }

  update(orderProducts:OrderProducts, quantity:number){
    orderProducts.quantity=quantity;
    this.orderProductsService.updateOP(orderProducts)
    this.closeSuccess();
  }


  closeUndo(){
    this.closeDialog('Undo');
  }

  closeSuccess(){
    this.closeDialog('Success');
  }

  closeDialog(action: 'Success' | 'Undo') {
    this.dialogRef.close(action);
  }

}
