
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

**(Or)**


**Download executables**

[routeplanner-0.0.1-SNAPSHOT.jar](executable/routeplanner-0.0.1-SNAPSHOT.jar)

[application.properties](src/main/resources/application.properties)

Download the above files and run the application.
```
java -jar routeplanner-0.0.1-SNAPSHOT.jar --spring.config.location="file:application.properties" --server.port=8089
```

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
## Project Structure
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
```
commons-collections org.apache.commons:commons-collections4 4.4
commons-csv org.apache.commons:commons-csv 1.8
commons-text org.apache.commons:commons-text 1.9
hibernate-validator org.hibernate:hibernate-validator 6.1.6.Final
httpcomponents-client org.apache.httpcomponents:httpclient 4.5
jackson-annotations com.fasterxml.jackson.core:jackson-annotations 2.11.3
jgrapht org.jgrapht:jgrapht-ext 1.4.0
junit4 junit:junit 4.13.1
lombok org.projectlombok:lombok 1.18.16
spring-boot-2.1.0.RELEASE org.springframework.boot:spring-boot-devtools
spring-boot-concourse org.springframework.boot:spring-boot-maven-plugin
spring-boot-concourse org.springframework.boot:spring-boot-starter-data-rest
spring-boot-concourse org.springframework.boot:spring-boot-starter-test
spring-boot-concourse org.springframework.boot:spring-boot-starter-web
springdoc-openapi org.springdoc:springdoc-openapi-ui 1.5.0
springfox-swagger-ui io.springfox:springfox-swagger-ui 3.0.0
```
