#System Architecture Diagram:

                   +---------------------------------------+
                   |     Frontend (HTML/JS)        			|
                   |---------------------------------------|
                   | - http://localhost:8888/login.html    |
                   | - http://localhost:8888/register.html |
                   | - login.js                            |
                   | - register.js                         |
                   +-------------+-------------------------+
                                 |
                           HTTP Requests
                                 |
                                 v
       +-------------------------+------------------------+
       |               Spring Boot REST API               |
       |--------------------------------------------------|
       | - POST /api/auth/register                        |
       | - POST /api/auth/login                           |
       +-------------------------------+------------------+
                                       |
                             JPA Repository Access
                                       |
                                       v
                        +-----------------------------+
                        |     MySQL Database (authdb) |
                        |-----------------------------|
                        | Table: user                 |
                        +-----------------------------+

#API DETAILS:

-POST /api/auth/register
>Request Body: {
  "username": "string",
  "email": "string",
  "password": "string"
}

Response: 200 OK
Response Body: "User registered successfully"
==================================================
-POST /api/auth/login
>Request Body: {
  "email": "string",
  "password": "string"
}

Response: 200 OK
Response Body: {
  "token": "jwt-token"
}

#DB Details:
MYSQL Database is already configured in my local system.
The User table will be auto generated during the time of registration if its first registration ever.
