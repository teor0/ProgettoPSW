import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Order } from 'src/app/models/Order';
import { Product, ProductImpl } from 'src/app/models/Product';
import { OrderProductService } from 'src/app/services/ModelServices/OrderProduct.service';
import { UserService } from 'src/app/services/ModelServices/User.service';

@Component({
  selector: 'app-ProductDialog',
  templateUrl: './ProductDialog.component.html',
  styleUrls: ['./ProductDialog.component.css']
})
export class ProductDialogComponent implements OnInit{

  title='How many item?';
  productToAdd!: Product;
  value=1;
  logged!: boolean;

  constructor(@Inject(MAT_DIALOG_DATA) private data: {
                  productToAdd: Product;
              },private dialogRef: MatDialogRef<ProductDialogComponent>,
                private orderProductsService: OrderProductService, private userService:UserService){
                  this.productToAdd = data.productToAdd;
  }

  ngOnInit(): void{
    this.userService.logStatusChange.subscribe(async status=>{
      this.logged=status;
    });
  }

  addTocart(product:Product, quantity:number){
    var cartProduct= new ProductImpl();
    cartProduct=cartProduct.copy(product);
    cartProduct.quantity=quantity;
    if(!this.logged){
      var sp=JSON.parse(sessionStorage.getItem('storedProducts') as string) as Product[]
      sp.push(cartProduct);
      sessionStorage.setItem('storedProducts',JSON.stringify(sp));
    }
    else{
      var order=JSON.parse(sessionStorage.getItem('order') as string) as Order;
      this.orderProductsService.createOP(order,cartProduct,quantity);
    }
  }

  closeDialog() {
    this.dialogRef.close();
  }

}
