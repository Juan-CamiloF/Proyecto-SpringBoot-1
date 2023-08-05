import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthComponent } from './components/auth/auth.component';
import { CreateUpdateCustomerComponent } from './components/customer/create-update-customer/create-update-customer.component';
import { DetailsCustomerComponent } from './components/customer/details-customer/details-customer.component';
import { ListCustomerComponent } from './components/customer/list-customer/list-customer.component';
import { CreateUpdateInvoiceComponent } from './components/invoice/create-update-invoice/create-update-invoice.component';
import { DetailsInvoiceComponent } from './components/invoice/details-invoice/details-invoice.component';
import { CreateProductComponent } from './components/product/create-product/create-product.component';
import { ListProductComponent } from './components/product/list-product/list-product.component';
import { AuthGuard } from './guards/auth-guard/auth.guard';

const routes: Routes = [
  { path: '', redirectTo: 'clientes', pathMatch: 'full' },
  { path: 'auth', component: AuthComponent },
  {
    path: 'clientes',
    component: ListCustomerComponent,
    canActivate: [AuthGuard],
    data: { role: 'ROLE_USER' },
  },
  {
    path: 'cliente',
    component: CreateUpdateCustomerComponent,
    canActivate: [AuthGuard],
    data: { role: 'ROLE_ADMIN' },
  },
  {
    path: 'cliente/:id',
    component: CreateUpdateCustomerComponent,
    canActivate: [AuthGuard],
    data: { role: 'ROLE_ADMIN' },
  },
  {
    path: 'cliente/detalle/:id',
    component: DetailsCustomerComponent,
    canActivate: [AuthGuard],
    data: { role: 'ROLE_USER' },
  },
  {
    path: 'factura/:idCliente',
    component: CreateUpdateInvoiceComponent,
    canActivate: [AuthGuard],
    data: { role: 'ROLE_ADMIN' },
  },{
    path: 'factura/actualizar/:idCliente/:idFactura',
    component: CreateUpdateInvoiceComponent,
    canActivate: [AuthGuard],
    data: { role: 'ROLE_ADMIN' },
  },
  {
    path: 'factura/detalle/:id',
    component: DetailsInvoiceComponent,
    canActivate: [AuthGuard],
    data: { role: 'ROLE_USER' },
  },
  {
    path: 'productos',
    component: ListProductComponent,
    canActivate: [AuthGuard],
    data: { role: 'ROLE_USER' },
  },
  {
    path: 'producto',
    component: CreateProductComponent,
    canActivate: [AuthGuard],
    data: { role: 'ROLE_ADMIN' },
  },
  { path: '**', component: ListCustomerComponent, canActivate: [AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
