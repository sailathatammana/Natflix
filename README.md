# Natflix

Natflix is a backend server application which can be used to create content, series details, movies & documentaries
details, users and store all the data in the database.

The application is build using Spring,Docker and PostgreSQL. Check the following links for documentation and guides:

- [Spring](https://spring.io/projects/spring-boot)
- [Docker](https://www.docker.com/)
- [PostgreSQL](https://www.postgresql.org)

## Setup

Our development environment for a application will consist of:

1. Database (Postgres).
2. Backend server (Spring).

### Prerequisites

- `docker` and `docker-compose`.
- `nodejs`.
- `Java 11`.
- `GIT (optional)`.
- `IDE able to build projects if you plan to run from an IDE`.

### Starting the database

In the root folder, run

```
docker-compose up
```

### Starting the backend server

Open the root folder and run

```
./gradlew bootRun
```

The front-end build is integrated with the back-end, and stored in the /static/folder.So, Open browser and
visit http://localhost:8080 for the front-end web interface.One can also run
Front-end application manually by following the instructions [here](https://github.com/sailathatammana/Natflix-frontend)

## Project organization

### Requirement gathering

A spreadsheet with the information related to the organization of the project. It includes user-stories, tasks and time
estimation.

[Google Spreadsheets link](https://docs.google.com/spreadsheets/d/1e8AjgA1QqndipJfiHLn4NMDeXEXxFiOp0BjwKIiCiKg/edit#gid=0)

### Use case diagram

A low detail diagram to visualize how the application will work.

[Use case Diagram](https://bit.ly/3W4sdad)

### Class diagrams

The class diagram allows to visualize the overall hierarchy of the project.

[Class Diagram](https://bit.ly/3SJqfZL)

[Entity Relationship Diagram](https://bit.ly/3DyIw7N)