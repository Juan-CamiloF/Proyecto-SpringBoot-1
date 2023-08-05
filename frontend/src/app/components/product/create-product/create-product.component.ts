import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { ProductService } from 'src/app/services/product/product.service';
import { SnackbarComponent } from '../../snackbar/snackbar.component';

@Component({
  selector: 'app-create-product',
  templateUrl: './create-product.component.html',
  styleUrls: ['./create-product.component.css'],
})
export class CreateProductComponent implements OnInit {
  constructor(
    private productService: ProductService,
    private formBuilder: FormBuilder,
    private _snackBar: MatSnackBar,
    private router: Router
  ) {}

  public formCreateProduct: FormGroup = this.formBuilder.group({
    name: [
      '',
      [Validators.required, Validators.minLength(3), Validators.maxLength(50)],
    ],
    price: [
      '',
      [Validators.required, Validators.min(0), Validators.max(100000000)],
    ],
  });

  ngOnInit(): void {}

  get f() {
    return this.formCreateProduct.controls;
  }

  createProduct() {
    if (this.formCreateProduct.invalid) {
      Object.values(this.formCreateProduct.controls).forEach((control) => {
        control.markAsTouched();
      });
      return;
    }
    this.productService.postProducts(this.formCreateProduct.value).subscribe({
      next: (res) => {
        this.openSnackBar(res.message);
        this.router.navigate(['/productos']);
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
