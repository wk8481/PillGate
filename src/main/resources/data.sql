INSERT INTO MedicationSchedule (customer_id, pillName, repeatIn, timeTakePill, nrOfPillsPlaced, weightOfSinglePill, nrOfPillsTaken, isStopped, message)
VALUES
    (1, 'Ibuprofen', 8, '2023-12-20 08:00:00', 20, 0.2, 2, FALSE, 'Morning dosage'),
    (1, 'Acetaminophen', 24, '2023-12-19 20:00:00', 20, 0.25, 1, FALSE, 'Evening dosage'),
    (1, 'Lisinopril', 24, '2023-12-21 09:00:00', 30, 0.15, 1, FALSE, 'For blood pressure'),
    (1, 'Metformin', 12, '2023-12-18 08:00:00', 30, 0.5, 1, FALSE, 'For diabetes'),
    (1, 'Atorvastatin', 24, '2023-12-18 21:00:00', 30, 0.3, 1, FALSE, 'For cholesterol'),
    (1, 'Levothyroxine', 24, '2023-12-18 07:00:00', 30, 0.2, 1, FALSE, 'For thyroid'),
    (1, 'Amlodipine', 24, '2023-12-18 08:30:00', 30, 0.35, 0, FALSE, 'For hypertension'),
    (1, 'Omeprazole', 24, '2023-12-18 07:30:00', 30, 0.4, 0, FALSE, 'For acidity');
