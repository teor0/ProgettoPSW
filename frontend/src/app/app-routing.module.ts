import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './routes/Login/Login.component';
import { HomeComponent } from './routes/Home/Home.component';
import { ErrorComponent } from './routes/Error/Error.component';
import { UserComponent } from './routes/User/User.component';
import { AdminComponent } from './routes/Admin/Admin.component';
import { ProductComponent } from './routes/Product/Product.component';
import { OrderComponent } from './routes/Order/Order.component';
import { CartComponent } from './routes/Cart/Cart.component';
import { CompleteRegistrationComponent } from './routes/CompleteRegistration/CompleteRegistration.component';
import { CompleteLoginComponent } from './routes/CompleteLogin/CompleteLogin.component';
import { PreviewCartComponent } from './routes/PreviewCart/PreviewCart.component';

const routes: Routes = [
  {path: '', component:HomeComponent},
  {path: 'Login', component:LoginComponent},
  {path: 'complete-registration', component:CompleteRegistrationComponent},
  {path: 'complete-login', component:CompleteLoginComponent},
  {path: 'cart', component:PreviewCartComponent},
  {path: 'Product', component:ProductComponent},
  {path: 'Order', component:OrderComponent},
  {path: 'User', component:UserComponent},
  {path: 'Admin', component:AdminComponent},
  {path: 'Cart', component:CartComponent},
  {path: '**', component:ErrorComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
