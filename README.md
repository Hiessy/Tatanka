
# Tatanka Project

Simple restfull api for registering user, and getting a quotation on the price for specific bikes rentals:

1. Rental by hour, charging $5 per hour
2. Rental by day, charging $20 a day
3. Rental by week, charging $60 a week
4. Family Rental, is a promotion that can include from 3 to 5 Rentals (of any type) with a discount of 30% of the total price

## Getting Started

The source code was build in IntelliJ IDEA community edition as a maven project. Download or clone the repository into your desired local path and just import in IntelliJ. To export the project to Eclipse please read the following [export to eclipse guide](https://www.jetbrains.com/help/idea/exporting-an-intellij-idea-project-to-eclipse.html), however intelliJ is required in order to do this.

### Prerequisites

In order to build the project, the following will be required:

[Java jdk 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
[IntelliJ IDEA](https://www.jetbrains.com/idea/download/index.html)
[Maven 3.x](https://maven.apache.org/download.cgi)

### Installing

In order to run the test your must first clone the project to the desired path locally

open a terminal or prompt and navigate to the desired folder and clone the repository

```
c:\user\hiessy> cd IdeaProjects
c:\user\hiessy\IdeaProjects> git clone https://github.com/Hiessy/Tatanka.git
```

Navigate into the cloned folder and run the maven test suite

```
c:\user\hiessy\IdeaProjects> cd Tatanka
c:\user\hiessy\IdeaProjects\Tatanka>mvn test
```

Once complete you should be able to see the BUILD SUCCESS result
## Checking code coverage

It is recommended that code coverage be checked with the IntelliJ IDE. In order to do this, open up the IDE and import the project. Once complete click on [Viewing Code Coverage Results](https://www.jetbrains.com/help/idea/viewing-code-coverage-results.html) for a guide on how to achieve this.
## Running the Tatanka
Once the tests have cleared you can run the spring but project by packagin it with maven first
```
c:\user\hiessy\IdeaProjects\Tatanka>mvn package
```
When the build is complete, after you see the BUILD SUCCESS message cd to the target directory and run the jar package
```
c:\user\hiessy\IdeaProjects\Tatanka>cd target
c:\user\hiessy\IdeaProjects\Tatanka\target>java -jar rental-1.0-SNAPSHOT.jar
```
This is a self contained spring boot project with an embedded database H2 so it should start fine. Tatanka create a log folder on the path where it is running and write to the rental.log file inside.

There is also a doc folder with the auto generated Javadoc from IntelliJ IDE