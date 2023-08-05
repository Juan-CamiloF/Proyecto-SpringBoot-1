import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';
import { SnackbarComponent } from '../snackbar/snackbar.component';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css'],
})
export class AuthComponent implements OnInit {
  public hide: Boolean = true;
  public formAuth: FormGroup = this.formBuild.group({
    username: ['', Validators.required],
    password: ['', [Validators.required, Validators.minLength(4)]],
  });

  get f() {
    return this.formAuth.controls;
  }

  constructor(
    private formBuild: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {}

  authUser(): void {
    if (this.formAuth.invalid) return;
    this.authService.postAuth(this.formAuth.value).subscribe({
      next: (res) => {
        this.authService.saveToken(res.jwt);
        this.router.navigate(['/clientes']);
      },
      error: (err) => {
        if (err.status === 0)
          this.openSnackBar('Ocurrió un error intente más tarde');
        else this.openSnackBar('Usuario o contraseña incorrectos');
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
