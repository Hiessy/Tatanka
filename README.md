

![alt text](https://github.com/Hiessy/Tatanka/blob/master/src/main/resources/image/icon.png?raw=true "Logo Title Text 1")

# Tatanka Project

Simple restful API as an example, with some CRUD operations and data storage logic:

## Table of Contents
1. [Context](#context)
2. [Getting Started](#getting-started)<br>
		a.[Prerequisites](#prerequisites)<br>
		b.[Installing](#installing)
3. [Checking code coverage](#checking-code-coverage)
4. [Running the Tatanka](#running-the-tatanka)
5. [API Reference](#api-reference)
6. [Built With](#built-with)
7. [Author](#author)

## Context
1. Rental by hour, charging $5 per hour
2. Rental by day, charging $20 a day
3. Rental by week, charging $60 a week
4. Family Rental, is a promotion that can include from 3 to 5 Rentals (of any type) with a discount of 30% of the total price

## Getting Started

The source code was build in IntelliJ IDEA community edition as a maven project. Download or clone the repository into the desired path and import in IntelliJ. To export the project to Eclipse please read the following [export to eclipse guide](https://www.jetbrains.com/help/idea/exporting-an-intellij-idea-project-to-eclipse.html), however intelliJ is required in order to test for code coverage, since it was tested in that IDE.

### Prerequisites

In order to build the project, the following programas will have to be installed:

[Java jdk 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)<br>
[Maven 3.x](https://maven.apache.org/download.cgi)<br>
[IntelliJ IDEA](https://www.jetbrains.com/idea/download/index.html) <small><small>**Not required for maven testing**</small></small><br> 
### Installing

Open a terminal or prompt and navigate to the desired folder and clone the repository

```
c:\Users\hiessy> cd IdeaProjects
c:\Users\hiessy\IdeaProjects> git clone https://github.com/Hiessy/Tatanka.git
```

Navigate into the cloned folder and run the maven test suite

```
c:\Users\hiessy\IdeaProjects> cd Tatanka
c:\Users\hiessy\IdeaProjects\Tatanka>mvn test
```

Once complete you should be able to see the BUILD SUCCESS.
## Checking code coverage

It is recommended that code coverage be checked with the IntelliJ IDE. In order to do this, open up the IDE and import the project. Once complete click on [Viewing Code Coverage Results](https://www.jetbrains.com/help/idea/viewing-code-coverage-results.html) for a guide on how to achieve this.
## Running the Tatanka
Once the tests have cleared you can run the spring boot project by packaging it with maven compiler plugin defined in the *pom* file. This will rerun the test suite again, so you could probably skip running the tests with maven if you plan on building
```
c:\Users\hiessy\IdeaProjects\Tatanka>mvn package
```
When the build is complete, you should see the BUILD SUCCESS message. Navigate to the target directory and run the jar package
```
c:\Users\hiessy\IdeaProjects\Tatanka>cd target
c:\Users\hiessy\IdeaProjects\Tatanka\target>java -jar rental-1.0-SNAPSHOT.jar
```
This is a self contained spring boot project with an embedded database H2 so it should start fine. Tatanka logs folder on the path where it is running and write to the rental.log file inside. Make sure there are no other programs listening on port 8080 when running Tatanka.

There is also a doc folder with the auto generated Javadoc from IntelliJ IDE.

## API Reference
The following show how to consume the exposed API. **Data Params** should be placed in **json** object and the ***Content-Type*** should have the ***application/json*** when required.

| URL        | Method  | URL Params      | Data Params     | Success Response | Error Response
|:-------------:|:-----:|:-------------:|:-----|:---------:|:-------------:|
| /v1/rental  | <font color="#008800">GET</font> |  |  | **200** | **404** |
| /v1/rental/{rentalId} | <font color="#DD0000">DELETE</font> | rentalId |  | **200** | **404** |
| /v1/rental | <font color="#6600FF">PUT</font> |  | time<br> numberOfBikes | **200** | **400**  |
| /v1/user/{userId}/rental      | <font color="#0000EE">POST</font> | userId | time<br> numberOfBikes<br> rentalDateRequested | **201** | **404**<br> **400** |
| /v1/user |   <font color="#0000EE">POST</font> |    | name<br> password | **201**   | **409**<br> **400** |
| /v1/user/{name} | <font color="#008800">GET</font> | name |  | **200**  | **409**<br> **400**     |
## Built With

* [Spring Boot](https://projects.spring.io/spring-boot/) - The web framework
* [Spring HATEOAS](https://projects.spring.io/spring-hateoas/) - Following the HATEOAS principle
* [Hibernate](https://docs.jboss.org/hibernate/orm/4.1/manual/en-US/html/) - ORM framework
* [JUnit 4.12](https://junit.org/junit4/) - Unit testing framework
* [Mokito](http://site.mockito.org/) - Mocking framework used
* [Maven](https://maven.apache.org/) - Dependency Management

## Author

* **Martin Diaz** - *Initial work* - [Hiessy](https://github.com/Hiessy)
