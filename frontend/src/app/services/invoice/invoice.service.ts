import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Invoice } from 'src/app/models/invoice';
import { ResponseInvoice } from 'src/app/models/invoice';
import { ResponseMessage } from 'src/app/models/message';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class InvoiceService {
  private url: string = environment.baseUrl + '/invoices';

  constructor(private http: HttpClient) {}

  getInvoice(id: string): Observable<Invoice> {
    let newUrl = `${this.url}/${id}`;
    return this.http.get<Invoice>(newUrl);
  }

  postInvoice(invoice: Invoice): Observable<ResponseInvoice> {
    return this.http.post<ResponseInvoice>(this.url, invoice);
  }

  updateInvoice(invoice: Invoice): Observable<ResponseInvoice> {
    let newUrl = `${this.url}/${invoice.id}`;
    return this.http.put<ResponseInvoice>(newUrl, invoice);
  }

  deleteInvoice(id: number): Observable<ResponseMessage> {
    let newUrl = `${this.url}/${id}`;
    return this.http.delete<ResponseMessage>(newUrl);
  }
}
