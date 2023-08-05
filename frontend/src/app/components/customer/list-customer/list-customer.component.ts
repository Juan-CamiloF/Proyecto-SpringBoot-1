import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Customer } from 'src/app/models/customer';
import { CustomerService } from 'src/app/services/customer/customer.service';
import { SnackbarComponent } from '../../snackbar/snackbar.component';
import * as CryptoJS from 'crypto-js';
import { Router } from '@angular/router';
import { MatPaginatorIntl, PageEvent } from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';
import { ModalComponent } from '../../modal/modal.component';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-list-customer',
  templateUrl: './list-customer.component.html',
  styleUrls: ['./list-customer.component.css'],
})
export class ListCustomerComponent implements OnInit {
  constructor(
    private customerService: CustomerService,
    public authService: AuthService,
    private _snackBar: MatSnackBar,
    private router: Router,
    private paginator: MatPaginatorIntl,
    public dialog: MatDialog
  ) {}

  pageNumber: number = 0;
  pageSize: number = 10;
  dataSource: Customer[] = [];
  length: number = 0;
  displayedColumns = ['id', 'names', 'email', 'detail', 'update'];

  ngOnInit(): void {
    /* Configuración en español para paginación angular material */
    this.paginator.itemsPerPageLabel = 'Clientes por página';
    this.paginator.firstPageLabel = 'Primera página';
    this.paginator.lastPageLabel = 'Última página';
    this.paginator.previousPageLabel = 'Página anterior';
    this.paginator.nextPageLabel = 'Página siguiente';

    this.paginator.getRangeLabel = (page: number, pageSize: number) => {
      const start = page * pageSize + 1;
      const end = (page + 1) * pageSize;
      return `${start} - ${end} de ${this.length}`;
    };
    this.getCustomers(this.pageNumber, this.pageSize);
  }

  getCustomers(pageNumber: number, pageSize: number): void {
    this.customerService.getCustomers(pageNumber, pageSize).subscribe({
      next: (res) => {
        this.dataSource = res.content;
        this.length = res.totalElements;
      },
      error: (err) => {
        if (err.status == 0) {
          this.openSnackBar('Ocurrió un error intente más tarde');
        } else {
          this.openSnackBar(err.error.message);
        }
      },
    });
  }

  handlePage(e: PageEvent) {
    this.pageSize = e.pageSize;
    this.pageNumber = e.pageIndex;
    this.getCustomers(this.pageNumber, this.pageSize);
  }

  updateCustomer(id: number): void {
    const idCrypto = CryptoJS.AES.encrypt(
      id.toString(),
      'PR0YECT0-1'
    ).toString();
    this.router.navigate(['/cliente', idCrypto]);
  }

  detailsCustomer(id: number): void {
    const idCrypto = CryptoJS.AES.encrypt(
      id.toString(),
      'PR0YECT0-1'
    ).toString();
    this.router.navigate(['/cliente/detalle', idCrypto]);
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
        this.getCustomers(this.pageNumber, this.pageSize);
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
