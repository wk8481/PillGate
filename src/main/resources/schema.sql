-- WeightSensor Table
-- one to many relationship with customer
-- WeightSensor Table



-- DROP TABLE IF EXISTS Customer;
CREATE TABLE IF NOT EXISTS Customer (
                                        customer_id SERIAL PRIMARY KEY NOT NULL,
                                        customer_name VARCHAR(255),
                                        birthDate DATE,
                                        email VARCHAR(255),
                                        hasCareGiver BOOLEAN,
                                        password VARCHAR(255) -- Adjust the size of the VARCHAR to match your password hashing format
);


-- ALTER TABLE IF EXISTS Customer
--     ADD CONSTRAINT unique_email UNIQUE (email);

SELECT constraint_name, column_name
FROM information_schema.constraint_column_usage
WHERE table_name = 'customer';



-- DROP TABLE IF EXISTS WeightSensor;
CREATE TABLE IF NOT EXISTS WeightSensor (
                                            sensor_ID SERIAL PRIMARY KEY,
                                            customer_id INT,
                                            WEIGHT_CAPACITY_GRAMS INT DEFAULT 1000,
                                            calibrationDate DATE, -- change this to timestamp
                                            weight DOUBLE PRECISION, -- Change here
                                            FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);



-- CareGiver Table
-- DROP TABLE IF EXISTS CareGiver;
CREATE TABLE IF NOT EXISTS CareGiver (
                                         caregiver_id SERIAL PRIMARY KEY,
                                         caregiver_name VARCHAR(255),
                                         email VARCHAR(255)
);



-- Dashboard Table
--  ,
-- DROP TABLE IF EXISTS Dashboard;
CREATE TABLE IF NOT EXISTS Dashboard (
                                         dashboard_id SERIAL PRIMARY KEY,
                                         nrPillTaken INT,
                                         duration INT,
                                         customer_id INT UNIQUE,
                                         FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);



-- MedicationSchedule Table
-- DROP TABLE IF EXISTS MedicationSchedule;
CREATE TABLE IF NOT EXISTS MedicationSchedule (
                                                  medSchedule_id SERIAL PRIMARY KEY,
                                                  customer_id INT,
                                                  FOREIGN KEY (customer_id) REFERENCES Customer(customer_id),
                                                  pillName VARCHAR(255),
                                                  repeatIn INT,
                                                  timeTakePill TIMESTAMP,
                                                  nrOfPillsPlaced INT,
                                                  weightOfSinglePill DOUBLE PRECISION, -- Change here
                                                  nrOfPillsTaken INT,
                                                  isStopped BOOLEAN,
                                                  message VARCHAR(255)
);



-- Customer-Caregiver Table
-- Associative table for many-to-many relationship between Customer and CareGiver
-- DROP TABLE IF EXISTS CustomerCareGiver;
CREATE TABLE IF NOT EXISTS CustomerCareGiver (
                                                 customer_id INT,
                                                 caregiver_id INT,
                                                 PRIMARY KEY (customer_id, caregiver_id),
                                                 FOREIGN KEY (customer_id) REFERENCES Customer(customer_id),
                                                 FOREIGN KEY (caregiver_id) REFERENCES CareGiver(caregiver_id)
);


select * from CUSTOMER;
select * from MEDICATIONSCHEDULE;

