# Inditex Service

## Introduction

This microservice allows to fetch the price of a product in a date interval

## Setup

### Installation

Simply move to the project's root folder and run from a terminal:

```
mvn clean install
```

### Execution

Starting from the project's root folder, execute the following steps from a new terminal:

1. Run like java application:

    ```
    java -jar inditex-boot/target/inditex-boot-0.0.1-SNAPSHOT.jar
    ```

2. Spring-boot app (from the root of the project):

    ```
   mvn spring-boot:run -pl inditex-boot
    ```

## Features
### API's

* Prices_Management
  * You can check the API accessing to the next url: http://localhost:9090/swagger-ui.html

## How to test the microservice

After you run the microservice like describing before, I have attached a postman collection 
with 6 request:
* Response 200: it finds a price to the interval time, identification product and identification group
* Response 204: it does not find a price to the interval time, identification product and identification group
* Response 400: when the required param 'date' is not informed
* Response 400: when the param 'date' is malformed
* Response 400: when the param 'product_id' is not of right type
* Response 400: when the param 'brand_id' is not of right type

## Technologies Used

* **Platform:** Java 17
* **Project type:** Microservice
* **Spring Boot version:** 3.3.2

## Contact

| Name   | Role  |
|:-------|:------|
| Manuel Jesús España|Developer|
