# Warehouse API

Проект с курса "Spring Framework" академии разработки Mediasoft.
___

__Описание:__
Простое CRUD-приложение для работы склада товаров

__Технологии:__
* Spring Boot,
* Spring JPA
* Hibernate,
* PostgreSQL
* H2 Base

### Инструкция по запуску

__Локально:__
1. ```mvn clean package```
2. ```java -jar warehouse-0.0.1.jar```

__В Docker:__
1. ```mvn clean package```
2. ```mvn install```
3. ```docker-compose build```
4. ```docker-compose up -d```