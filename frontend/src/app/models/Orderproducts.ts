import { Product } from "./Product";
import { Order } from "./Order";
export interface OrderProducts {
  id: number;
  orderId: number;
  productId: number;
  quantity: number;
  price: number;
}
export class OrderProductsImpl implements OrderProducts {
  public id: number;
  public orderId: number;
  public productId: number;
  public quantity: number;
  public price: number;

  constructor(order:Order, product:Product,quantity:number){
    this.id=1;
    this.orderId=order.id;
    this.productId=product.id;
    this.quantity=quantity;
    this.price=product.price;
  }

}
