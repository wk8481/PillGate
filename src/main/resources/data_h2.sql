--
--
--
--
-- INSERT INTO Customer (customer_name, birthDate, email, hasCareGiver, password) VALUES
--
--     ('John Doe', '1980-01-01', 'john.doe@example.com', true, 'pass123'),
--     ('Jane Smith', '1990-02-02', 'jane.smith@example.com', false, 'pass124'),
--     ('Alice Johnson', '1985-03-03', 'alice.johnson@example.com', true, 'pass125'),
--     ('Bob White', '1995-04-04', 'bob.white@example.com', false, 'pass126'),
--     ('Emma Turner', '1982-05-05', 'emma.turner@example.com', true, 'pass127')
--     ;
--
--
--


INSERT INTO WeightSensor (WEIGHT_CAPACITY_GRAMS, calibrationDate, weight) VALUES (1000, '2022-01-01', 750);
INSERT INTO WeightSensor (WEIGHT_CAPACITY_GRAMS, calibrationDate, weight) VALUES (1200, '2022-01-02', 800);
INSERT INTO WeightSensor (WEIGHT_CAPACITY_GRAMS, calibrationDate, weight) VALUES (1100, '2022-02-01', 650);
INSERT INTO WeightSensor (WEIGHT_CAPACITY_GRAMS, calibrationDate, weight) VALUES (1500, '2022-02-15', 900);
INSERT INTO WeightSensor (WEIGHT_CAPACITY_GRAMS, calibrationDate, weight) VALUES (1300, '2022-03-01', 700);
INSERT INTO WeightSensor (WEIGHT_CAPACITY_GRAMS, calibrationDate, weight) VALUES (1000, '2022-03-20', 750);
INSERT INTO WeightSensor (WEIGHT_CAPACITY_GRAMS, calibrationDate, weight) VALUES (900, '2022-04-01', 550);
INSERT INTO WeightSensor (WEIGHT_CAPACITY_GRAMS, calibrationDate, weight) VALUES (1400, '2022-04-10', 850);
INSERT INTO WeightSensor (WEIGHT_CAPACITY_GRAMS, calibrationDate, weight) VALUES (1600, '2022-05-05', 1000);
INSERT INTO WeightSensor (WEIGHT_CAPACITY_GRAMS, calibrationDate, weight) VALUES (1700, '2022-06-01', 1050);


/*INSERT INTO MedicationSchedule (customer_id, pillName, repeatIn, timeTakePill, nrOfPillsPlaced, weightOfSinglePill, nrOfPillsTaken, isStopped, message)
VALUES
    (1, 'Ibuprofen', 8, '2023-12-20 08:00:00', 20, 0.2, 2, FALSE, 'Morning dosage'),
    (1, 'Acetaminophen', 24, '2023-12-19 20:00:00', 20, 0.25, 1, FALSE, 'Evening dosage'),
    (1, 'Lisinopril', 24, '2023-12-21 09:00:00', 30, 0.15, 1, FALSE, 'For blood pressure'),
    (1, 'Metformin', 12, '2023-12-18 08:00:00', 30, 0.5, 1, FALSE, 'For diabetes'),
    (1, 'Atorvastatin', 24, '2023-12-18 21:00:00', 30, 0.3, 1, FALSE, 'For cholesterol'),
    (1, 'Levothyroxine', 24, '2023-12-18 07:00:00', 30, 0.2, 1, FALSE, 'For thyroid'),
    (1, 'Amlodipine', 24, '2023-12-18 08:30:00', 30, 0.35, 0, FALSE, 'For hypertension'),
    (1, 'Omeprazole', 24, '2023-12-18 07:30:00', 30, 0.4, 0, FALSE, 'For acidity');*/


/*INSERT INTO MedicationSchedule (customer_id, pillName, repeatIn, timeTakePill, nrOfPillsPlaced, weightOfSinglePill, nrOfPillsTaken, isStopped, message)
VALUES
    (1, 'Ib', 8, '2023-12-20 10:00:00', 30, 0.2, 2, FALSE, 'Morning dosage');*/
-- Note: Adjust the customer_id and timestamps as per your actual data and requirements.


--
--
-- select * from WEIGHTSENSOR;
-- INSERT INTO CareGiver (caregiver_name, email) VALUES
--                                                   ('Charlie Brown', 'charlie.brown@example.com'),
--                                                   ('Lucy Van Pelt', 'lucy.vanpelt@example.com'),
--                                                   ('Linus Van Pelt', 'linus.vanpelt@example.com')
--     ; -- Add more entries as needed
--
--
--
-- INSERT INTO Dashboard (nrPillTaken, duration, customer_id) VALUES
--                                                                (5, 30, 1),
--                                                                (4, 20, 2),
--                                                                (6, 25, 3)
--     ; -- Add more entries as needed
--
--
--
--
-- INSERT INTO CustomerCareGiver (customer_id, caregiver_id) VALUES
--                                                               (1, 1),
--                                                               (2, 2),
--                                                               (3, 3)
--     ; -- Add more entries as needed
--
