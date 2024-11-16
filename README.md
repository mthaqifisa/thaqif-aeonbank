# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Swagger Document - http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### Guides 
#### [1] Setup MySQL in local docker
1. Create .yml file with the following content (sample name: MySQL.yml):
```yml
version: '3.8'

services:
  mysql:
    image: mysql:latest
    container_name: mysql_container
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: aeon
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

volumes:
  mysql_data:
```

2. Open terminal in the same folder of previous .yml file and run the following command:
```shell
docker-compose -f MySQL.yml up -d
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