import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { SnackbarComponent } from 'src/app/components/snackbar/snackbar.component';
import { Customer } from 'src/app/models/customer';
import { Product } from 'src/app/models/product';
import { CustomerService } from 'src/app/services/customer/customer.service';
import { ProductService } from 'src/app/services/product/product.service';
import * as CryptoJS from 'crypto-js';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { Location } from '@angular/common';
import { InvoiceDetails } from 'src/app/models/invoice';
import { InvoiceService } from 'src/app/services/invoice/invoice.service';
import { map, mergeMap, Observable, startWith } from 'rxjs';
import { MatAutocompleteTrigger } from '@angular/material/autocomplete';
@Component({
  selector: 'app-create-update-invoice',
  templateUrl: './create-update-invoice.component.html',
  styleUrls: ['./create-update-invoice.component.css'],
})
export class CreateUpdateInvoiceComponent implements OnInit {
  constructor(
    private activatedRoute: ActivatedRoute,
    private customerService: CustomerService,
    private productService: ProductService,
    private invoiceService: InvoiceService,
    private formBuilder: FormBuilder,
    private _snackBar: MatSnackBar,
    private _location: Location,
    private router: Router
  ) {}

  @ViewChild(MatAutocompleteTrigger) trigger!: MatAutocompleteTrigger;

  displayedColumns: string[] = [
    'amount',
    'product',
    'unitPrice',
    'totalPrice',
    'actions',
  ];
  customer!: Customer;
  products: Observable<Product[]> | undefined;
  details: InvoiceDetails[] = [];
  total: number = 0;

  public formCreateUpdateInvoice = this.formBuilder.group({
    id: [''],
    observation: ['', [Validators.required, Validators.maxLength(50)]],
    description: ['', Validators.maxLength(100)],
    customer: ['', Validators.required],
    details: ['', Validators.required],
    createdAt: [''],
  });

  public formInvoiceDetails = this.formBuilder.group({
    id: [''],
    amount: ['', [Validators.required, Validators.min(1)]],
    product: ['', [Validators.required, this.requireMatch]],
  });

  public formAmount = new FormControl('', [
    Validators.required,
    Validators.min(1),
  ]);

  private requireMatch(control: AbstractControl): ValidationErrors | null {
    const selection: any = control.value;
    if (typeof selection === 'string') {
      return { requireMatch: true };
    }
    return null;
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params) => {
      let idParamCustomer = params['idCliente'];
      if (idParamCustomer) {
        let id = CryptoJS.AES.decrypt(idParamCustomer, 'PR0YECT0-1').toString(
          CryptoJS.enc.Utf8
        );
        this.getCustomer(id);
      }
      let idParamInvoice = params['idFactura'];
      if (idParamInvoice) {
        let id = CryptoJS.AES.decrypt(idParamCustomer, 'PR0YECT0-1').toString(
          CryptoJS.enc.Utf8
        );
        this.invoiceService.getInvoice(id).subscribe({
          next: (res) => {
            this.formCreateUpdateInvoice.patchValue({
              id: res.id,
              observation: res.observation,
              description: res.description,
              details: res.details,
              createdAt: res.createdAt,
            });
            this.details = res.details;
            this.total = this.details
              .map((detail) => detail.amount * detail.product.price)
              .reduce((acc, cur) => acc + cur);
          },
          error: (err) => {
            this.openSnackBar(err.error.message);
          },
        });
      }
    });

    this.products = this.formInvoiceDetails.get('product')?.valueChanges.pipe(
      startWith(''),
      map((value) => (typeof value === 'string' ? value : value?.name)),
      mergeMap((value) => (value ? this._filter(value) : []))
    );
  }

  ngAfterViewInit() {
    this.trigger.panelClosingActions.subscribe((e) => {
      if (!(e && e.source)) {
        this.formInvoiceDetails.get('product')?.setValue(null);
        this.trigger.closePanel();
      }
    });
  }

  private _filter(name: string): Observable<Product[]> {
    const filterValue = name.toLowerCase();

    return this.productService.getListProductsByName(filterValue);
  }

  displayFn(product: Product): string {
    return product && product.name ? product.name : '';
  }

  get f() {
    return this.formCreateUpdateInvoice.controls;
  }

  get fDetails() {
    return this.formInvoiceDetails.controls;
  }

  backClicked(): void {
    this._location.back();
  }

  getCustomer(id: string): void {
    this.customerService.getCustomer(id).subscribe((res) => {
      this.customer = res;
      this.formCreateUpdateInvoice.controls['customer'].setValue(res);
    });
  }

  existProduct(product: Product): boolean {
    return this.details.filter((e) => e.product.id === product.id).length > 0;
  }

  addProduct(): void {
    if (this.formInvoiceDetails.invalid) return;
    const product = this.formInvoiceDetails.get('product')?.value;
    if (this.existProduct(product)) {
      this.details = this.details.map((e) => {
        if (e.product.id == product.id) {
          const amount = this.formInvoiceDetails.get('amount')?.value;
          e.amount += amount;
        }
        return e;
      });
    } else {
      this.details = [this.formInvoiceDetails.value, ...this.details];
    }
    this.formInvoiceDetails.reset();
    this.formCreateUpdateInvoice.controls['details'].setValue(this.details);
    this.total = this.details
      .map((detail) => detail.amount * detail.product.price)
      .reduce((acc, cur) => acc + cur);
  }

  changeAmount(invoiceDetail: InvoiceDetails, event: any) {
    let amount: number = event.target.value as number;
    this.details.map((detail) => {
      if (detail === invoiceDetail) {
        detail.amount = amount;
      }
      return detail;
    });
    this.total = this.details
      .map((detail) => detail.amount * detail.product.price)
      .reduce((acc, cur) => acc + cur);
  }

  removeProduct(id: number): void {
    this.details = this.details.filter((e) => e.product.id != id);
    this.formCreateUpdateInvoice.controls['details'].setValue(this.details);
  }

  createInvoice(): void {
    if (this.formCreateUpdateInvoice.invalid) {
      Object.values(this.formCreateUpdateInvoice.controls).forEach(
        (control) => {
          control.markAsTouched();
        }
      );
      return;
    }
    this.invoiceService
      .postInvoice(this.formCreateUpdateInvoice.value)
      .subscribe({
        next: (res) => {
          this.openSnackBar(res.message);
          this._location.back();
        },
        error: (err) => {
          if (err.status === 0)
            this.openSnackBar('Ocurrió un error intente más tarde');
          else this.openSnackBar(err.error.message);
        },
      });
  }

  updateInvoice(): void {
    if (this.formCreateUpdateInvoice.invalid) {
      Object.values(this.formCreateUpdateInvoice.controls).forEach(
        (control) => {
          control.markAsTouched();
        }
      );
      return;
    }
    this.invoiceService
      .updateInvoice(this.formCreateUpdateInvoice.value)
      .subscribe({
        next: (res) => {
          this.openSnackBar(res.message);
          this._location.back();
        },
        error: (err) => {
          if (err.status === 0)
            this.openSnackBar('Ocurrió un error intente más tarde');
          else this.openSnackBar(err.error.message);
        },
      });
  }

  openSnackBar(message: string): void {
    this._snackBar.openFromComponent(SnackbarComponent, {
      data: message,
      duration: 2000,
    });
  }
}
