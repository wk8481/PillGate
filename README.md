# Team PillGate

## Team Members
- Emran Hamdard
- William Kasasa
- Manami Hayashi
- Alperen Doganci
- Narjiss Haijj

## Overview of the Project
PillGate is a Java-based project that interfaces with Arduino devices. Our MVP (Minimum Viable Product) aims to remind people to take their pills on time and detect whether they have taken their pills or not.

## Table of Contents
- [Installation](#installation)
- [Configuration Instructions](#configuration-instructions)
- [Dependencies](#dependencies)
- [Interface Documentation](#documentation-of-the-interface-with-other-systems)
- [Usage and Features](#usage-and-features)
- [Contributing](#contributing)
- [License](#license)


## Installation
1. Clone the repository:
    ```
    git clone https://gitlab.com/kdg-ti/integration-2.1/23-24/group-1/misplaced.git
    cd existing_repo
    ```
2. Connect your Arduino to your computer to get the COM5 serial port.
3. Run your Arduino code in the Arduino IDE, then close the IDE.
4. Open IntelliJ or your preferred Java IDE.
5. Build the Gradle Config File.
6. Configure a working PostgreSQL Database on PGAdmin4.
7. **IMPORTANT**: Set up your P2DConfig File with your username, password, and database URL.
8. Ensure Application Properties are set up and configured with the same credentials as in your P2DConfig file for database connection.
9. Uncomment the dummy data in the code for login and testing purposes.
10. Execute the application.
11. Navigate to `localhost:8080/home` in your browser.
12. Log in or register a new user. You can use the pre-existing user John Doe with email `john.doe@example.com` and password `pass123`.
13. Set up your medication schedules. **Tip**: Set `timeTakePill` to one or two minutes later for testing.
14. You will receive a reminder to take your pills.
15. Check the dashboard page for graphs showing your medication schedule and adherence.

## Configuration Instructions
- Ensure you have the Gradle Configuration File and add the linked dependencies.
- Required dependencies: jdbc, H2 datasource, jSerialComm, PostgreSQL datasource, and jfreechart.
    ```gradle
    implementation "org.springframework.boot:spring-boot-starter-jdbc"
    runtimeOnly "com.h2database:h2"
    implementation "com.fazecast:jSerialComm:2.10.4" // For serial communication
    runtimeOnly "org.postgresql:postgresql"
    implementation "org.jfree:jfreechart:1.5.3"
    ```

## Dependencies
- **jdbc**: For using the JdbcTemplate to configure our database and link Java code to the PostgreSQL database.
- **H2 datasource**: In-memory database used for development purposes.
- **jSerialComm**: For serial communication between the COM5 port and Java code.
- **PostgreSQL datasource**: Database for persisting data (production version).
- **jfreechart**: To create graphs.

## Documentation of Interface with Other Systems
- Arduino Device and HX711 Load Sensor with a Test Bed and Pill Box required.
- Configure the Arduino IDE.
- Details about Arduino installation are in the Arduino README.

## Conclusion
PillGate is an innovative solution combining hardware and software to improve medication adherence. Its simple yet effective approach can make a significant impact on healthcare and individual well-being.

## License
This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).


