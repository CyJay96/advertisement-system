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


---

### :pushpin: **API Endpoints**

**Authentication**

| **HTTP METHOD** |        **ENDPOINT**        | **DESCRIPTION** |
|:---------------:|:--------------------------:|:---------------:|
|    **POST**     |  `/api/v0/auth/register`   |  Register User  |
|    **POST**     |    `/api/v0/auth/login`    | Authorize User  |
|    **POST**     |      `/api/v0/logout`      |   Logout User   |

**Users**

| **HTTP METHOD** |                **ENDPOINT**                |      **DESCRIPTION**      |
|:---------------:|:------------------------------------------:|:-------------------------:|
|     **GET**     |            `/auth/api/v0/users`            |      Find all Users       |
|     **GET**     |         `/auth/api/v0/users/{id}`          |      Find User by ID      |
|     **GET**     | `/auth/api/v0/users/byUsername/{username}` |   Find User by username   |
|     **PUT**     |         `/auth/api/v0/users/{id}`          |     Update User by ID     |
|    **PATCH**    |         `/auth/api/v0/users/{id}`          | Partial Update User by ID |
|    **PATCH**    |     `/auth/api/v0/users/restore/{id}`      |    Restore User by ID     |
|   **DELETE**    |         `/auth/api/v0/users/{id}`          |     Delete User by ID     |

**Advertisers**

| **HTTP METHOD** |            **ENDPOINT**            |         **DESCRIPTION**          |
|:---------------:|:----------------------------------:|:--------------------------------:|
|    **POST**     |       `/api/v0/advertisers`        |       Save new Advertiser        |
|     **GET**     |       `/api/v0/advertisers`        |       Find all Advertisers       |
|     **GET**     |   `/api/v0/advertisers/criteria`   | Find all Advertisers by Criteria |
|     **GET**     |     `/api/v0/advertisers/{id}`     |      Find Advertiser by ID       |
|     **PUT**     |     `/api/v0/advertisers/{id}`     |     Update Advertiser by ID      |
|    **PATCH**    |     `/api/v0/advertisers/{id}`     | Partial Update Advertiser by ID  |
|    **PATCH**    | `/api/v0/advertisers/restore/{id}` |     Restore Advertiser by ID     |
|   **DELETE**    |     `/api/v0/advertisers/{id}`     |     Delete Advertiser by ID      |

**Campaigns**

| **HTTP METHOD** |           **ENDPOINT**           |        **DESCRIPTION**         |
|:---------------:|:--------------------------------:|:------------------------------:|
|    **POST**     |   `/api/v0/campaigns/{newsId}`   |       Save new Campaign        |
|     **GET**     |       `/api/v0/campaigns`        |       Find all Campaigns       |
|     **GET**     |   `/api/v0/campaigns/criteria`   | Find all Campaigns by Criteria |
|     **GET**     |     `/api/v0/campaigns/{id}`     |      Find Campaign by ID       |
|     **PUT**     |     `/api/v0/campaigns/{id}`     |     Update Campaign by ID      |
|    **PATCH**    |     `/api/v0/campaigns/{id}`     | Partial Update Campaign by ID  |
|    **PATCH**    | `/api/v0/campaigns/restore/{id}` |     Restore Campaign by ID     |
|   **DELETE**    |     `/api/v0/campaigns/{id}`     |     Delete Campaign by ID      |

**Countries**

| **HTTP METHOD** |           **ENDPOINT**           |        **DESCRIPTION**         |
|:---------------:|:--------------------------------:|:------------------------------:|
|    **POST**     |   `/api/v0/countries/{newsId}`   |        Save new Country        |
|     **GET**     |       `/api/v0/countries`        |       Find all Countries       |
|     **GET**     |   `/api/v0/countries/criteria`   | Find all Countries by Criteria |
|     **GET**     |     `/api/v0/countries/{id}`     |       Find Country by ID       |
|     **PUT**     |     `/api/v0/countries/{id}`     |      Update Country by ID      |
|    **PATCH**    |     `/api/v0/countries/{id}`     |  Partial Update Country by ID  |
|    **PATCH**    | `/api/v0/countries/restore/{id}` |     Restore Country by ID      |
|   **DELETE**    |     `/api/v0/countries/{id}`     |      Delete Country by ID      |

**Languages**

| **HTTP METHOD** |           **ENDPOINT**           |        **DESCRIPTION**         |
|:---------------:|:--------------------------------:|:------------------------------:|
|    **POST**     |   `/api/v0/languages/{newsId}`   |       Save new Language        |
|     **GET**     |       `/api/v0/languages`        |       Find all Languages       |
|     **GET**     |   `/api/v0/languages/criteria`   | Find all Languages by Criteria |
|     **GET**     |     `/api/v0/languages/{id}`     |      Find Language by ID       |
|     **PUT**     |     `/api/v0/languages/{id}`     |     Update Language by ID      |
|    **PATCH**    |     `/api/v0/languages/{id}`     | Partial Update Language by ID  |
|    **PATCH**    | `/api/v0/languages/restore/{id}` |     Restore Language by ID     |
|   **DELETE**    |     `/api/v0/languages/{id}`     |     Delete Language by ID      |
