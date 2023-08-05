import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

/* Pipe change lenguaje */
import { LOCALE_ID } from '@angular/core';
import localeEs from '@angular/common/locales/es';
import { registerLocaleData } from '@angular/common';
registerLocaleData(localeEs, 'es');

/* Angular material */
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatTableModule } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSelectModule } from '@angular/material/select';
import { MatAutocompleteModule } from '@angular/material/autocomplete';

/* Components */
import { MenuComponent } from './components/menu/menu.component';
import { FooterComponent } from './components/footer/footer.component';
import { AuthComponent } from './components/auth/auth.component';
import { CreateUpdateCustomerComponent } from './components/customer/create-update-customer/create-update-customer.component';
import { ListCustomerComponent } from './components/customer/list-customer/list-customer.component';
import { DetailsCustomerComponent } from './components/customer/details-customer/details-customer.component';
import { CreateUpdateInvoiceComponent } from './components/invoice/create-update-invoice/create-update-invoice.component';
import { DetailsInvoiceComponent } from './components/invoice/details-invoice/details-invoice.component';
import { SnackbarComponent } from './components/snackbar/snackbar.component';
import { ModalComponent } from './components/modal/modal.component';

/* Reactive forms */
import { ReactiveFormsModule } from '@angular/forms';

/* Http Client */
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpInterceptorService } from './services/http-interceptor/http-interceptor.service';
import { SecurePipePipe } from './pipes/secure-pipe.pipe';
import { ListProductComponent } from './components/product/list-product/list-product.component';
import { CreateProductComponent } from './components/product/create-product/create-product.component';
import { CurrencyMaskModule } from 'ng2-currency-mask';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    FooterComponent,
    ListCustomerComponent,
    CreateUpdateCustomerComponent,
    SnackbarComponent,
    ModalComponent,
    DetailsCustomerComponent,
    AuthComponent,
    SecurePipePipe,
    DetailsInvoiceComponent,
    CreateUpdateInvoiceComponent,
    ListProductComponent,
    CreateProductComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MatMenuModule,
    MatButtonModule,
    MatGridListModule,
    MatTableModule,
    MatInputModule,
    MatSnackBarModule,
    MatIconModule,
    MatPaginatorModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatDialogModule,
    MatProgressBarModule,
    MatSelectModule,
    MatAutocompleteModule,
    CurrencyMaskModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true,
    },
    { provide: LOCALE_ID, useValue: 'es' },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
