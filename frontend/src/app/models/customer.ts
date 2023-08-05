import { Invoice } from './invoice';
import { Region } from './region';

export interface Customer {
  id: number;
  names: string;
  surnames: string;
  email: string;
  dateOfBirth: Date;
  updateAt: Date;
  createdAt: Date;
  filename: string;
  urlFilename: string;
  region: Region;
  invoices: Invoice[];
}

export interface CustomerPage {
  content: Customer[];
  pageable: {
    sort: {
      empty: string;
      sorted: string;
      unsorted: string;
    };
    offset: number;
    pageSize: number;
    pageNumber: number;
    unpaged: boolean;
    paged: boolean;
  };
  last: boolean;
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
  sort: {
    empty: boolean;
    sorted: boolean;
    unsorted: boolean;
  };
  first: boolean;
  numberOfElements: number;
  empty: boolean;
}

export interface RequestCreateCustomer {
  names: string;
  surnames: string;
  email: string;
  dateOfBirth: Date;
  region: Region;
}

export interface ResponseCustomer {
  message: string;
  customer: Customer;
}
