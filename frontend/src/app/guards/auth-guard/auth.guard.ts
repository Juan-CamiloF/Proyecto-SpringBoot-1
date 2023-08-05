import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  UrlTree,
} from '@angular/router';
import { Observable } from 'rxjs';
import { SnackbarComponent } from 'src/app/components/snackbar/snackbar.component';
import { AuthService } from 'src/app/services/auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router,
    private _snackBar: MatSnackBar
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    let role = route.data['role'] as string;
    if (!this.authService.sessionActive()) {
      this.router.navigate(['/auth']);
      this.openSnackBar('Por favor inicie sesión');
      return false;
    } else {
      if (this.authService.tokenExpired()) {
        this.authService.signOff();
        this.router.navigate(['/auth']);
        this.openSnackBar('Por favor inicie sesión');
        return false;
      }
    }
    if (!this.authService.hasRole(role)) {
      this.router.navigate(['clientes']);
      this.openSnackBar('Sin autorización');
      return false;
    }
    return true;
  }

  openSnackBar(message: string): void {
    this._snackBar.openFromComponent(SnackbarComponent, {
      data: message,
      duration: 2000,
    });
  }
}
