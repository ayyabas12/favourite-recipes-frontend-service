# Frontend Service

Requirements
For building and running the application you need:
# Technologies
1) JDK 18
2) Apache Maven 3


**Build and Deploy the Application**

Navigate to the project, and build App

1) Clone the application
2) Navigate to the project, and build App
3) mvn clean install (Build and run the app using maven)
4) Navigate to the application (favourite-recipes-service-application)
5) run the app (mvn spring-boot:run) or java -jar jarName.jar
6) The app will start running at http://localhost:8080
7) Swagger url http://localhost:8080/swagger-ui/

8) Clone favourite-recipes-frontend-service
9) mvn clean install (Build and run the app using maven)
10) run the app (mvn spring-boot:run) or java -jar jarName.jar
11) http://localhost:7001/h2-ui/
12) JDBC URL :jdbc:h2:mem:testdb
13) user name:sa and password:sa

**Libraries used**
 1) Spring Boot 2.6.3.RELEASE
 2) Spring Boot Test
 3) JSON Library(javax.json)
 4) Swagger API
 5) Hibernate Validation API (org.hibernate)
 6) H2 Database


**Features**

  **About the Service**
  This is Service just a simple Recipes REST service to get the data persistence layer and return to ui. It uses an in-memory database (H2) to store and retrieve the data

These services can perform,
1) Ingredients
2) Recipes
3) Add/update/GET/Delete and Search

Note :
      start the favourite-recipes-persistence-service api

API Insert Data :

1) First Insert Data into Ingredients using following api
   http://localhost:8080/dish-frontend-service/ingredient
   Method : Post
{
   "ingredientName":"tomoto",
   "imageURL": "tomoto.png"
   }  


2) Second Insert data into Recipes table using following api
   http://localhost:8080/dish-frontend-service/recipes
   Method : POST
   {
   "recipeName":"Chicken Curry",
   "quantity":3,
   "instructions":"New Food  checken all non vegetarian recipes",
   "category":"NON_VEGETARIAN",
   "ingredientId":[From first API result]
  }