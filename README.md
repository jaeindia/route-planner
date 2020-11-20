
## Route-Planner
Routing service application, to help users find routes from any station to any other station on the stations and lines of Singapore’s urban rail system.

## Running the Application
**Generate executable jar**
 - Clone the project. 
```
git clone https://github.com/jaeindia/route-planner.git
```
- Go to the main directory. Build project.
```
cd route-planner/
mvn clean package spring-boot:repackage
```
- Run the application.
```
java -jar target/routeplanner-0.0.1-SNAPSHOT.jar
```

**Download executable jar**

[jar](executable/routeplanner-0.0.1-SNAPSHOT.jar)
## APIs
**OpenAPI descriptions**

[http://localhost:8080/routeplanner-docs](http://localhost:8080/routeplanner-docs)

**Swagger UI**

[http://localhost:8080/routeplanner-swagger-ui.html](http://localhost:8080/routeplanner-swagger-ui.html)


- ### Shortest Route
Find the shortest route (Only 1) between source station and destination station.

**`Request URL`** http://localhost:8080/api/routes

**`Request Body`**

    {
      "source": "clementi",
      "destination": "one-north"
    }

**`Response Body`**
```
{
  "source": "Clementi",
  "destination": "one-north",
  "stations": 4,
  "route": [
    "EW23",
    "EW22",
    "EW21",
    "CC22",
    "CC23"
  ],
  "steps": [
    "Take EW line from Clementi to Dover",
    "Take EW line from Dover to Buona Vista",
    "Change from EW line to CC line",
    "Take CC line from Buona Vista to one-north"
  ]
}
```
- ### Shortest Route with travel time (Bonus)
Find atmost 3 (K <= 3) shortest routes between source station and destination station and calculate travel time. Top 3 paths are sorted based on the travel time.

**`Request URL`** http://localhost:8080/api/routes/travel-time

**`Request Body`**

    {
      "source": "Boon Lay",
      "destination": "Little India",
      "date": "2020-01-01T16:00"
    }

    
**`Response Body`**

```
[
  {
    "source": "Boon Lay",
    "destination": "Little India",
    "stations": 13,
    "route": [
      "EW27",
      "EW26",
      "EW25",
      "EW24",
      "EW23",
      "EW22",
      "EW21",
      "CC22",
      "CC21",
      "CC20",
      "CC19",
      "DT9",
      "DT10",
      "DT11",
      "DT12"
    ],
    "steps": [
      "Take EW line from Boon Lay to Lakeside",
      "Take EW line from Lakeside to Chinese Garden",
      "Take EW line from Chinese Garden to Jurong East",
      "Take EW line from Jurong East to Clementi",
      "Take EW line from Clementi to Dover",
      "Take EW line from Dover to Buona Vista",
      "Change from EW line to CC line",
      "Take CC line from Buona Vista to Holland Village",
      "Take CC line from Holland Village to Farrer Road",
      "Take CC line from Farrer Road to Botanic Gardens",
      "Change from CC line to DT line",
      "Take DT line from Botanic Gardens to Stevens",
      "Take DT line from Stevens to Newton",
      "Take DT line from Newton to Little India"
    ],
    "start": "2020-01-01T16:00",
    "end": "2020-01-01T18:16",
    "Time": 136
  },
  {
    "source": "Boon Lay",
    "destination": "Little India",
    "stations": 16,
    "route": [
      "EW27",
      "EW26",
      "EW25",
      "EW24",
      "EW23",
      "EW22",
      "EW21",
      "EW20",
      "EW19",
      "EW18",
      "EW17",
      "EW16",
      "NE3",
      "NE4",
      "NE5",
      "NE6",
      "NE7"
    ],
    "steps": [
      "Take EW line from Boon Lay to Lakeside",
      "Take EW line from Lakeside to Chinese Garden",
      "Take EW line from Chinese Garden to Jurong East",
      "Take EW line from Jurong East to Clementi",
      "Take EW line from Clementi to Dover",
      "Take EW line from Dover to Buona Vista",
      "Take EW line from Buona Vista to Commonwealth",
      "Take EW line from Commonwealth to Queenstown",
      "Take EW line from Queenstown to Redhill",
      "Take EW line from Redhill to Tiong Bahru",
      "Take EW line from Tiong Bahru to Outram Park",
      "Change from EW line to NE line",
      "Take NE line from Outram Park to Chinatown",
      "Take NE line from Chinatown to Clarke Quay",
      "Take NE line from Clarke Quay to Dhoby Ghaut",
      "Take NE line from Dhoby Ghaut to Little India"
    ],
    "start": "2020-01-01T16:00",
    "end": "2020-01-01T18:48",
    "Time": 168
  },
  {
    "source": "Boon Lay",
    "destination": "Little India",
    "stations": 16,
    "route": [
      "EW27",
      "EW26",
      "EW25",
      "EW24",
      "EW23",
      "EW22",
      "EW21",
      "CC22",
      "CC21",
      "CC20",
      "CC19",
      "DT9",
      "DT10",
      "DT11",
      "NS21",
      "NS22",
      "NS23",
      "NS24",
      "NE6",
      "NE7"
    ],
    "steps": [
      "Take EW line from Boon Lay to Lakeside",
      "Take EW line from Lakeside to Chinese Garden",
      "Take EW line from Chinese Garden to Jurong East",
      "Take EW line from Jurong East to Clementi",
      "Take EW line from Clementi to Dover",
      "Take EW line from Dover to Buona Vista",
      "Change from EW line to CC line",
      "Take CC line from Buona Vista to Holland Village",
      "Take CC line from Holland Village to Farrer Road",
      "Take CC line from Farrer Road to Botanic Gardens",
      "Change from CC line to DT line",
      "Take DT line from Botanic Gardens to Stevens",
      "Take DT line from Stevens to Newton",
      "Change from DT line to NS line",
      "Take NS line from Newton to Orchard",
      "Take NS line from Orchard to Somerset",
      "Take NS line from Somerset to Dhoby Ghaut",
      "Change from NS line to NE line",
      "Take NE line from Dhoby Ghaut to Little India"
    ],
    "start": "2020-01-01T16:00",
    "end": "2020-01-01T19:24",
    "Time": 204
  }
]
```
## Project Structure        .
```
├── main
│   ├── java
│   │   └── com
│   │       └── zendesk
│   │           └── company
│   │               └── routeplanner
│   │                   ├── controller
│   │                   │   └── RouteHandler.java
│   │                   ├── entity
│   │                   │   ├── graph
│   │                   │   │   ├── Edge.java
│   │                   │   │   ├── Line.java
│   │                   │   │   └── Node.java
│   │                   │   ├── request
│   │                   │   │   ├── RouteRequestVo.java
│   │                   │   │   └── RouteRequestWithTimeVo.java
│   │                   │   └── response
│   │                   │       ├── CustomErrorResponse.java
│   │                   │       └── RouteResponseVo.java
│   │                   ├── exception
│   │                   │   ├── AbstractCustomException.java
│   │                   │   ├── BadRequestException.java
│   │                   │   ├── handler
│   │                   │   │   └── CustomGlobalExceptionHandler.java
│   │                   │   ├── NoValidPathsExistException.java
│   │                   │   ├── ResourceNotFoundException.java
│   │                   │   └── ServerErrorException.java
│   │                   ├── graphoperations
│   │                   │   ├── GraphHolder.java
│   │                   │   ├── GraphLoader.java
│   │                   │   ├── ShortestPath.java
│   │                   │   └── time
│   │                   │       ├── NightHours.java
│   │                   │       ├── NonPeakHours.java
│   │                   │       ├── PeakHours.java
│   │                   │       ├── TimeOfDay.java
│   │                   │       └── TravelTimeCalcContext.java
│   │                   ├── RouteplannerApplication.java
│   │                   ├── service
│   │                   │   ├── GraphService.java
│   │                   │   └── RoutesService.java
│   │                   └── util
│   │                       ├── Consts.java
│   │                       └── TimeUtil.java
│   └── resources
│       ├── application.properties
│       ├── data
│       │   └── StationMap.csv
│       ├── static
│       └── templates
└── test
    └── java
        └── com
            └── zendesk
                └── company
                    └── routeplanner
                        ├── AbstractTestIT.java
                        ├── RouteRequestsWithoutTimeIT.java
                        └── RouteRequestsWithTimeIT.java
```

## Tests Overall Coverage Summary

    Package,    Class %,    Method %,    Line % 
    all classes, 90.9% (30/ 33),82.4% (122/ 148),87.4% (450/ 515)
##  Dependency list

    org.springframework.boot:spring-boot-starter-data-rest:jar:2.4.0:compile
    org.springframework.data:spring-data-rest-webmvc:jar:3.4.1:compile
    org.springframework.data:spring-data-rest-core:jar:3.4.1:compile
    org.springframework:spring-tx:jar:5.3.1:compile
    org.springframework.hateoas:spring-hateoas:jar:1.2.1:compile
    org.springframework.data:spring-data-commons:jar:2.4.1:compile
    org.atteo:evo-inflector:jar:1.2.2:compile
    com.fasterxml.jackson.core:jackson-databind:jar:2.11.3:compile
    org.slf4j:slf4j-api:jar:1.7.30:compile
    org.springframework.boot:spring-boot-starter-web:jar:2.4.0:compile
    org.springframework.boot:spring-boot-starter:jar:2.4.0:compile
    org.springframework.boot:spring-boot-starter-logging:jar:2.4.0:compile
    ch.qos.logback:logback-classic:jar:1.2.3:compile
    ch.qos.logback:logback-core:jar:1.2.3:compile
    org.apache.logging.log4j:log4j-to-slf4j:jar:2.13.3:compile
    org.apache.logging.log4j:log4j-api:jar:2.13.3:compile
    org.slf4j:jul-to-slf4j:jar:1.7.30:compile
    jakarta.annotation:jakarta.annotation-api:jar:1.3.5:compile
    org.yaml:snakeyaml:jar:1.27:compile
    org.springframework.boot:spring-boot-starter-json:jar:2.4.0:compile
    com.fasterxml.jackson.datatype:jackson-datatype-jdk8:jar:2.11.3:compile
    com.fasterxml.jackson.datatype:jackson-datatype-jsr310:jar:2.11.3:compile
    com.fasterxml.jackson.module:jackson-module-parameter-names:jar:2.11.3:compile
    org.springframework.boot:spring-boot-starter-tomcat:jar:2.4.0:compile
    org.apache.tomcat.embed:tomcat-embed-core:jar:9.0.39:compile
    org.glassfish:jakarta.el:jar:3.0.3:compile
    org.apache.tomcat.embed:tomcat-embed-websocket:jar:9.0.39:compile
    org.springframework:spring-web:jar:5.3.1:compile
    org.springframework:spring-beans:jar:5.3.1:compile
    org.springframework:spring-webmvc:jar:5.3.1:compile
    org.springframework:spring-aop:jar:5.3.1:compile
    org.springframework:spring-context:jar:5.3.1:compile
    org.springframework:spring-expression:jar:5.3.1:compile
    org.springframework.boot:spring-boot-starter-test:jar:2.4.0:test
    org.springframework.boot:spring-boot-test:jar:2.4.0:test
    org.springframework.boot:spring-boot-test-autoconfigure:jar:2.4.0:test
    com.jayway.jsonpath:json-path:jar:2.4.0:compile
    net.minidev:json-smart:jar:2.3:compile
    net.minidev:accessors-smart:jar:1.2:compile
    org.ow2.asm:asm:jar:5.0.4:compile
    jakarta.xml.bind:jakarta.xml.bind-api:jar:2.3.3:compile
    jakarta.activation:jakarta.activation-api:jar:1.2.2:compile
    org.assertj:assertj-core:jar:3.18.1:test
    org.hamcrest:hamcrest:jar:2.2:test
    org.junit.jupiter:junit-jupiter:jar:5.7.0:test
    org.junit.jupiter:junit-jupiter-api:jar:5.7.0:test
    org.apiguardian:apiguardian-api:jar:1.1.0:test
    org.opentest4j:opentest4j:jar:1.2.0:test
    org.junit.platform:junit-platform-commons:jar:1.7.0:test
    org.junit.jupiter:junit-jupiter-params:jar:5.7.0:test
    org.junit.jupiter:junit-jupiter-engine:jar:5.7.0:test
    org.junit.platform:junit-platform-engine:jar:1.7.0:test
    org.mockito:mockito-core:jar:3.6.0:test
    net.bytebuddy:byte-buddy:jar:1.10.18:runtime
    net.bytebuddy:byte-buddy-agent:jar:1.10.18:test
    org.objenesis:objenesis:jar:3.1:test
    org.mockito:mockito-junit-jupiter:jar:3.6.0:test
    org.skyscreamer:jsonassert:jar:1.5.0:test
    com.vaadin.external.google:android-json:jar:0.0.20131108.vaadin1:test
    org.springframework:spring-core:jar:5.3.1:compile
    org.springframework:spring-jcl:jar:5.3.1:compile
    org.springframework:spring-test:jar:5.3.1:test
    org.xmlunit:xmlunit-core:jar:2.7.0:test
    org.springframework.boot:spring-boot-devtools:jar:2.4.0:compile
    org.springframework.boot:spring-boot:jar:2.4.0:compile
    org.springframework.boot:spring-boot-autoconfigure:jar:2.4.0:compile
    org.jgrapht:jgrapht-ext:jar:1.4.0:compile
    org.jgrapht:jgrapht-core:jar:1.4.0:compile
    org.jheaps:jheaps:jar:0.11:compile
    com.github.vlsi.mxgraph:jgraphx:jar:3.9.8.1:compile
    org.apache.commons:commons-csv:jar:1.8:compile
    org.apache.httpcomponents:httpclient:jar:4.5:compile
    org.apache.httpcomponents:httpcore:jar:4.4.13:compile
    commons-codec:commons-codec:jar:1.15:compile
    com.fasterxml.jackson.core:jackson-annotations:jar:2.11.3:compile
    org.apache.commons:commons-collections4:jar:4.4:compile
    org.apache.commons:commons-text:jar:1.9:compile
    org.apache.commons:commons-lang3:jar:3.11:compile
    org.hibernate.validator:hibernate-validator:jar:6.1.6.Final:compile
    jakarta.validation:jakarta.validation-api:jar:2.0.2:compile
    org.jboss.logging:jboss-logging:jar:3.4.1.Final:compile
    com.fasterxml:classmate:jar:1.5.1:compile
    org.springdoc:springdoc-openapi-ui:jar:1.5.0:compile
    org.springdoc:springdoc-openapi-webmvc-core:jar:1.5.0:compile
    org.springdoc:springdoc-openapi-common:jar:1.5.0:compile
    io.swagger.core.v3:swagger-models:jar:2.1.5:compile
    io.swagger.core.v3:swagger-annotations:jar:2.1.5:compile
    io.swagger.core.v3:swagger-integration:jar:2.1.5:compile
    io.swagger.core.v3:swagger-core:jar:2.1.5:compile
    com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:jar:2.11.3:compile
    io.github.classgraph:classgraph:jar:4.8.69:compile
    org.webjars:swagger-ui:jar:3.36.1:compile
    org.webjars:webjars-locator-core:jar:0.46:compile
    com.fasterxml.jackson.core:jackson-core:jar:2.11.3:compile
    io.springfox:springfox-swagger-ui:jar:3.0.0:compile
    io.springfox:springfox-spring-webmvc:jar:3.0.0:runtime
    io.springfox:springfox-spi:jar:3.0.0:runtime
    io.springfox:springfox-schema:jar:3.0.0:runtime
    io.springfox:springfox-core:jar:3.0.0:runtime
    io.springfox:springfox-spring-web:jar:3.0.0:runtime
    org.springframework.plugin:spring-plugin-core:jar:2.0.0.RELEASE:compile
    org.springframework.plugin:spring-plugin-metadata:jar:2.0.0.RELEASE:runtime
    junit:junit:jar:4.13.1:test
    org.hamcrest:hamcrest-core:jar:2.2:test
    org.projectlombok:lombok:jar:1.18.16:provided
