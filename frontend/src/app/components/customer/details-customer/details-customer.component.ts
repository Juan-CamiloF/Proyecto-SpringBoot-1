import { HttpEventType } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import * as CryptoJS from 'crypto-js';
import { Customer } from 'src/app/models/customer';
import { ModalComponent } from '../../modal/modal.component';
import { SnackbarComponent } from '../../snackbar/snackbar.component';
import { ResponseCustomer } from 'src/app/models/customer';
import { AuthService } from 'src/app/services/auth/auth.service';
import { CustomerService } from 'src/app/services/customer/customer.service';
import { InvoiceService } from 'src/app/services/invoice/invoice.service';
@Component({
  selector: 'app-details-customer',
  templateUrl: './details-customer.component.html',
  styleUrls: ['./details-customer.component.css'],
})
export class DetailsCustomerComponent implements OnInit {
  constructor(
    private activatedRoute: ActivatedRoute,
    public authService: AuthService,
    private customerService: CustomerService,
    private invoiceService: InvoiceService,
    private router: Router,
    public dialog: MatDialog,
    private _snackBar: MatSnackBar
  ) {}

  displayedColumns: string[] = [
    'observation',
    'total',
    'createdAt',
    'details',
    'actions',
  ];
  customer!: Customer;
  selectedFile: string = '';
  progress: number = 0;

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params) => {
      let idParam = params['id'];
      if (idParam) {
        let id = CryptoJS.AES.decrypt(idParam, 'PR0YECT0-1').toString(
          CryptoJS.enc.Utf8
        );
        this.getCustomer(id);
      }
    });
  }

  getCustomer(id: string): void {
    this.customerService.getCustomer(id).subscribe((res) => {
      this.customer = res;
    });
  }

  updateCustomer(id: number): void {
    let idCrypto = CryptoJS.AES.encrypt(id.toString(), 'PR0YECT0-1').toString();
    this.router.navigate(['/cliente', idCrypto]);
  }

  openDialogDeleteCustomer(id: number): void {
    const dialogRef = this.dialog.open(ModalComponent, {
      width: '350px',
      data: {
        message: 'Eliminar cliente',
        content:
          'Está seguro de eliminar el cliente, está operación es irreversible.',
        result: false,
      },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) this.deleteCustomer(id);
      else this.openSnackBar('No se elimino el cliente');
    });
  }

  deleteCustomer(id: number): void {
    this.customerService.deleteCustomer(id).subscribe({
      next: (message) => {
        this.openSnackBar(message.message);
        this.router.navigate(['/clientes']);
      },
      error: (err) => {
        this.openSnackBar(err.error.message);
      },
    });
  }

  uploadResource(event: Event): void {
    const target = event.target as HTMLInputElement;
    const files = target.files as FileList;
    if (!files[0] || files[0].type.indexOf('image') === -1) {
      this.openSnackBar('El archivo no es válido');
      return;
    }
    if (files[0]) {
      this.selectedFile = files[0].name;
      let form = new FormData();
      form.append('id', this.customer.id.toString());
      form.append('file', files[0]);
      this.customerService.uploadFile(form).subscribe({
        next: (event) => {
          if (event.type === HttpEventType.UploadProgress && event.total) {
            this.progress = Math.round((event.loaded / event.total) * 100);
          } else if (event.type === HttpEventType.Response && event.body) {
            let res: ResponseCustomer = event.body;
            this.customer = res.customer;
            this.openSnackBar(res.message);
          }
        },
        error: (err) => {
          this.openSnackBar(err.error.message);
        },
      });
    } else {
      this.selectedFile = '';
    }
  }

  deleteResource(id: number, fileName: string): void {
    this.customerService.deleteFile(id, fileName).subscribe({
      next: (res) => {
        this.customer = res.customer;
        this.openSnackBar(res.message);
      },
      error: (err) => {
        this.openSnackBar(err.error.message);
      },
    });
  }

  createInvoice(idCustomer: number): void {
    const idCrypto = CryptoJS.AES.encrypt(
      idCustomer.toString(),
      'PR0YECT0-1'
    ).toString();
    this.router.navigate(['/factura', idCrypto]);
  }

  detailsInvoice(id: number) {
    let idCrypto = CryptoJS.AES.encrypt(id.toString(), 'PR0YECT0-1').toString();
    this.router.navigate(['/factura/detalle', idCrypto]);
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

  deleteInvoice(id: number) {
    this.invoiceService.deleteInvoice(id).subscribe({
      next: (res) => {
        this.openSnackBar(res.message);
        this.getCustomer(this.customer.id.toString());
      },
      error: (err) => {
        this.openSnackBar(err.error.message);
      },
    });
  }

  updateInvoice(idCustomer: number, idInvoice: number) {
    let idCryptoCustomer = CryptoJS.AES.encrypt(
      idCustomer.toString(),
      'PR0YECT0-1'
    ).toString();
    let idCryptoInvoice = CryptoJS.AES.encrypt(
      idInvoice.toString(),
      'PR0YECT0-1'
    ).toString();
    this.router.navigate(['/factura/actualizar', idCryptoCustomer, idCryptoInvoice]);
  }

  openSnackBar(message: string): void {
    this._snackBar.openFromComponent(SnackbarComponent, {
      data: message,
      duration: 2000,
    });
  }
}
