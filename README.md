Solventum Coding Challenge Spring Boot URL Shortener


Getting Started
Install Junit 
Install Maven
Install Eclipse

This is a simple Spring Boot application that encodes and decodes URLs using hex strings. It stores data in memory and supports a configurable concurrency limit.

API Endpoints:

POSTS
/encode
Request body:
{ "url": "https://example.com" }
Response:
{ "url": "http://short.est/1" }

/decode
Request body:
{ "url": "http://short.est/1" }
Response:
{ "url": "https://example.com" }

To run the app:
mvn spring-boot:run


curl -X POST http://localhost:8080/encode -H "Content-Type: application/json" -d "{\"url\":\"https://example.com\"}"
curl -X POST http://localhost:8080/decode -H "Content-Type: application/json" -d "{\"url\":\"http://short.est/1\"}"


To run tests:
mvn test

Reference links:

Spring Boot guide: 
https://spring.io/guides/gs/spring-boot/
@RestController 
https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RestController.html
@PostMapping
https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/PostMapping.html
@RequestBody
https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestBody.html
ResponseEntity
https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html
Integer.toHexString
https://docs.oracle.com/javase/8/docs/api/java/lang/Integer.html#toHexString-int-
Semaphore (Java concurrency)
 https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Semaphore.html
Spring Boot testing (MockMvc)
https://spring.io/guides/gs/testing-web/
JUnit 5 guide
https://junit.org/junit5/docs/current/user-guide/








