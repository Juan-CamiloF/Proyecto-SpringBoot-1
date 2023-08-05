import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { Invoice, InvoiceDetails } from 'src/app/models/invoice';
import { InvoiceService } from 'src/app/services/invoice/invoice.service';
import { SnackbarComponent } from '../../snackbar/snackbar.component';
import { Location } from '@angular/common';
import * as CryptoJS from 'crypto-js';
import { MatDialog } from '@angular/material/dialog';
import { ModalComponent } from '../../modal/modal.component';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-details-invoice',
  templateUrl: './details-invoice.component.html',
  styleUrls: ['./details-invoice.component.css'],
})
export class DetailsInvoiceComponent implements OnInit {
  constructor(
    public authService: AuthService,
    private activatedRoute: ActivatedRoute,
    private invoiceService: InvoiceService,
    private dialog: MatDialog,
    private _snackBar: MatSnackBar,
    private _location: Location
  ) {}

  invoice!: Invoice;
  displayedColumns: string[] = [
    'amount',
    'product',
    'price',
    'calculateAmount',
  ];
  dataSource: InvoiceDetails[] = [];

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params) => {
      let idParam = params['id'];
      if (idParam) {
        let id = CryptoJS.AES.decrypt(idParam, 'PR0YECT0-1').toString(
          CryptoJS.enc.Utf8
        );
        this.getInvoice(id);
      }
    });
  }

  backClicked(): void {
    this._location.back();
  }

  getInvoice(id: string): void {
    this.invoiceService.getInvoice(id).subscribe({
      next: (res) => {
        this.invoice = res;
        this.dataSource = res.details;
      },
      error: (err) => {
        this.openSnackBar(err.error.message);
      },
    });
  }

  openDialogDeleteInvoice(id: number): void {
    const dialogRef = this.dialog.open(ModalComponent, {
      width: '350px',
      data: {
        message: 'Eliminar factura',
        content:
          'Está seguro de eliminar la factura, está operación es irreversible.',
        result: false,
      },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) this.deleteInvoice(id);
      else this.openSnackBar('No se elimino la factura');
    });
  }

  deleteInvoice(id: number): void {
    this.invoiceService.deleteInvoice(id).subscribe({
      next: (res) => {
        this.openSnackBar(res.message);
        this._location.back();
      },
      error: (err) => {
        this.openSnackBar(err.error.message);
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
