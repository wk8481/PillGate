package be.kdg.programming3.pillgate.service;

import be.kdg.programming3.pillgate.domain.sensor.WeightSensor;
import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;
import be.kdg.programming3.pillgate.repo.medSchedRepo.MedScheduleRepository;
import be.kdg.programming3.pillgate.repo.sensorRepo.SensorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


@Service
//@Qualifier
public class SerialReaderServiceImpl implements SerialReader{
    private BufferedReader input;
    private final SensorRepository sensorRepository;
    private final MedScheduleRepository medScheduleRepository;
    private Logger logger = LoggerFactory.getLogger(SerialReaderServiceImpl.class);

    private boolean stopReadingArduinoData = false;
    private static final double BOX_WEIGHT = 100.0;

    @Autowired
    public SerialReaderServiceImpl(SensorRepository sensorRepository, MedScheduleRepository medScheduleRepository) {
        this.sensorRepository = sensorRepository;
        this.medScheduleRepository=medScheduleRepository;
    }

    public void readArduinoData(String portName) {
        SerialPort[] serialPorts = SerialPort.getCommPorts();
        SerialPort serialPort = null;

        for (SerialPort port : serialPorts) {
            if (port.getSystemPortName().equals(portName)) {
                serialPort = port;
                break;
            }
        }

        if (serialPort == null) {
            logger.info("Port not found: {}", portName);
            throw new RuntimeException("Port not found: " + portName);
        }

        if (serialPort.openPort()) {
            // Sets up the input stream
            logger.info("Port opened successfully: {}", portName);
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));

            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

            // Starts a thread to continuously read data
            new Thread(() -> {
                try {
                    while (!stopReadingArduinoData) {
                        String inputLine = input.readLine();
                        if (inputLine != null && !inputLine.isEmpty()) {
                            processArduinoData(inputLine);
                        }
                    }
                } catch (SerialPortTimeoutException e) {
                    logger.info("Timeout reading Arduino data", e);
                    throw new RuntimeException("Timeout reading Arduino data", e);
                } catch (IOException e) {
                    logger.info("Error reading Arduino data", e);
                    throw new RuntimeException("Error reading Arduino data", e);

                } finally {
                    logger.info("Arduino data reading thread terminated.");
                }
            }).start();
        } else {
            logger.info("Failed to open port: {}", portName);
            throw new RuntimeException("Failed to open port: " + portName);
        }
    }

    private void processArduinoData(String inputLine) {
        inputLine = inputLine.replaceAll("[^\\x20-\\x7E]", "").trim();
        //logger.info("Received Arduino data: {}", inputLine);

        //InputLine is in the format: "Reading: X.XX grams calibration factor: YYY.ZZZ"

        String[] parts = inputLine.split("\\s+|:");
        if (parts.length >= 7 && parts[0].trim().equals("Reading") && parts[1].trim().equals("")) {
            try {
                logger.info("Parsing weight value total weight: {}", parts[2]);
                //logger.info("Parsing calibration factor: {}", parts[6]);
                double weight = Double.parseDouble(parts[2]);
                double calibrationFactor = Double.parseDouble(parts[6]);

                // Retrieve the first WeightSensor created from the repository

                WeightSensor firstSensor = sensorRepository.findAllWSensors().stream().findFirst().orElse(null);
                logger.info("====================================");
                logger.info("First weight sensor: {}", firstSensor);

                // Update the values in the latest WeightSensor
                if (firstSensor != null) {
                    firstSensor.updateValues(weight, calibrationFactor);
                    logger.info("Updating the values in the first WeightSensor: {},{}", weight, calibrationFactor);
                    firstSensor.setCalibrationDate(LocalDateTime.now());
                    sensorRepository.createSensor(firstSensor);

                    // Retrieve the latest MedicationSchedule from the repository
                    List<MedicationSchedule> medSchedules = medScheduleRepository.findAllMedSchedules();
                        MedicationSchedule latestMedSchedule = medSchedules
                                .stream()
                                .max(Comparator.comparing(MedicationSchedule::getTimeTakePill))
                                .orElse(null);

                    logger.info("Latest medication schedule: {}", latestMedSchedule);

                    // Update the MedicationSchedule based on weight sensor data
                    if (latestMedSchedule != null) {
                        // Process the weight data and update relevant fields in MedicationSchedule
                        processWeightData(latestMedSchedule, firstSensor);
                    }
                }
            } catch (NumberFormatException e) {
                logger.error("Error parsing number in data: {}", inputLine);
                System.out.println("Invalid number format in data: " + inputLine);
            }
        } else {
            System.out.println("Invalid data format: " + inputLine);
        }
    }


    /**
     *
     * @param weightSensor
     * @param nrOfPillsPlaced
     * @return
     * here the 0.1 value is a default value, it can't be less than 0 or not == 0, it always need to be greater
     */

    public double calculateWeightOfSinglePill(WeightSensor weightSensor, int nrOfPillsPlaced) {
        double weightOfSinglePill = (weightSensor.getWeight() - BOX_WEIGHT) / nrOfPillsPlaced;
        return Math.max(weightOfSinglePill,0.1);
    }

    public void processWeightData(MedicationSchedule medicationSchedule, WeightSensor weightSensor) {
        // Get the nrOfPillsPlaced value provided by user
        int nrOfPillsPlaced = medicationSchedule.getNrOfPillsPlaced();
        logger.info("Number of pills placed: {}", nrOfPillsPlaced);

        // Calculates the weight of a single pill
        double weightOfSinglePill = calculateWeightOfSinglePill(weightSensor, nrOfPillsPlaced);

        // Updates the MedicationSchedule fields
        medicationSchedule.setWeightOfSinglePill(weightOfSinglePill);
        logger.info("Weight of single pill: {}", weightOfSinglePill);

        // Checks if weight reduction indicates pill taken
        if (weightReductionIndicatesPillTaken(weightSensor, medicationSchedule)) {

            // Increments nrOfPillsTaken
            int nrOfPillsTaken = medicationSchedule.getNrOfPillsTaken() + 1;
            if (nrOfPillsTaken > medicationSchedule.getNrOfPillsPlaced() + 1) {
                logger.info("Number of pills taken is greater than number of pills placed. Skipping increment.");
                return;
            }
            medicationSchedule.setNrOfPillsTaken(nrOfPillsTaken);


            // Decrements nrOfPillsPlaced only if it's greater than 0 (to avoid negative values)
            if (medicationSchedule.getNrOfPillsPlaced() > 0) {
                int nrOfPillsPlacedUpdated = medicationSchedule.getNrOfPillsPlaced() - 1;
                medicationSchedule.setNrOfPillsPlaced(nrOfPillsPlacedUpdated);

                logger.info("Number of pills taken increased: {}", nrOfPillsTaken);
                logger.info("Number of pills placed decreased: {}", nrOfPillsPlacedUpdated);

                if (nrOfPillsPlacedUpdated == 0) {
                    logger.info("No more pills left in the box. Stopping the data retrieval process.");
                        disconnect();
                        logger.info("Disconnected from Arduino");

                }
            }
            // Save or update the MedicationSchedule in the repository or database
            medScheduleRepository.updateMedSchedule(medicationSchedule);
        }
    }

            // Method to check if weight reduction indicates a pill is taken
            private boolean weightReductionIndicatesPillTaken(WeightSensor weightSensor, MedicationSchedule medicationSchedule) {
                // Get the latest weight sensor from the repository
                List<WeightSensor> weightSensors = sensorRepository.findAllWSensors();

                if (weightSensors.size() < 2) {
                    logger.warn("Not enough weight sensor data found.");
                    return false;
                }

                logger.info("Weight sensor data: {}", weightSensors);


                // Compare current total weight with the previous total weight
                double currentTotalWeight = weightSensors.get(0).getWeight();
                double previousTotalWeight = weightSensors.get(1).getWeight();

                // Round the weights to a specific number of decimal places
                int decimalPlaces = 3; // Adjust this based on your requirements
                double roundedPreviousWeight = round(previousTotalWeight, decimalPlaces);
                logger.info("Rounded previous weight: {}", roundedPreviousWeight);
                double roundedCurrentWeight = round(currentTotalWeight, decimalPlaces);
                logger.info("Rounded current weight: {}", roundedCurrentWeight);

                double weightOfSinglePill =  medicationSchedule.getWeightOfSinglePill();

                // If the rounded difference is equal to or greater than the weight of a single pill, consider it a pill is taken
                if (roundedPreviousWeight - roundedCurrentWeight >= weightOfSinglePill) {
                    logger.info("Weight reduction indicates a pill taken.... incrementing nrOfPillsTaken and decrementing nrOfPillsPlaced");
                    logger.info("Weight reduction: {}", roundedPreviousWeight - roundedCurrentWeight);
                    return true;
                }

                return false;
            }

    // Helper method to round a double value to a specific number of decimal places
    private double round(double value, int decimalPlaces) {
        double scale = Math.pow(10, decimalPlaces);
        return Math.round(value * scale) / scale;
    }


    public void disconnect() {
        stopReadingArduinoData = true;
    }

}
