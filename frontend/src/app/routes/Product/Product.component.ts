import { AfterViewInit, ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatPaginator, MatPaginatorIntl } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ResponseService } from 'src/app/helpers/Response/ResponseService.service';
import { Product } from 'src/app/models/Product';
import { ProductService } from 'src/app/services/ModelServices/Product.service';

@Component({
  selector: 'app-Product',
  templateUrl: './Product.component.html',
  styleUrls: ['./Product.component.css']
})
export class ProductComponent implements OnInit, AfterViewInit {

  sliderValue=1;
  product: Product | undefined;
  productsSearch!: Product[];
  dataSourceSearch= new MatTableDataSource<Product>();
  paginatorSearch!: MatPaginator;
  sortSearch!: MatSort;

  //risoluzione visualizzazione della tabella grazie a https://github.com/angular/components/issues/10205
  @ViewChild(MatSort) set matSort(ms: MatSort) {
    this.sortSearch = ms;
    this.setDataSourceAttributes();
  }

  @ViewChild(MatPaginator) set matPaginator(mp: MatPaginator) {
    this.paginatorSearch = mp;
    this.setDataSourceAttributes();
  }

  search: FormGroup;
  hideSearchProduct: boolean=true;
  hideTab: boolean=true;
  products!:Product[];
  dataSource=new  MatTableDataSource<Product>();
  displayedColumns: string[] = ['id','barCode','name','category','price','description','quantity','action'];
  @ViewChild(MatPaginator) paginator=new MatPaginator(new MatPaginatorIntl(), ChangeDetectorRef.prototype);
  @ViewChild(MatSort) sort=new MatSort();
  showAll=true;


  constructor(private productService:ProductService, private responseService:ResponseService,){
                this.search= new FormGroup({
                  searchControl: new FormControl(null)
                })
  }

  ngOnInit(): void {
    this.productService.getProducts().subscribe(response=>{
      this.products=response;
      this.dataSource=new MatTableDataSource<Product>(this.products);
      this.dataSource.paginator=this.paginator;
      this.dataSource.sort = this.sort;
    });
    this.dataSourceSearch=new MatTableDataSource<Product>();
    this.dataSourceSearch.paginator=this.paginatorSearch;
    this.dataSourceSearch.sort = this.sortSearch;
  }

  ngAfterViewInit(){
    this.dataSource.sort = this.sort;
  }

  setDataSourceAttributes() {
    this.dataSourceSearch.paginator = this.paginatorSearch;
    this.dataSourceSearch.sort = this.sortSearch;
    if (this.paginatorSearch && this.sortSearch) {
      this.applyFilter('');
    }
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSourceSearch.filter = filterValue;
  }

  searchByBarcode(){
    this.productService.getProductByBarcode(this.showProduct.bind(this),this.search.controls['searchControl'].value);
  }

  searchByName(){
    this.productService.getProductsByName(this.showProducts.bind(this),this.search.controls['searchControl'].value);
  }

  addToCart(product:Product){
    this.responseService.openDialogAddCart(product);
  }

  //HIDE AND SHOW
  hideAll(){
    this.showAll=!this.showAll;
  }

  showProduct(status:boolean, response:Product){
    if(status)
      this.product=response;
  }

  private showProducts(status:boolean, response:Product[]){
    if(status){
      this.productsSearch=response;
      this.dataSourceSearch=new MatTableDataSource<Product>(this.productsSearch);
      this.dataSourceSearch.paginator=this.paginatorSearch;
      this.dataSourceSearch.sort=this.sortSearch;
      if(this.hideTab)
        this.hideTab=!this.hideTab;
    }
  }

  showSearchProduct(){
    this.hideSearchProduct=!this.hideSearchProduct;
  }

  collapse(){
    this.product=undefined;
  }

  collapseSearchTab(){
    this.hideSearchProduct=!this.hideSearchProduct;
  }

}
