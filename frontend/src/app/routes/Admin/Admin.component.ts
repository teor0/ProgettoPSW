import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorIntl } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ResponseService } from 'src/app/helpers/Response/ResponseService.service';
import { User } from 'src/app/models/User';
import { UserService } from 'src/app/services/ModelServices/User.service';

@Component({
  selector: 'app-Admin',
  templateUrl: './Admin.component.html',
  styleUrls: ['./Admin.component.css']
})
export class AdminComponent implements OnInit{

  users!:User[];
  dataSource!: MatTableDataSource<User>;
  displayedColumns: string[] = ['id', 'username', 'name', 'email','role','action'];
  @ViewChild(MatPaginator) paginator=new MatPaginator(new MatPaginatorIntl(), ChangeDetectorRef.prototype);
  @ViewChild(MatSort) sort=new MatSort();

  constructor(private userService: UserService, private responseService:ResponseService) {
  }

  ngOnInit(): void {
    this.userService.getUsers().subscribe(response=>
      {
       this.users=response;
       this.dataSource= new MatTableDataSource<User>(this.users);
       this.dataSource.paginator=this.paginator;
       this.dataSource.sort=this.sort;
      });
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
  }




  deleteUser(id:number){
    this.userService.deleteUser(id,this.refreshTable.bind(this));
  }

  private refreshTable(status: boolean,response:any){
    if(status)
    {
      this.responseService.openDialogOk(response);
      this.userService.getUsers().subscribe(response=>
      {
       this.users=response;
       this.dataSource= new MatTableDataSource<User>(this.users);
      });
    }
  }


}
