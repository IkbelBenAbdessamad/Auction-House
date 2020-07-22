# Auction House WEB Application

Develop a web application that manages an auction house. The core functionalities of this app are listed in the below list:
- [X]    Create/List/Delete an(all) auction(s) house.
- [X]     Create/List/Delete/UpdateStatus an(all) auction(s) of a specific auction house.
- [X]     Bid in an auction and display the winner of an auction.
- [X]     Create/List/Delete an(all) auction Bidder(s).

## Requirements

For building and running the application you need:

- JDK 1.8 or higher (This project uses the OpenJDK11, with compiler compliance level settled to 8)
- Maven 3.X (this project uses Apache Maven 3.6.0)
## Running the application locally

There are several ways to run the app on your local machine. One way is to execute the main method in the `com.spideo.auction_house.AuctionHOuseAplication.java` class from your IDE.

Or with **Maven** as below:

> NOTE: you don't have to install maven in your local machine as it is wrapped with the project

> NOTE: You must be in the root folder of the project where  src, docs, and other files are  located


1. Install all the  required libraries 

`./mvnw clean install`

2. Run the application 

`./mvnw spring-boot:run`

3. Or, you can package the project and  run the packaged  jar file

 `./mvnw clean package`
 
 `java -jar target/auction_house-0.1.jar    `
 
 
 ## Main technologies and frameworks

- Spring-Boot - Java-based framework 
- Maven - Dependency Management & The project build tool
- Tomcat -  web server and servlet container, comes embedded with the spring-boot app 
- Lombok - Java library to minimize/remove the boilerplate code by just using some annotations.
- Swagger/Swagger-UI - visualize, document and interact with the API's resources without having any of the implementation logic in place

 
 ##  API's documentation and testing using Swagger/Swagger-UI

For visualizing the detailed documentation of the app API's, along with testing them,  please refer to the Project's Swagger Page [API'S_Documentation_and_testing link](http://localhost:8080/swagger-ui.html)

The below  pic defines  the generated Swagger-Ui page

![image](https://user-images.githubusercontent.com/42076893/88170021-3eec1a80-cc1d-11ea-9955-88aa00f91976.png)




