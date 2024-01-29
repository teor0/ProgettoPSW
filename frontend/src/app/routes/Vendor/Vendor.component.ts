import { ProductService } from 'src/app/services/ModelServices/Product.service';
import { User } from './../../models/User';
import { Component, Input, ViewChild} from '@angular/core';
import { Product } from 'src/app/models/Product';
import { FormControl, FormGroup } from '@angular/forms';
import { ResponseService } from 'src/app/helpers/Response/ResponseService.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';

@Component({
  selector: 'app-Vendor',
  templateUrl: './Vendor.component.html',
  styleUrls: ['./Vendor.component.css']
})
export class VendorComponent{

  @Input('user') user!: User;
  hideUpdateProduct: boolean=true;
  product!: Product;
  searchResult!: Product | undefined;
  productForm: FormGroup;
  searchForm: FormGroup;
  searchBetweenForm: FormGroup;
  hideForm=true;
  hideSearch=true;
  hideResult=true;
  hidePriceButtons: boolean=true;
  hideButtons: boolean=true;

  productsSearch!: Product[];
  dataSourceSearch= new MatTableDataSource<Product>();
  paginatorSearch!: MatPaginator;
  sortSearch!: MatSort;
  hideTab: boolean=true;
  displayedColumns: string[] = ['id','barCode','name','category','price','description','quantity','action'];

  //risoluzione visualizzazione della tabella grazie a https://github.com/angular/components/issues/10205
  @ViewChild(MatSort) set matSort(ms: MatSort) {
    this.sortSearch = ms;
    this.setDataSourceAttributes();
  }

  @ViewChild(MatPaginator) set matPaginator(mp: MatPaginator) {
    this.paginatorSearch = mp;
    this.setDataSourceAttributes();
  }

  constructor(private productService:ProductService, private responseService: ResponseService) {
    this.productForm=new FormGroup({
      id: new FormControl(1),
      username: new FormControl(null),
      barCode: new FormControl(null),
      name: new FormControl(null),
      category: new FormControl(null),
      price: new FormControl(null),
      description: new FormControl(null),
      quantity: new FormControl(null)
    }),
    this.searchForm=new FormGroup({
      searchingForm: new FormControl(null)
    }),
    this.searchBetweenForm=new FormGroup({
      priceForm: new FormControl(1),
      minForm: new FormControl(0),
      maxForm: new FormControl(10000)
    })
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

  createProduct(){
    this.product=this.productForm.value
    this.productService.createProduct(this.product);
  }

  deleteProduct(id:number){
    this.productService.deleteProduct(id,this.hideProduct.bind(this));
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

  delete(id:number){
    this.productService.deleteProduct(id,this.successDelete.bind(this));
  }

  private successDelete(status:boolean,response:any){
    if(status)
      this.responseService.openDialogOk(response);
  }

  //SEARCH
  searchByName(){
    this.productService.getProductsByName(this.showProducts.bind(this),this.searchForm.controls['searchingForm'].value);
  }

  searchByBarcode(){
    this.productService.getProductByBarcode(this.showProduct.bind(this),this.searchForm.controls['searchingForm'].value)
  }

  searchByCat(){
    this.productService.getProductsByCategory(this.showProducts.bind(this),this.searchForm.controls['searchingForm'].value);
  }

  searchByPriceLE(){
    this.productService.getProductsByPriceLE(this.showProducts.bind(this),this.searchBetweenForm.controls['priceForm'].value);
  }

  searchByPriceGE(){
    this.productService.getProductsByPriceGE(this.showProducts.bind(this),this.searchBetweenForm.controls['priceForm'].value);
  }

  searchByPriceBetween(){
    this.productService.getProductsByPriceBetween(this.showProducts.bind(this),this.searchBetweenForm.controls['minForm'].value, this.searchBetweenForm.controls['maxForm'].value);
  }

  //UPDATE
  updateBarcode(product: Product){
    this.responseService.openDialogUpdate(product,'barCode',this.searchByName.bind(this));
  }

  updateName(product: Product){
    this.responseService.openDialogUpdate(product,'name',this.searchByName.bind(this));
  }

  updatePrice(product: Product){
    this.responseService.openDialogUpdate(product,'price',this.searchByPriceGE.bind(this));
  }

  updateQuantity(product: Product){
    this.responseService.openDialogUpdate(product,'quantity',this.searchByName.bind(this));
  }

  updateDescription(product: Product){
    this.responseService.openDialogUpdate(product,'description',this.searchByName.bind(this));
  }

  updateCategory(product: Product){
    this.responseService.openDialogUpdate(product,'category',this.searchByCat.bind(this));
  }

  //SHOW AND HIDE
  showFormCreate(){
    this.hideForm=!this.hideForm;
    if(!this.hideSearch)
      this.hideSearch=!this.hideSearch;
  }

  hideTable(){
    this.hideTab=!this.hideTab;
  }

  hideSearchResult(){
    this.searchResult=undefined;
  }

  showSearch(){
    if(!this.hideForm)
      this.hideForm=!this.hideForm;
    if(this.hideSearch)
      this.hideSearch=!this.hideSearch;
    this.hidePriceButtons=true;
    this.hideButtons=false;
  }

  hideSearchForm(){
    this.hideSearch=!this.hideSearch;
  }

  showPriceButtons(){
    if(this.hideSearch)
      this.hideSearch=!this.hideSearch;
    this.hidePriceButtons=false;
    this.hideButtons=true;
  }

  showUpdateProduct(){
    this.hideUpdateProduct=!this.hideUpdateProduct;
  }

  hideResultSearch(){
    this.hideResult=!this.hideResult;
  }

  showProduct(status:boolean, response:any){
    if(status)
      this.searchResult=response;
  }

  hideProduct(status:boolean,response:any){
    if(status)
      this.searchResult=undefined;
  }




}
