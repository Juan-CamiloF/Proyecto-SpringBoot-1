-- Inserts for table Roles --
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_USER');

-- Inserts for table Users --
INSERT INTO users (username, password, active) VALUES ('admin', '$2a$12$UPHCzDiBPF3VAmD6khHH8.5TVPMjEtyEXTHHVBwnE.IksFJ4pbuTa', true);
INSERT INTO users (username, password, active) VALUES ('user', '$2a$12$JsCgwVUbOxUWLF7KJRlzhu0.bHbsgxEj3r2ydFeiPWQbBDHfBsfEq', true);

-- Inserts for table Users_roles --
INSERT INTO user_roles(user_id, role_id) VALUES ((SELECT id from users where username = 'admin'),(SELECT id from roles where name = 'ROLE_ADMIN'));
INSERT INTO user_roles(user_id, role_id) VALUES ((SELECT id from users where username = 'admin'),(SELECT id from roles where name = 'ROLE_USER'));
INSERT INTO user_roles(user_id, role_id) VALUES ((SELECT id from users where username = 'user'),(SELECT id from roles where name = 'ROLE_USER'));


-- Inserts for table Regions --
INSERT INTO regions (name) VALUES ('ASIA');
INSERT INTO regions (name) VALUES ('ÁFRICA');
INSERT INTO regions (name) VALUES ('EUROPA');
INSERT INTO regions (name) VALUES ('AMÉRICA DEL NORTE');
INSERT INTO regions (name) VALUES ('AMÉRICA DEL SUR');
INSERT INTO regions (name) VALUES ('OCEANÍA');

-- Inserts for table customers --
INSERT INTO customers (names, surnames, email, date_of_birth, created_at, update_at, region_id) VALUES ('Juan Camilo', 'Fandiño Benavides', 'jucamilogac@outlook.com', '2022-01-01', '2022-01-01', '2022-01-01', (SELECT id from regions where name = 'ASIA'));
INSERT INTO customers (names, surnames, email, date_of_birth, created_at, update_at, region_id) VALUES ('Jose Angel', 'Abellán Bustos', 'joseAngelBustos@outlook.com', '2022-01-01', '2022-01-01', '2022-01-01', (SELECT id from regions where name = 'ASIA'));
INSERT INTO customers (names, surnames, email, date_of_birth, created_at, update_at, region_id) VALUES ('Herberto', 'Pareja Ferrero', 'herbertoFerrero@outlook.com', '2022-01-01', '2022-01-01', '2022-01-01', (SELECT id from regions where name = 'ASIA'));
INSERT INTO customers (names, surnames, email, date_of_birth, created_at, update_at, region_id) VALUES ('Alfredo Onofre', 'Ortiz Talavera', 'alfredOnofredo@outlook.com', '2022-01-01', '2022-01-01', '2022-01-01', (SELECT id from regions where name = 'ÁFRICA'));
INSERT INTO customers (names, surnames, email, date_of_birth, created_at, update_at, region_id) VALUES ('Blas', 'Ordóñez Camacho', 'blasOrdonies@outlook.com', '2022-01-01', '2022-01-01', '2022-01-01', (SELECT id from regions where name = 'EUROPA'));
INSERT INTO customers (names, surnames, email, date_of_birth, created_at, update_at, region_id) VALUES ('Alfredo', 'San José', 'alfresoSanJose@gmail.com', '2022-01-01', '2022-01-01', '2022-01-01', (SELECT id from regions where name = 'EUROPA'));
INSERT INTO customers (names, surnames, email, date_of_birth, created_at, update_at, region_id) VALUES ('Bernardo', 'Naranjo', 'bernardo_orange@gmail.com', '2022-01-01', '2022-01-01', '2022-01-01', (SELECT id from regions where name = 'EUROPA'));
INSERT INTO customers (names, surnames, email, date_of_birth, created_at, update_at, region_id) VALUES ('Nieves', 'Anton', 'Antonn@gmail.com', '2022-01-01', '2022-01-01', '2022-01-01', (SELECT id from regions where name = 'EUROPA'));
INSERT INTO customers (names, surnames, email, date_of_birth, created_at, update_at, region_id) VALUES ('Maria Esther', 'Piqueras', 'MariaPiqueras1@gmail.com', '2022-01-01', '2022-01-01', '2022-01-01', (SELECT id from regions where name = 'AMÉRICA DEL SUR'));
INSERT INTO customers (names, surnames, email, date_of_birth, created_at, update_at, region_id) VALUES ('Andrea', 'Belmonte', 'ABelmonte@gmail.com', '2022-01-01', '2022-01-01', '2022-01-01', (SELECT id from regions where name = 'AMÉRICA DEL SUR'));
INSERT INTO customers (names, surnames, email, date_of_birth, created_at, update_at, region_id) VALUES ('Silvia Maria', 'Ferreira', 'SilMaFerreira@gmail.com', '2022-01-01', '2022-01-01', '2022-01-01', (SELECT id from regions where name = 'AMÉRICA DEL NORTE'));
INSERT INTO customers (names, surnames, email, date_of_birth, created_at, update_at, region_id) VALUES ('Maria Remedios', 'San José', 'MariaRemeSan@gmail.com', '2022-01-01', '2022-01-01', '2022-01-01', (SELECT id from regions where name = 'AMÉRICA DEL NORTE'));
INSERT INTO customers (names, surnames, email, date_of_birth, created_at, update_at, region_id) VALUES ('Maria Rocio', 'Leon', 'LaMariaLion@gmail.com', '2022-01-01', '2022-01-01', '2022-01-01', (SELECT id from regions where name = 'OCEANÍA'));
INSERT INTO customers (names, surnames, email, date_of_birth, created_at, update_at, region_id) VALUES ('Estefania', 'Villaverde', 'esteVillageGreen@gmail.com', '2022-01-01', '2022-01-01', '2022-01-01', (SELECT id from regions where name = 'OCEANÍA'));
INSERT INTO customers (names, surnames, email, date_of_birth, created_at, update_at, region_id) VALUES ('Camila Vicenta', 'de Haro', 'camilinaVicenta@gmail.com', '2022-01-01', '2022-01-01', '2022-01-01', (SELECT id from regions where name = 'OCEANÍA'));

-- Inserts for table products --
INSERT INTO products (name, price, created_at) VALUES ('Computador Portátil Gamer MSI 15.6', 4200000, '2022-01-01');
INSERT INTO products (name, price, created_at) VALUES ('Computador Portátil ASUS TUF Gaming F15', 5400000, '2022-01-01');
INSERT INTO products (name, price, created_at) VALUES ('Computador Portátil Gamer Victus HP 16.1', 5400000, '2022-01-01');
INSERT INTO products (name, price, created_at) VALUES ('Monitor SAMSUNG Gamer 27', 999901, '2022-01-01');
INSERT INTO products (name, price, created_at) VALUES ('Monitor SAMSUNG 32" Pulgadas BM500', 1120000, '2022-01-01');
INSERT INTO products (name, price, created_at) VALUES ('Monitor Gamer ACER 23.8', 930000, '2022-01-01');
INSERT INTO products (name, price, created_at) VALUES ('iPhone 11 128 GB Blanco', 2650000, '2022-01-01');
INSERT INTO products (name, price, created_at) VALUES ('Celular KALLEY Black G2 64GB Negro', 450000, '2022-01-01');
INSERT INTO products (name, price, created_at) VALUES ('iPhone 13 128GB Rosa', 4209000, '2022-01-01');

-- Inserts for table invoice --
INSERT INTO invoices (observation, description, created_at, customer_id) VALUES ('Observation1', 'Description1', '2022-01-01', (SELECT id FROM customers where names = 'Juan Camilo'));
INSERT INTO invoices (observation, description, created_at, customer_id) VALUES ('Observation2', 'Description2', '2022-01-01', (SELECT id FROM customers where names = 'Jose Angel'));

-- Inserts for table invoice details --
INSERT INTO invoice_details (amount, product_id, invoice_id) VALUES (10, (SELECT id FROM products where name = 'Monitor SAMSUNG Gamer 27'), (SELECT id FROM invoices where observation = 'Observation1'));
INSERT INTO invoice_details (amount, product_id, invoice_id) VALUES (2, (SELECT id FROM products where name = 'Computador Portátil Gamer MSI 15.6'), (SELECT id FROM invoices where observation = 'Observation1'));
INSERT INTO invoice_details (amount, product_id, invoice_id) VALUES (2, (SELECT id FROM products where name = 'iPhone 11 128 GB Blanco'), (SELECT id FROM invoices where observation = 'Observation2'));
