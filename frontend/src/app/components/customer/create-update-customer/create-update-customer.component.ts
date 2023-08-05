import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { ResponseCustomer } from 'src/app/models/customer';
import { CustomerService } from 'src/app/services/customer/customer.service';
import { SnackbarComponent } from '../../snackbar/snackbar.component';
import * as CryptoJS from 'crypto-js';
import { RegionService } from 'src/app/services/region/region.service';
import { Region } from 'src/app/models/region';
import { Location } from '@angular/common';

@Component({
  selector: 'app-create-update-customer',
  templateUrl: './create-update-customer.component.html',
  styleUrls: ['./create-update-customer.component.css'],
})
export class CreateUpdateCustomerComponent implements OnInit {
  public regions: Region[] = [];
  public regionSelect!: Region;
  maxDate: Date = new Date();
  public formCreateUpdateCustomer: FormGroup = this.formBuilder.group({
    id: [''],
    names: [
      '',
      [Validators.minLength(2), Validators.maxLength(50), Validators.required],
    ],
    surnames: [
      '',
      [Validators.minLength(2), Validators.maxLength(50), Validators.required],
    ],
    email: ['', [Validators.required, Validators.email]],
    dateOfBirth: ['', Validators.required],
    region: ['', Validators.required],
    createdAt: [''],
    updateAt: [''],
  });

  get f() {
    return this.formCreateUpdateCustomer.controls;
  }

  constructor(
    private formBuilder: FormBuilder,
    private customerService: CustomerService,
    private regionService: RegionService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private _snackBar: MatSnackBar,
    private _location: Location
  ) {}

  ngOnInit(): void {
    this.getCustomer();
    this.getRegions();
  }

  backClicked(): void {
    this._location.back();
  }

  getCustomer(): void {
    this.activatedRoute.params.subscribe((params) => {
      let idParam = params['id'];
      if (idParam) {
        let id = CryptoJS.AES.decrypt(idParam, 'PR0YECT0-1').toString(
          CryptoJS.enc.Utf8
        );
        this.customerService.getCustomer(id).subscribe({
          next: (customer) => {
            this.formCreateUpdateCustomer.patchValue({
              id: customer.id,
              names: customer.names,
              surnames: customer.surnames,
              email: customer.email,
              dateOfBirth: customer.dateOfBirth,
              updateAt: customer.updateAt,
              createdAt: customer.createdAt,
            });
            this.formCreateUpdateCustomer
              .get('region')
              ?.setValue(customer.region);
          },
          error: (err) => {
            if (err.status === 0)
              this.openSnackBar('Ocurrió un error intente más tarde');
            else this.openSnackBar(err.error.message);
          },
        });
      }
    });
  }

  getRegions(): void {
    this.regionService.getRegions().subscribe({
      next: (res) => {
        this.regions = res;
      },
      error: (err) => {
        if (err.status === 0)
          this.openSnackBar('Ocurrió un error intente más tarde');
        else this.openSnackBar(err.error.message);
      },
    });
  }

  compareRegion(region: Region, customerRegion: Region): boolean {
    return region == null || customerRegion == null
      ? false
      : region.id === customerRegion.id;
  }

  createCustomer(): void {
    if (this.formCreateUpdateCustomer.invalid) {
      Object.values(this.formCreateUpdateCustomer.controls).forEach((control) => {
        control.markAsTouched();
      });
      return;
    }
    this.customerService
      .postCustomer(this.formCreateUpdateCustomer.value)
      .subscribe({
        next: (customer: ResponseCustomer) => {
          const createdCustomer = customer.customer;
          const message =
            'Cliente ' +
            createdCustomer.names +
            ' ' +
            createdCustomer.surnames +
            ' registrado';
          this.openSnackBar(message);
          this.router.navigate(['/clientes']);
        },
        error: (err) => {
          if (err.status === 0)
            this.openSnackBar('Ocurrió un error intente más tarde');
          else this.openSnackBar(err.error.message);
        },
      });
  }

  updateCustomer(): void {
    if (this.formCreateUpdateCustomer.invalid) {
      Object.values(this.formCreateUpdateCustomer.controls).forEach((control) => {
        control.markAsTouched();
      });
      return;
    }
    this.customerService
      .putCustomer(this.formCreateUpdateCustomer.value)
      .subscribe({
        next: (customer: ResponseCustomer) => {
          const updatedCustomer = customer.customer;
          const message =
            'Cliente ' +
            updatedCustomer.names +
            ' ' +
            updatedCustomer.surnames +
            ' actualizado';
          this.openSnackBar(message);
          this.router.navigate(['/clientes']);
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
