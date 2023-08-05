export interface Product {
  id: number;
  name: string;
  price: number;
  createdAt: Date;
}

export interface ProductPage {
  content: Product[];
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

export interface ResponseProduct {
  message: string;
  product: Product;
}
