# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Swagger Document - http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### Guides 
#### [1] Deploy app to local docker
Run the following command from app root folder:
```shell
docker-compose up -d
```


#### [2] Run the application locally
Open terminal in the project folder and run the following command:
```shell
mvn spring-boot:run
```

#### [3] Run sample in Postman
1. Export the following postman collection in postman
```text
../thaqif-aeonbank/postman/collection
```
2. Export the following postman environment in postman
```text
../thaqif-aeonbank/postman/environment
```


#### [4] View unit test code coverage (jacoco report)
1. Compile the code
```shell
mvn clean install
```
2. View controller report in
```text
../thaqif-aeonbank/target/site/jacoco/com.thaqif.aeonbank.controller/index.html
```
3. View service report in
```text
../thaqif-aeonbank/target/site/jacoco/com.thaqif.aeonbank.service/index.html
```