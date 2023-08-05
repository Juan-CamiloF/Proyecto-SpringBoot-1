import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product, ProductPage, ResponseProduct } from 'src/app/models/product';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private url: string = environment.baseUrl + '/products';

  constructor(private http: HttpClient) {}

  postProducts(product: Product): Observable<ResponseProduct> {
    return this.http.post<ResponseProduct>(this.url, product);
  }

  getProducts(pageNumber: number, pageSize: number): Observable<ProductPage> {
    let newUrl = `${this.url}?pageNumber=${pageNumber}&pageSize=${pageSize}`;
    return this.http.get<ProductPage>(newUrl);
  }

  getListProductsByName(name: string): Observable<Product[]> {
    let newUrl = `${this.url}/${name}`;
    return this.http.get<Product[]>(newUrl);
  }
}
