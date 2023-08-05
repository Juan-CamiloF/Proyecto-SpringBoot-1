import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import {
  Customer,
  RequestCreateCustomer,
  ResponseCustomer,
  CustomerPage,
} from 'src/app/models/customer';
import { environment } from 'src/environments/environment';
import { ResponseMessage } from 'src/app/models/message';
@Injectable({
  providedIn: 'root',
})
export class CustomerService {
  private url: string = environment.baseUrl + '/customers';
  constructor(private http: HttpClient) {}

  getCustomers(pageNumber: number, pageSize: number): Observable<CustomerPage> {
    let newUrl = `${this.url}/page?pageSize=${pageSize}&pageNumber=${pageNumber}`;
    return this.http.get<CustomerPage>(newUrl);
  }

  postCustomer(customer: RequestCreateCustomer): Observable<ResponseCustomer> {
    return this.http.post<ResponseCustomer>(this.url, customer);
  }

  getCustomer(id: string): Observable<Customer> {
    let newUrl = `${this.url}/${id}`;
    return this.http.get<Customer>(newUrl);
  }

  putCustomer(customer: Customer): Observable<ResponseCustomer> {
    let newUrl = `${this.url}/${customer.id}`;
    return this.http.put<ResponseCustomer>(newUrl, customer);
  }

  deleteCustomer(id: number): Observable<ResponseMessage> {
    let newUrl = `${this.url}/${id}`;
    return this.http.delete<ResponseMessage>(newUrl);
  }

  uploadFile(form: FormData): Observable<HttpEvent<ResponseCustomer>> {
    let newUrl = `${this.url}/upload`;

    const req = new HttpRequest('POST', newUrl, form, {
      reportProgress: true,
    });

    return this.http.request(req);
  }

  deleteFile(id: number, fileName: string): Observable<ResponseCustomer> {
    let newUrl = `${this.url}/delete/${id}/${fileName}`;
    return this.http.delete<ResponseCustomer>(newUrl);
  }
}
