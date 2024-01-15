import { Product, ProductImpl } from './../../models/Product';
import { Component, Inject } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ProductService } from 'src/app/services/ModelServices/Product.service';
import { ResponseService } from '../Response/ResponseService.service';

@Component({
  selector: 'app-UpdateDialog',
  templateUrl: './UpdateDialog.component.html',
  styleUrls: ['./UpdateDialog.component.css']
})
export class UpdateDialogComponent{

  title='Insert your update';
  productToUpdate!: Product;
  updateForm: FormControl;
  propertyToUpdate!:keyof ProductImpl;

  constructor(@Inject(MAT_DIALOG_DATA) private data: {
                  productToUpdate: Product;
                  propertyToUpdate: keyof ProductImpl;
              },private dialogRef: MatDialogRef<UpdateDialogComponent>,
                private productService:ProductService, private responseService:ResponseService){
                  if (data.productToUpdate)
                    this.productToUpdate = data.productToUpdate;
                  this.propertyToUpdate=data.propertyToUpdate;
                  this.updateForm= new FormControl('')
  }

  update<P extends keyof ProductImpl>(product:Product, fieldToUpdate: P, fieldUpdated:ProductImpl[P]){
    var updatedProduct=new ProductImpl();
    updatedProduct=updatedProduct.copy(product);
    updatedProduct[fieldToUpdate]=fieldUpdated;
    this.productService.update(this.updateSuccess.bind(this),updatedProduct,fieldToUpdate);
  }

  private updateSuccess(status:boolean){
    if(status)
      this.responseService.openDialogNoMsg();
  }

  closeDialog() {
    this.dialogRef.close();
  }

}
