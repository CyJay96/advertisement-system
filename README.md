<h1 align="center">Advertisement Management System RESTful Web Service</h1>

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

---

### :zap: **Description**

This is a RESTful web service built with Spring Boot, Java, Gradle and PostgreSQL,
which provides functionality for managing Advertisers and Campaigns. The service includes support
for creating, updating and deleting Advertisers, Campaigns, Countries, Languages and Users.

---

### :white_check_mark: **The stack of technologies used**
:star: **API Technologies:**
- SOLID
- OOP
- DI
- REST

:desktop_computer: **Backend technologies:**
- Java 17
- Spring Boot 3, Spring Security
- JWT

:hammer_and_wrench: **Build Tool:**
- Gradle 8.4

:floppy_disk: **DataBase:**
- PostgreSQL
- LiquiBase Migration

:heavy_check_mark: **Testing:**
- Junit 5
- Mockito

:whale: **Containerization:**
- Docker

---

### :rocket: **Get Started**

**Build with Gradle**

Build module:

    $ ./gradlew clean build

If in case face any issue while building module because of test cases, then try build with disabling test cases:

    $ ./gradlew clean build -x test

Run Application using Docker Compose:

    $ docker-compose up

Browse OpenAPI Documentation:  
`localhost:8080/swagger-ui.html`  
`localhost:8080/v3/api-docs`
