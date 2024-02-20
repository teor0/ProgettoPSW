import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { KeycloakService } from 'keycloak-angular';
import { Order, OrderImpl } from 'src/app/models/Order';
import { Product, ProductImpl } from 'src/app/models/Product';
import { User } from 'src/app/models/User';
import { OrderService } from 'src/app/services/ModelServices/Order.service';
import { OrderProductService } from 'src/app/services/ModelServices/OrderProduct.service';

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
  user!:User;
  order!:Order;

  constructor(@Inject(MAT_DIALOG_DATA) private data: {productToAdd: Product;},
                    private dialogRef: MatDialogRef<ProductDialogComponent>, private orderService: OrderService,
                    private orderProductsService: OrderProductService, private keycloakService:KeycloakService){
        this.productToAdd = data.productToAdd;
        if(sessionStorage.getItem('order')!=null)
          this.order=JSON.parse(sessionStorage.getItem('order') as string) as Order;
        this.user=JSON.parse(sessionStorage.getItem('user') as string) as User;
  }

  async ngOnInit(){
    this.logged = await this.keycloakService.isLoggedIn();
  }

  addTocart(product:Product, quantity:number){
    var cartProduct= new ProductImpl();
    cartProduct=cartProduct.copy(product);
    cartProduct.quantity=quantity;
    if(!this.logged){
      var sp=JSON.parse(sessionStorage.getItem('storedProducts') as string) as Product[];
      if(sp.find(p=> p.id === product.id))
        sp.some(p=>{if(p.id === product.id){
                      p.quantity+=quantity;
                      sessionStorage.setItem('storedProducts',JSON.stringify(sp));
                    }
        })
      else{
        sp.push(cartProduct);
        sessionStorage.setItem('storedProducts',JSON.stringify(sp));
      }
    }
    else{
      if(sessionStorage.getItem('order')===null){
        this.order=new OrderImpl();
        this.order.user=this.user;
        this.orderService.createWithOP(this.order,cartProduct,quantity);
      }
      else{
        this.orderProductsService.create(this.order,cartProduct,quantity);
      }
    }
    this.closeAdd();
  }

  closeUndo(){
    this.closeDialog('Undo');
  }

  closeAdd(){
    this.closeDialog('Add');
  }

  closeDialog(action: 'Add' | 'Undo') {
    this.dialogRef.close(action);
  }

}
