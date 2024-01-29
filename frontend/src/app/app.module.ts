import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatButtonModule } from '@angular/material/button'
import { MatDialogModule } from '@angular/material/dialog';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatPaginatorModule} from '@angular/material/paginator';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatTabsModule } from '@angular/material/tabs';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { MatOptionModule } from '@angular/material/core';
//import { AuthInterceptor } from './helpers/AuthInterceptor';
import { ErrorHandlerInterceptor } from './helpers/Error/ErrorInterceptor';
import { ResponseComponent } from './helpers/Response/Response.component';
import { LoginComponent } from './routes/Login/Login.component';
import { AdminComponent } from './routes/Admin/Admin.component';
import { ShowModelComponent } from './helpers/Response/ShowModel/ShowModel.component';
import { VendorComponent } from './routes/Vendor/Vendor.component';
import { UserComponent } from './routes/User/User.component';
import { ProductComponent } from './routes/Product/Product.component';
import { MatSortModule } from '@angular/material/sort';
import { MatMenuModule } from '@angular/material/menu';
import { MatSliderModule } from '@angular/material/slider';
import { ShowOrderComponent } from './helpers/Response/ShowModel/ShowOrder/ShowOrder.component';
import { OrderComponent } from './routes/Order/Order.component';
import { ShowOrderProductsComponent } from './helpers/Response/ShowModel/ShowOrderProducts/ShowOrderProducts.component';
import { ProductDialogComponent } from './helpers/Response/ProductDialog/ProductDialog.component';
import { CartComponent } from './routes/Cart/Cart.component';
import { UpdateDialogComponent } from './helpers/UpdateDialog/UpdateDialog.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { CompleteRegistrationComponent } from './routes/CompleteRegistration/CompleteRegistration.component';
import { initializeKeycloak } from './services/Auth/KeycloakInit.factory';
import { AuthService } from './services/Auth/AuthService.service';
import { CompleteLoginComponent } from './routes/CompleteLogin/CompleteLogin.component';
import { OrderProductsDialogComponent } from './helpers/Response/OrderProductsDialog/OrderProductsDialog.component';


@NgModule({
  declarations: [
    AppComponent,
    ResponseComponent,
    ShowModelComponent,
    AdminComponent,
    UpdateDialogComponent,
    ProductDialogComponent,
    LoginComponent,
    CompleteRegistrationComponent,
    CompleteLoginComponent,
    OrderProductsDialogComponent,
    OrderComponent,
    CartComponent,
    UserComponent,
    ProductComponent,
    VendorComponent,
    ShowOrderProductsComponent,
    ShowOrderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    KeycloakAngularModule,
    MatButtonModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    FormsModule,
    MatSortModule,
    MatDividerModule,
    MatFormFieldModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSliderModule,
    MatInputModule,
    MatIconModule,
    MatDialogModule,
    MatMenuModule,
    MatOptionModule,
    MatSelectModule,
    MatTabsModule,
    MatPaginatorModule,
    MatTableModule,
    MatCardModule
  ],
  providers: [
    { provide: APP_INITIALIZER, useFactory: initializeKeycloak, multi: true, deps: [KeycloakService, AuthService] },
    //{ provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorHandlerInterceptor, multi:true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
