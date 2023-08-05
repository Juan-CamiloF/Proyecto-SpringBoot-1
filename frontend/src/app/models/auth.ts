export interface Auth {
  username: string;
  password: string;
}

export interface Jwt {
  jwt: string;
}

export interface User {
  id: number;
  username: string;
  active: boolean;
  roles: [Role];
}

export interface Role {
  id: number;
  name: string;
}
