export interface Product {
  id: number;
  barCode: string;
  name: string;
  category: string;
  price: number;
  description: string;
  quantity: number;
}
export class ProductImpl implements Product{
  public id: number;
  public barCode: string;
  public name: string;
  public category: string;
  public price: number;
  public description: string;
  public quantity: number;

  constructor(){
    this.id=1;
    this.barCode='';
    this.name='';
    this.category='';
    this.price=0.0;
    this.description='';
    this.quantity=0;
  }

  copy(p:Product) : ProductImpl{
    this.id=p.id;
    this.barCode=p.barCode;
    this.name=p.name;
    this.price=p.price;
    this.description=p.description;
    this.quantity=p.quantity;
    return this;
  }


}
