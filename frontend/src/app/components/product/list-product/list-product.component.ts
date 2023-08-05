import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatPaginatorIntl, PageEvent } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Product } from 'src/app/models/product';
import { ProductService } from 'src/app/services/product/product.service';
import { SnackbarComponent } from '../../snackbar/snackbar.component';

@Component({
  selector: 'app-list-product',
  templateUrl: './list-product.component.html',
  styleUrls: ['./list-product.component.css'],
})
export class ListProductComponent implements OnInit {
  constructor(
    private productService: ProductService,
    private paginator: MatPaginatorIntl,
    private _snackBar: MatSnackBar
  ) {}

  pageNumber: number = 0;
  pageSize: number = 10;
  dataSource: Product[] = [];
  displayedColumns = ['id', 'name', 'price', 'createdAt'];
  length: number = 0;

  ngOnInit(): void {
    /* Configuración en español para paginación angular material */
    this.paginator.itemsPerPageLabel = 'Productos por página';
    this.paginator.firstPageLabel = 'Primera página';
    this.paginator.lastPageLabel = 'Última página';
    this.paginator.previousPageLabel = 'Página anterior';
    this.paginator.nextPageLabel = 'Página siguiente';

    this.paginator.getRangeLabel = (page: number, pageSize: number) => {
      const start = page * pageSize + 1;
      const end = (page + 1) * pageSize;
      return `${start} - ${end} de ${this.length}`;
    };
    this.getProducts(this.pageNumber, this.pageSize);
  }

  getProducts(pageNumber: number, pageSize: number) {
    this.productService.getProducts(pageNumber, pageSize).subscribe({
      next: (res) => {
        this.dataSource = res.content;
        this.length = res.totalElements;
      },
      error: (err) => {
        if (err.status === 0)
          this.openSnackBar('Ocurrió un error intenté más tarde');
        else this.openSnackBar(err.error.message);
      },
    });
  }

  handlePage(e: PageEvent) {
    this.pageSize = e.pageSize;
    this.pageNumber = e.pageIndex;
    this.getProducts(this.pageNumber, this.pageSize);
  }

  openSnackBar(message: string): void {
    this._snackBar.openFromComponent(SnackbarComponent, {
      data: message,
      duration: 2000,
    });
  }
}
