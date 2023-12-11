//package be.kdg.programming3.pillgate.config.loadData;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import jakarta.annotation.PostConstruct;
//
//@Configuration
//public class PostgresDataAndSchemaConfig {
//
//
//    @Autowired
//    private final JdbcTemplate jdbcTemplate;
//
//
//    public PostgresDataAndSchemaConfig(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @PostConstruct
//    public void setupDatabase() {
//        createSchema();
//        insertData();
//    }
//
//    private void createSchema() {
//        jdbcTemplate.update("""
//                -- WeightSensor Table
//                DROP TABLE IF EXISTS Customer;
//                CREATE TABLE IF NOT EXISTS Customer (
//                    customer_id INTEGER SERIAL PRIMARY KEY NOT NULL,
//                    customer_name VARCHAR(255),
//                    birthDate DATE,
//                    email VARCHAR(255),
//                    hasCareGiver BOOLEAN,
//                    password VARCHAR(255)
//                );
//
//                DROP TABLE IF EXISTS WeightSensor;
//                CREATE TABLE IF NOT EXISTS WeightSensor (
//                    sensor_ID INTEGER SERIAL PRIMARY KEY,
//                    customer_id INT,
//                    WEIGHT_CAPACITY_GRAMS INT DEFAULT 1000,
//                    calibrationDate DATE,
//                    weight DOUBLE PRECISION,
//                    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
//                );
//
//                -- CareGiver Table
//                DROP TABLE IF EXISTS CareGiver;
//                CREATE TABLE IF NOT EXISTS CareGiver (
//                    caregiver_id INTEGER SERIAL PRIMARY KEY,
//                    caregiver_name VARCHAR(255),
//                    email VARCHAR(255)
//                );
//
//                -- Dashboard Table
//                CREATE TABLE IF NOT EXISTS Dashboard (
//                    dashboard_id INTEGER SERIAL PRIMARY KEY,
//                    nrPillTaken INT,
//                    duration INT,
//                    customer_id INT UNIQUE,
//                    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
//                );
//
//                -- MedicationSchedule Table
//                DROP TABLE IF EXISTS MEDICATIONSCHEDULE;
//                CREATE TABLE IF NOT EXISTS MedicationSchedule (
//                    medSchedule_id INTEGER SERIAL PRIMARY KEY,
//                    customer_id INT,
//                    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id),
//                    startDate DATE,
//                    endDate DATE,
//                    pillName VARCHAR(255),
//                    quantity INT,
//                    timeTakePill DATE,
//                    repeatIn INT,
//                    nrOfPillsPlaced INT,
//                    weightOfSinglePill DOUBLE PRECISION,
//                    nrOfPillsTaken INT,
//                    isStopped BOOLEAN,
//                    message VARCHAR(255)
//                );
//
//                -- Customer-Caregiver Table
//                DROP TABLE IF EXISTS CustomerCareGiver;
//                CREATE TABLE IF NOT EXISTS CustomerCareGiver (
//                    customer_id INT,
//                    caregiver_id INT,
//                    PRIMARY KEY (customer_id, caregiver_id),
//                    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id),
//                    FOREIGN KEY (caregiver_id) REFERENCES CareGiver(caregiver_id)
//                );
//        """);
//    }
//
//    private void insertData() {
//        jdbcTemplate.update("""
//                -- Insert data into Customer table
//                INSERT INTO Customer (customer_name, birthDate, email, hasCareGiver, password) VALUES
//                    ('John Doe', '1980-01-01', 'john.doe@example.com', true, 'pass123'),
//                    ('Jane Smith', '1990-02-02', 'jane.smith@example.com', false, 'pass124'),
//                    ('Alice Johnson', '1985-03-03', 'alice.johnson@example.com', true, 'pass125'),
//                    ('Bob White', '1995-04-04', 'bob.white@example.com', false, 'pass126'),
//                    ('Emma Turner', '1982-05-05', 'emma.turner@example.com', true, 'pass127');
//
//                -- Insert data into WeightSensor table
//                INSERT INTO WeightSensor (WEIGHT_CAPACITY_GRAMS, calibrationDate, weight) VALUES (1000, '2022-01-01', 750.0);
//
//                -- Insert data into CareGiver table
//                INSERT INTO CareGiver (caregiver_name, email) VALUES
//                    ('Charlie Brown', 'charlie.brown@example.com'),
//                    ('Lucy Van Pelt', 'lucy.vanpelt@example.com'),
//                    ('Linus Van Pelt', 'linus.vanpelt@example.com');
//
//                -- Insert data into Dashboard table
//                INSERT INTO Dashboard (nrPillTaken, duration, customer_id) VALUES
//                    (5, 30, 1),
//                    (4, 20, 2),
//                    (6, 25, 3);
//
//                -- Insert data into CustomerCareGiver table
//                INSERT INTO CustomerCareGiver (customer_id, caregiver_id) VALUES
//                    (1, 1),
//                    (2, 2),
//                    (3, 3);
//        """);
//    }
//}
