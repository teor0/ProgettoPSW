import { OrderProducts } from "./Orderproducts";
import { User, UserImpl } from "./User";
export interface OrderDTO {
  id: number;
  userId: User;
  total: number;
  createDate: Date;
  status: string;
  orderProducts: OrderProducts[];
}

export interface Order {
  id: number;
  user: User;
  total: number;
  createDate: Date;
  status: string;
  orderProducts: OrderProducts[];
}

export class OrderImpl implements Order{
  public id: number;
  public user: User;
  public total: number;
  public createDate: Date;
  public status: string;
  public orderProducts: OrderProducts[];

  constructor(){
    this.id=1;
    this.user=new UserImpl();
    this.total=0.0;
    this.createDate=new Date();
    this.status='Pending';
    this.orderProducts=[];
  }

  copy(c:Order) : OrderImpl{
    this.id=c.id;
    this.user=c.user;
    this.total=c.total;
    this.createDate=c.createDate;
    this.status=c.status;
    this.orderProducts=c.orderProducts;
    return this;
  }

  copyDTO(c:OrderDTO) : OrderImpl{
    this.id=c.id;
    this.user=c.userId;
    this.total=c.total;
    this.createDate=c.createDate;
    this.status=c.status;
    this.orderProducts=c.orderProducts;
    return this;
  }
}

export class OrderDTOImpl implements OrderDTO{
  public id: number;
  public userId: User;
  public total: number;
  public createDate: Date;
  public status: string;
  public orderProducts: OrderProducts[];

  constructor(){
    this.id=1;
    this.userId=new UserImpl();
    this.total=0.0;
    this.createDate=new Date();
    this.status='Pending';
    this.orderProducts=[];
  }

  copy(c:OrderDTO) : OrderDTOImpl{
    this.id=c.id;
    this.userId=c.userId;
    this.total=c.total;
    this.createDate=c.createDate;
    this.status=c.status;
    this.orderProducts=c.orderProducts;
    return this;
  }

  copyOrder(c:Order) : OrderDTOImpl{
    this.id=c.id;
    this.userId=c.user;
    this.total=c.total;
    this.createDate=c.createDate;
    this.status=c.status;
    this.orderProducts=c.orderProducts;
    return this;
  }

}
