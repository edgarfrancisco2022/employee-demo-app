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

### Security diagram
1. User calls registration endpoint -> /addNewUser <br>
2. UserInfoService encodes the password and saves user to the database <br>
3. User calls login endpoint with username and password to generate JWT -> /generateToken <br>
4. A new Authentication object is created with the UsernamePasswordAuthenticationToken class <br>
5. AuthenticationManager is called to check authentication <br>
6. AuthenticationManager uses the Authentication Provider class DaoAuthenticationProvider <br>
7. DaoAuthenticationProvider uses UserDetailsService to check the database for matching user <br>
8. If user exists, a UserDetails object is created with UserInfoDetails <br>
9. DaoAuthenticationProvider uses BCryptPasswordEncoder to check password matches <br>
10. If user is authenticated successfully the JwtService is called to generate a new token <br>
11. A new token is generated is using the given SECRET <br>
12. The token is returned to the user <br>
13. User sends request to protected endpoint adding the token in an Authorization header that starts with 'Bearer ' <br>
14. The JwtAuthFilter intercepts the request, it extracts the username and loads it from the database <br>
15. The token is validated, and new UsernamePasswordAuthenticationToken object is created and added to the security context <br>
16. After this user may access protected resources if he has sufficient authorities <br>
