
# Journal Application

## Endpoints 

All endpoints are in RESTFULL format.

### 1- Create Article

Creates an article with the following mandatory information
- Title
- Author
- Content
- Publish Date (ISO 8601)

[POST] /journal/v1/articles/

Authorized Roles: USER, ADMIN

### 2- List Articles

List articles by pagination. Mandatory page parameters;
- page
- size

[GET] /journal/v1/articles?page={INTEGER}size={INTEGER}

Authorized Roles: USER, ADMIN

### 3- Statistics

Lists statistics of last seven days. If optional `endDate` parameter is specified then the endpoint returns 
the last seven days until that day.

[GET] /journal/v1/articles/statistics?endDate={DATE}

Authorized Roles: ADMIN

## Code Structure

Hexagonal architecture is used in this project.
- `domain` module indicates the business side. It doesn't contain any framework.
- `infra` module is the infrastructure module. It is built with spring framework.

### Domain Module

This module contains three use-case handler;
- Create
- List
- Publish
There are some ports (interfaces) that can be implemented in any infrastructure.

### Infra Module

This module uses `domain` module and implements ports(interfaces).
Endpoints are in this module. 
In infrastructure module, the following technologies are used for integration;
- spring boot framework 
- Data JPA 
- quartz (clustered scheduling operations)
- MySQL

## Installation
### Method-1 (By downloading and installing all necessary applications)
1- Please download MySQL Community Server 8.0.31 (https://dev.mysql.com/downloads/mysql/)

- Execute [schema.sql](infra/src/main/resources/schema.sql)

- Database can be created automatically by modifying some properties before run the [application.properties](src/main/resources/application.properties): 

```properties
spring.sql.init.data-locations=classpath:schema.sql
spring.sql.init.mode=always
```
2- Please download Java 11 (https://jdk.java.net/archive/)

- [Windows](https://download.java.net/java/GA/jdk11/9/GPL/openjdk-11.0.2_windows-x64_bin.zip) 
- [Mac](https://download.java.net/java/GA/jdk11/9/GPL/openjdk-11.0.2_osx-x64_bin.tar.gz) 

3- Please download Maven 3.8.2 [download link](https://maven.apache.org/docs/3.8.2/release-notes.html)

4- Define bin directories of JDK and Maven into the System Path

5- Run following command on the root path of project

```shell
cd journal
mvn clean package -DskipTests
java -jar infra/target/journal-infra-1.0-SNAPSHOT.jar
```

### Method-2 (By using Docker)
1- Please download and install Docker https://www.docker.com/products/docker-desktop/

2- Execute the following docker command to run MySQL and create `journal` database :
```shell
docker-compose -f docker-compose-mysql.yml up -d
```

3- Connect to journal database and execute [schema.sql](infra/src/main/resources/schema.sql)

- Database can be created automatically by modifying some properties before run the [application.properties](src/main/resources/application.properties):
```properties
spring.sql.init.data-locations=classpath:schema.sql
spring.sql.init.mode=always
```
4- In order to run the Spring project, please execute the following code.

```shell
docker-compose -f docker-compose-spring.yml up --build -d
```

## Usage

- Import [Postman Collection](Journal.postman_collection.json)
- All endpoints are authorized, so at first, call one of the /login endpoints: One for USER, and other for ADMIN
- Then call /create, /list, /statistics 

