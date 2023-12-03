--
-- -- WeightSensor Table
-- -- one to many relationship with customer
-- -- WeightSensor Table
--
-- Drop TABLE IF EXISTS Customer;
-- CREATE TABLE IF NOT EXISTS Customer (
--                                         customer_id INT AUTO_INCREMENT PRIMARY KEY,
--                                         customer_name VARCHAR(255),
--     birthDate DATE,
--     email VARCHAR(255),
--     hasCareGiver BOOLEAN,
--     password VARCHAR(255) -- Adjust the size of the VARCHAR to match your password hashing format
--     );
--
--
--
--
-- Drop table IF EXISTS WeightSensor;
-- CREATE TABLE IF NOT EXISTS WeightSensor (
--                                             sensor_ID INT AUTO_INCREMENT PRIMARY KEY,
--                                             customer_id INT,
--                                             WEIGHT_CAPACITY_GRAMS INT DEFAULT 1000,
--                                             calibrationDate DATE,
--                                             weight DOUBLE,
--                                             FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
-- );
--
--
--
--
-- -- CareGiver Table
-- Drop TABLE IF EXISTS CareGiver;
-- CREATE TABLE IF NOT EXISTS CareGiver (
--                                          caregiver_id INT AUTO_INCREMENT PRIMARY KEY,
--                                          caregiver_name VARCHAR(255),
--     email VARCHAR(255)
--     );
--
-- -- Customer Table
--
--
--
--
-- -- Dashboard Table
-- CREATE TABLE IF NOT EXISTS Dashboard (
--                                          dashboard_id INT AUTO_INCREMENT PRIMARY KEY,
--                                          nrPillTaken INT,
--                                          duration INT,
--                                          customer_id INT UNIQUE,
--                                          FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
--     );
--
-- -- MedicationSchedule Table
-- CREATE TABLE IF NOT EXISTS MedicationSchedule (
--                                                   medSchedule_id INT AUTO_INCREMENT PRIMARY KEY,
--                                                   customer_id INT,
--                                                   FOREIGN KEY (customer_id) REFERENCES Customer(customer_id),
--                                                   startDate DATE,
--                                                   endDate DATE,
--                                                   pillName VARCHAR(255),
--                                                   quantity INT,
--                                                   timeTakePill DATE
-- );
--
--
-- -- Customer-Caregiver Table
--
-- -- Associative table for many-to-many relationship between Customer and CareGiver
-- Drop TABLE IF EXISTS CustomerCareGiver;
-- CREATE TABLE IF NOT EXISTS CustomerCareGiver (
--                                                  customer_id INT,
--                                                  caregiver_id INT,
--                                                  PRIMARY KEY (customer_id, caregiver_id),
--                                                  FOREIGN KEY (customer_id) REFERENCES Customer(customer_id),
--                                                  FOREIGN KEY (caregiver_id) REFERENCES CareGiver(caregiver_id)
-- );
--
-- select * from CUSTOMER;
--
--
--
-- =======
-- WeightSensor Table
-- one to many relationship with customer
-- WeightSensor Table

Drop TABLE IF EXISTS Customer;
CREATE TABLE IF NOT EXISTS Customer (
                                        customer_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
                                        customer_name VARCHAR(255),
    birthDate DATE,
    email VARCHAR(255),
    hasCareGiver BOOLEAN,
    password VARCHAR(255) -- Adjust the size of the VARCHAR to match your password hashing format
    );




Drop table IF EXISTS WeightSensor;
CREATE TABLE IF NOT EXISTS WeightSensor (
                                            sensor_ID INT AUTO_INCREMENT PRIMARY KEY,
                                            customer_id INT,
                                            WEIGHT_CAPACITY_GRAMS INT DEFAULT 1000,
                                            calibrationDate DATE,
                                            weight DOUBLE,
                                            FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);




-- CareGiver Table
Drop TABLE IF EXISTS CareGiver;
CREATE TABLE IF NOT EXISTS CareGiver (
                                         caregiver_id INT AUTO_INCREMENT PRIMARY KEY,
                                         caregiver_name VARCHAR(255),
    email VARCHAR(255)
    );

-- Customer Table




-- Dashboard Table
CREATE TABLE IF NOT EXISTS Dashboard (
                                         dashboard_id INT AUTO_INCREMENT PRIMARY KEY,
                                         nrPillTaken INT,
                                         duration INT,
                                         customer_id INT UNIQUE,
                                         FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
    );

-- MedicationSchedule Table
CREATE TABLE IF NOT EXISTS MedicationSchedule (
                                                  medSchedule_id INT AUTO_INCREMENT PRIMARY KEY,
                                                  customer_id INT,
                                                  FOREIGN KEY (customer_id) REFERENCES Customer(customer_id),
                                                  startDate DATE,
                                                  endDate DATE,
                                                  pillName VARCHAR(255),
                                                  quantity INT,
                                                  timeTakePill DATE,
                                                  isStopped BOOLEAN,
                                                  message VARCHAR(255)

);


-- Customer-Caregiver Table

-- Associative table for many-to-many relationship between Customer and CareGiver
Drop TABLE IF EXISTS CustomerCareGiver;
CREATE TABLE IF NOT EXISTS CustomerCareGiver (
                                                 customer_id INT,
                                                 caregiver_id INT,
                                                 PRIMARY KEY (customer_id, caregiver_id),
                                                 FOREIGN KEY (customer_id) REFERENCES Customer(customer_id),
                                                 FOREIGN KEY (caregiver_id) REFERENCES CareGiver(caregiver_id)
);

select * from CUSTOMER;




