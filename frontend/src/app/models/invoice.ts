import { Customer } from './customer';
import { Product } from './product';

export interface Invoice {
  id: number;
  customer: Customer;
  observation: string;
  description: string;
  createdAt: Date;
  details: InvoiceDetails[];
  total: number;
}

export interface InvoiceDetails {
  id: number;
  amount: number;
  product: Product;
  calculateAmount: number;
}

export interface ResponseInvoice {
  message: string;
  invoice: Invoice;
}
