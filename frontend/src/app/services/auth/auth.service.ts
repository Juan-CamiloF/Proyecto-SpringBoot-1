import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import jwtDecode from 'jwt-decode';
import { Observable } from 'rxjs';
import { Auth, Jwt, User } from 'src/app/models/auth';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private url: string = environment.baseUrl + '/auth';

  constructor(private http: HttpClient, private router: Router) {}

  postAuth(auth: Auth): Observable<Jwt> {
    return this.http.post<Jwt>(this.url, auth);
  }

  saveToken(token: string): void {
    sessionStorage.setItem('proyecto1-token', token);
  }

  sessionActive(): Boolean {
    return !!sessionStorage.getItem('proyecto1-token');
  }

  signOff(): void {
    sessionStorage.removeItem('proyecto1-token');
    this.router.navigate(['/auth']);
  }

  getToken(): string | null {
    return sessionStorage.getItem('proyecto1-token');
  }

  getDataToken(): any {
    const jwt = this.getToken();
    return jwt ? jwtDecode(jwt) : null;
  }

  hasRole(role: string): Boolean {
    const token = this.getDataToken();
    return (
      token.authorities.filter((r: any) => r.authority === role).length === 1
    );
  }

  tokenExpired(): boolean {
    const token = this.getDataToken();
    const date = new Date().getTime() / 1000;
    return token.exp < date;
  }
}
