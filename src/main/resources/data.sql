



INSERT INTO Customer (customer_name, birthDate, email, hasCareGiver, password) VALUES

    ('John Doe', '1980-01-01', 'john.doe@example.com', true, 'pass123'),
    ('Jane Smith', '1990-02-02', 'jane.smith@example.com', false, 'pass124'),
    ('Alice Johnson', '1985-03-03', 'alice.johnson@example.com', true, 'pass125'),
    ('Bob White', '1995-04-04', 'bob.white@example.com', false, 'pass126'),
    ('Emma Turner', '1982-05-05', 'emma.turner@example.com', true, 'pass127')
    ;



INSERT INTO WeightSensor (WEIGHT_CAPACITY_GRAMS, calibrationDate, weight) VALUES (1000, '2022-01-01', 750);


select * from WEIGHTSENSOR;
INSERT INTO CareGiver (caregiver_name, email) VALUES
                                                  ('Charlie Brown', 'charlie.brown@example.com'),
                                                  ('Lucy Van Pelt', 'lucy.vanpelt@example.com'),
                                                  ('Linus Van Pelt', 'linus.vanpelt@example.com')
    ; -- Add more entries as needed



INSERT INTO Dashboard (nrPillTaken, duration, customer_id) VALUES
                                                               (5, 30, 1),
                                                               (4, 20, 2),
                                                               (6, 25, 3)
    ; -- Add more entries as needed




INSERT INTO CustomerCareGiver (customer_id, caregiver_id) VALUES
                                                              (1, 1),
                                                              (2, 2),
                                                              (3, 3)
    ; -- Add more entries as needed

