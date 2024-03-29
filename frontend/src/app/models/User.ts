export interface User{
  id:number,
  username:string,
  name: string,
  email: string,
  role:string
}

export class UserImpl implements User{
  public id:number;
  public username:string;
  public name: string;
  public email:string;
  public role:string;

  constructor(){
    this.id=0;
    this.username= '';
    this.name= '';
    this.email = '';
    this.role='';
  }

  copy(c:User) : UserImpl{
    this.username = c.username;
    this.name = c.name;
    this.email = c.email;
    this.role= c.role;
    return this;
  }
}

