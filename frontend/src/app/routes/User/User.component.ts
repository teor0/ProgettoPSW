import { Component} from '@angular/core';
import { User } from 'src/app/models/User';

@Component({
  selector: 'app-User',
  templateUrl: './User.component.html',
  styleUrls: ['./User.component.css']
})
export class UserComponent{

  user!: User;

  constructor() {
    this.user=JSON.parse(sessionStorage.getItem('user') as string) as User;
  }


}
