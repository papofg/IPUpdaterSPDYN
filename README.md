# IPUpdaterSPDYN

Client to update the IP address for your dynamic dns host (http://spdyn.de) 
**(Spring Boot, Spring Security, JPA, Hibernate, Quartz, Thymeleaf, Bootstrap)**

## Getting Started

Download the project

### Prerequisites

* Java 8, MySQL, Maven
* You should have an account at spdyn.de
* You should create a host at your spdyn.de account

### Installing

* Create a database in your MySQL and executes [ddl.sql](https://github.com/papofg/IPUpdaterSPDYN/blob/master/src/main/resources/ddl.sql)  
* Edit **spring.datasource.url**, **spring.datasource.username** and **spring.datasource.password** in [application.properties](https://github.com/papofg/IPUpdaterSPDYN/blob/master/src/main/resources/application.properties) 
* Add **server.port** in [application.properties](https://github.com/papofg/IPUpdaterSPDYN/blob/master/src/main/resources/application.properties) to change the listen port (default 8080)

### Suggested IDE

Spring Tool Suite 4

### Compiling with Maven

```
mvn package
```

### Deployment

```
mvn spring-boot:run
```

### Accessing

* Open your navigator and go to http://[your_address]:[listen_port]
* The initial users are **dbadmin1** (READ/WRITE) and **dbuser1** (READ ONLY)
* Their passwords are **123**

### Scheduled job

Every 10 minutes the system will check the IP and update it at SPDYN if it changed
