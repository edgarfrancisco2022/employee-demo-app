# employee-demo-app

### Register new user
POST http://localhost:8080/auth/addNewUser <br>
{ <br>
    "name": "user", <br>
    "email": "user@gmail.com", <br>
    "password": "password", <br>
    "roles": "ROLE_USER,ROLE_ADMIN" <br>
} 

### Generate JWT 
POST http://localhost:8080/auth/generateToken <br>
{ <br>
    "username": "user", <br>
    "password": "password" <br>
} <br>

### Add JWT as an Authorization header with the value starting with the word 'Bearer '
Example: <br>
Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlZGdhciIsImlhdCI6MTczNDkyNTE5MSwiZXhwIjoxNzM0OTI2OTkxfQ.qTEuqVYI8WYXYR6JczzVTZJU9Kk95KYW5s9QXvGMwX0 <br>

### Swagger
endpoint: /swagger-ui.html

### H2 console
username: sa  
password:  
endpoint: /h2-console

