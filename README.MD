# Proyecto 1 - SpringBoot/Angular

This repository contains a project developed using Spring Boot for the backend and Angular for the frontend. The system is designed to manage customers, allowing for the creation, editing, and deletion of customers, as well as the association of images with each customer. Additionally, invoices can be registered for each customer, including products that are also managed by the system.

| username | password | permissions      |
|:--------:|:--------:| :---------------:|
| admin    | admin    | all permissions  |
| user     | user     | read permissions |


## Backend

### Technologies:

Java, Springboot and Postgresql.

### Versions: 

Springboot version: 2.7.4

Java version: 11

### Usage instructions

Clone the repository

```bash
git clone https://github.com/Juan-CamiloF/proyecto-springboot-1.git
```

Backend and database together

```bash
cd proyecto-1
docker compose up --build -d
```

Database 

```bash
cd proyecto-1
docker compose up --build -d db
```

Backend

```bash
cd proyecto-1
docker compose up --build proyecto1
```

### URLS
Swagger: http://localhost:8080/swagger-ui/

Jacoco: http://localhost:63342/proyecto-1/proyecto1/target/site/jacoco/index.html



## Frontend

### Technologies:

Angular and Angular material.

### Versions: 

Angular version: 13.2.6

Node version: 16.10.0

Npm version: 9.5.1

### Usage instructions

Clone the repository

```bash
git clone https://github.com/Juan-CamiloF/proyecto-springboot-1.git
```

With docker

```bash
cd frontend
docker build -t frontend:1 .
docker run -d -p 4200:4200 frontend:1
```

Without docker

```bash
npm install
ng serve
```
