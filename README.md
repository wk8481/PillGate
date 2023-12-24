# Team PillGate
## Team-members:
Emran Hamdard 
William Kasasa
Manami Hayashi
Alperen Doganci
Narjiss Haijj

## Overview of the Project
This is a java project that interacts with Arduino.

Our MVP is to remind people to take their pills on time and detect whether they have taken their pill or not

## Installation
1. Clone the repository
2. Then write the commands below
```
cd existing_repo
git clone https://gitlab.com/kdg-ti/integration-2.1/23-24/group-1/misplaced.git
```
3. Connect your Arduino to your computer to get COM5 serial port
4. Then run your Arduino code in the Arduino IDE 
5. Then close your Arduino IDE
6. Open Intellij or preferred Java IDE
7. Build the Gradle Config File
8. Configure a working Postgres Database on PGAdmin4
### 9. Setup your P2DConfig File for your username, password, url of database VERY IMPORTANT
10. Application Properties should be setup and configured with the same credentials as your P2DConfig file for your database connection
11. Uncomment the dummy data to use for login and testing purposes 
12. Execute the application 
13. Type localhost/8080/home into your preferred browser
14. You will see our website
15. Go to login or register a new user or use our pre-existing user which is explained in the next step
16. Use the existing customer John Doe to login when prompted, the email is john.doe@example.com and password is pass123
### 17. Then you can setup your own medication schedules(information) BUT set timeTakePill to one minute or 2 minutes later 
18. You will receiver a reminder to take your pills
19. You can head to the dashboard page to see the graphs of your medication schedule 
and the graphs will show you when you took a pill over week and during one day




## Configuration instructions
Have Gradle Configuration File and add the linked dependencies



## Dependencies: what libraries (other than the standard) did you use and why

Ommited the dependencies for spring boot, testing, thymeleaf, bootstrap, web, validation and other spring framework dependencies
 as they are the standard dependencies

Below are is our list of non-standard dependencies that we use for our project:

1. jdbc dependency - to use the jdbctemplate to configure our database and link the java code to the postgresql database
2. H2 datasource - this is our memory database and we used it for development purposes hence why it has a H2DBConfig 
3. jSerialComm dependency - to be able to the serial communication between our COM5 port and the java code
4. postgresql datasource - this is our database that persists data(production version/prod)
5. jfreechart - this dependency creates our graphs

    ```
    implementation ("org.springframework.boot:spring-boot-starter-jdbc")
    runtimeOnly ("com.h2database:h2")
    implementation("com.fazecast:jSerialComm:2.10.4") //this for the serial  
    runtimeOnly("org.postgresql:postgresql")
    implementation ("org.jfree:jfreechart:1.5.3")
    ```
## Documentation of the interface with other systems
Should have an Arduino Device and a HX711 Load Sensor with a Test Bed and a Pill Box
Should configure the Arduino IDE 
For the details about the Arduino installation, check the Arduino README
