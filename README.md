
# Rate Limiter

This project implements a rate limiter for Spring Boot in Microservices architecture. A rate limiter is a mechanism that restricts the number of requests a client can make to a particular API within a certain time window. Rate limiting helps protect APIs from abuse and prevents overloading of the server. Here for acheiving this, bucket4j dependency is used. Used Concurrent hashmap for storing the user and their limit details. ConcurrentHashMap is a thread-safe implementation of the Map interface in Java, which means multiple threads can access it simultaneously without any synchronization issues.

## Prerequisites:

- Java 8 or later
- Spring Boot 2.4.1 or later

## Bucket4j:

Bucket4j is a Java rate-limiting library based on the token-bucket algorithm. Bucket4j is a thread-safe library that can be used in either a standalone JVM application, or a clustered environment. 

## Installation:

 1. Clone the repository <br />
 
     git clone https://github.com/suryabalan/Rate_Limiter.git <br />
   
2. Build the project using Maven <br />

     - cd Rate Limiter <br />
     - mvn clean install <br />
     - mvn spring-boot:run (This service needs to be up and running as the junit test cases in other 2 services depends on this service)
     
   2.1. Do the above step for all the services <br />
     
     - cd UserOne
     - mvn clean install
     - mvn spring-boot:run
     - cd UserTwo
     - mvn clean install
     - mvn spring-boot:run
  
   2.2. We can also run all the services using the jar file
     
      - java -jar target/RateLimiter-0.0.1-SNAPSHOT
      - java -jar target/UserOne-0.0.1-SNAPSHOT
      - java -jar target/UserTwo-0.0.1-SNAPSHOT
      
3. Assumptions:
   
   - A service is considered as user. Here UserOne is one use and UserTwo is another user. There are 3 API's
   - Each API has a different rate limit.
   - Rate limit is done based on API + user combination.
   - Here the limits is considered per minute. For example: 5 requests per minute. After a minute, it gets updated automatically.
   - We can set the rate limit for each API. If not, we have default values of 2 requests per minute for api one, 5 requests per minute for api two and 
     10 requests per minute for api three.

4. Working logic:

   Rate Limiter service runs on port 7777. UserOne service runs on port 9999 and UserTwo service run on port 8888.
  
   Here , using this api in POST method: http://localhost:9999/api/v1/master and with the following JSON request: <br />
   { <br />
    "apiOne": 2, <br />
    "apiTwo":5,  <br />
    "apiThree":10 <br />
   } <br />
   we can set the rate limit for each api. We can also set limit for either one or two api's. If we don't set the limit before, the application will take default
   values.
  
   ![image](https://user-images.githubusercontent.com/44115585/222969316-34c27093-9c5a-4599-8eec-5d6d3a8925ed.png)
   
   Here, user one is calling api one. If the request is less than the limit, it will give a success response, othervise it throws an exception stating the remaining
   seconds in which the api will be unblocked for that particular user.
  
   ![image](https://user-images.githubusercontent.com/44115585/222969621-65753f00-5b58-4cf6-a0d7-08563a318cdf.png)

   Here, as the limit is reached, it throws an exception with the remaing seconds to unblock that api for that particular user.

   ![image](https://user-images.githubusercontent.com/44115585/222969645-be5f648d-bf41-4b3f-83d2-0b0dfcd63612.png)
  
  
   So, the same can be done for user two.The API's are 
   
   - http://localhost:8888/api/v1/api-one
   - http://localhost:8888/api/v1/api-two
   - http://localhost:8888/api/v1/api-three
  
  
 

  




