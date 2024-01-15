import { Order } from './Order';
import { User } from './User';
export interface Cart {
  id: number;
  user: number;
  order: number;
}

export class CartImpl implements Cart{
  public id: number;
  public user: number;
  public order: number;

  constructor(user:User,order:Order){
    this.id=1;
    this.user=user.id;
    this.order=order.id;
  }

}
