
# Customer REST API
Sample Customer Rest API using `Spring Boot` and  `Apache Camel`

    1. Spring Boot: v2.2.2
    2. Apache Camel: v3.0.0
    3. Java: v1.8
    4. Maven: v3.6.0
    5. Junit jupiter: v5.5.2
    6. springfox-swagger2: v2.8.0
    7. springfox-swagger-ui: v2.8.0
    8. Docker
    9. Git
    10.Json
    

**Clone the application**

```bash
git clone https://github.com/aziz781/customer-api.git
```

## Three ways to Build & Run

**1. Maven**
>Build

```bash
mvn package 
```

>Run

```bash
java -jar target/customer-api-1.0.0.jar
```


Alternatively, you can run the app without packaging it using
>Build & Run

```bash
mvn spring-boot:run
```


**2. Docker**

>Build

```bash
docker build -t aziz781/customer-api .
```

>Run

```bash
docker run -d -p 8080:8080 -t aziz781/customer-api
```


**3. Docker-compose**

> Build & Run

```bash
docker-compose up
```


##  How to use Customer Rest API

The app will start running locally at <http://localhost:8080/>

**API Documentation**
>The API documentation at http://localhost:8080/swagger-ui.html


**API Resource**
The API expose the the following resource

    POST /api/v1/customers
    
**API URL**

The POST endpoint for customer registration.

    http://localhost:8080/api/v1/customers
    
**Request Payload**

The sample request JSON payload for Customer Rest API.

>Frontend Json

```javascript
{
  "firstName": "Abdul",
  "lastName": "Aziz",
  "phone": "0123456789",
  "email": "customer@sample.com"
}
```


* The frontend json is validated using frontend schema
* The frontend json is Transformed to backend json
* The backend json is validated against backend schema 

**NOTE**:
`The transformation is implemented using camel Jolt component to process a JSON messages using an JOLT specification. 
This is for JSON to JSON transformation.`

>Backend Json

```javascript
{
  "userFirstName": "Abdul",
  "userLastName": "Aziz",
  "contact":{
    "phoneNumber": "0123456789",
    "emailAddress": "customer@sample.com"
  }
}
```

>Mock Backend service at http://localhost:9001/registrations
`Mock service is implemented using Camel Rest DSL with  Netty HTTP component  to facilitiate HTTP transport.`

## Responses

201

    {
      "status": "SUCCESS",
      "message": "Customer registered successfully"
    }
     

400
 
     {
       "status": "ERROR",
       "message": "firstName: is missing but it is required"
     }


400
 
     {
       "status": "ERROR",
       "message": "firstName: must be at least 3 characters long"
     }

400
 
     {
       "status": "ERROR",
       "message": "Invalid Json Format"
     }


  
## CURL  

```ruby
curl -i -X POST "http://localhost:8080/api/v1/customers" -H "accept: application/json" -H "Content-Type: application/json" -d ""


HTTP/1.1 400
Content-Type: application/json


{"status":"ERROR","message":"Invalid Json Format"}
```


```ruby 
curl -i -X POST "http://localhost:8080/api/v1/customers" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"firstName\": \"\", \"lastName\": \"greatman\", \"phone\": \"012345678\", \"email\": \"tester@api.com\" }"

HTTP/1.1 400
Content-Type: application/json


{"status":"ERROR","message":"firstName: is missing but it is required"}
```

```ruby 
curl -i -X POST "http://localhost:8080/api/v1/customers" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"firstName\": \"my\", \"lastName\": \"greatman\", \"phone\": \"012345678\", \"email\": \"tester@api.com\" }"

HTTP/1.1 400
Content-Type: application/json


{"status":"ERROR","message":"firstName: must be at least 3 characters long"}
``` 

```ruby 
curl -i -X POST "http://localhost:8080/api/v1/customers" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"firstName\": \"tester\", \"lastName\": \"greatman\", \"phone\": \"012345678\", \"email\": \"tester@api.com\" }"

HTTP/1.1 201
Content-Type: application/json


{"status":"SUCCESS","message":"Customer registered successfully"}
``` 

 
## Docker Hub  
 
```ruby
docker pull aziz781/customer-api:latest
```
  
 
 
  