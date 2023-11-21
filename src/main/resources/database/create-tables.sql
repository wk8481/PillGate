-- Medicine Table
CREATE TABLE IF NOT EXISTS Medicine (
                                        medicine_id INT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(255),
                                        weight DOUBLE
);

-- WeightSensor Table
CREATE TABLE IF NOT EXISTS WeightSensor (
                                            sensor_ID INT AUTO_INCREMENT PRIMARY KEY,
                                            WEIGHT_CAPACITY_GRAMS INT DEFAULT 1000,
                                            calibrationDate DATE,
                                            weight DOUBLE
);

-- CareGiver Table
CREATE TABLE IF NOT EXISTS CareGiver (
                                         caregiver_id INT AUTO_INCREMENT PRIMARY KEY,
                                         caregiver_name VARCHAR(255)
);

-- Customer Table
CREATE TABLE IF NOT EXISTS Customer (
                                        customer_id INT AUTO_INCREMENT PRIMARY KEY,
                                        customer_name VARCHAR(255),
                                        age INT,
                                        email VARCHAR(255),
                                        hasCareGiver BOOLEAN
);

-- Dashboard Table
CREATE TABLE IF NOT EXISTS Dashboard (
                                         dashboard_id INT AUTO_INCREMENT PRIMARY KEY,
                                         nrPillTaken INT,
                                         duration INT
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
                                                  timeTakePill DATE
);

select  * from Medicine;
