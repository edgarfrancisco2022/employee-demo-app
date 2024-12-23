# employee-demo-app

### Register new user
POST http://localhost:8080/auth/addNewUser 
{ 
    "name": "user", 
    "email": "user@gmail.com", 
    "password": "password", 
    "roles": "ROLE_USER,ROLE_ADMIN" 
} 

### Generate JWT 
POST http://localhost:8080/auth/generateToken 
{ 
    "username": "user", 
    "password": "password" 
} 

### Add JWT as an Authorization header with the value starting with the word 'Bearer '
Example: 
Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlZGdhciIsImlhdCI6MTczNDkyNTE5MSwiZXhwIjoxNzM0OTI2OTkxfQ.qTEuqVYI8WYXYR6JczzVTZJU9Kk95KYW5s9QXvGMwX0 

### Swagger
endpoint: /swagger-ui.html

### H2 console
username: sa  
password:  
endpoint: /h2-console

