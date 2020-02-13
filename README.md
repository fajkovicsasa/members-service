# Member service

Simple web service for managing user database with support for:
- retrieving all users
- retrieving a specific user
- create a new user
- update a user
- delete all inactive users

All the endpoints follow the standard REST principle.

<b>Tech stack:</b>
- OpenJDK 1.8
- Gradle 6.0.1
- Spring Boot, Spring REST, Spring DATA JPA, Lombok
- H2 database
- jUnit 5

### Build & Run
- Run ``gradlew build`` to compile the service.
- Run ``gradlew bootRun`` to run the service.

### Execute tests
- Run ``gradlew test`` to execute tests.

## Usage examples

#### Retrieve all members

<b>Request:</b>
``
[GET] /api/v1/members?page=0&size=2
``

<b>Response:</b>
- Produces: application/json
<pre>
[
    {
        "id": 1,
        "firstName": "Sasa",
        "lastName": "Fajkovic",
        "dateOfBirth": null,
        "picture": null,
        "active": true
    },
    {
        "id": 2,
        "firstName": "Pink",
        "lastName": "Floyd",
        "dateOfBirth": null,
        "picture": null,
        "active": false
    }
]
</pre>

---
#### Retrieve a specific member details

Request:
``
/api/v1/members/1
``

<b>Response:</b> 

- Produces: application/json
<pre>
[
    {
        "firstName": "Sasa",
        "lastName": "Fajkovic",
        "dateOfBirth": null,
        "picture": null,
        "active": true
    }
]
</pre>

---
#### Add new user
<b>Request:</b> 
``
[POST] api/v1/members
``
 - Consumes: application/json
 
<pre>
{
    "firstName": "Sasa",
    "lastName": "Fajkovic",
    "dateOfBirth": "1989-05-10",
    "picture": null,
    "active": true
}
</pre>
---

#### Update user details
<b>Request:</b> 
``
[PUT] api/v1/members/1
``
 - Consumes: application/json
 
<pre>
{
    "firstName": "Sasa",
    "lastName": "Fajkovic",
    "dateOfBirth": "1989-05-10",
    "picture": null,
    "active": true
}
</pre>
---

#### Delete inactive users
<b>Request:</b> 
``
[DELETE] api/v1/members?deleteAllInactive=true
``

