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

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final String jsonFilePath = "weight_sensor_data.json";


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
            // Set up the input stream
            logger.info("Port opened successfully: {}", portName);
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));

            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

            // Start a thread to continuously read data
            new Thread(() -> {
                try {
                    StringBuilder dataBuffer = new StringBuilder();
                    while (true) {
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
        // Assuming inputLine is in the format: "Reading: X.XX grams calibration factor: YYY.ZZZ"

        String[] parts = inputLine.split("\\s+|:");
        if (parts.length >= 7 && parts[0].trim().equals("Reading") && parts[1].trim().equals("")) {
            try {
                logger.info("Parsing weight value total weight: {}", parts[2]);
                //logger.info("Parsing calibration factor: {}", parts[6]);
                double weight = Double.parseDouble(parts[2]);
                double calibrationFactor = Double.parseDouble(parts[6]);

                // Retrieve the latest WeightSensor from the repository
                WeightSensor latestSensor = sensorRepository.findAllWSensors().stream().findFirst().orElse(null);

                latestSensor.setCalibrationDate(LocalDateTime.now());
                //sensorRepository.createSensor(latestSensor);
                sensorRepository.updateSensor(latestSensor);
                //logger.info("Latest weight sensor: {}", latestSensor);

                // Update the values in the latest WeightSensor
                if (latestSensor != null) {
                    latestSensor.updateValues(weight, calibrationFactor);

                    latestSensor.setCalibrationDate(LocalDateTime.now());
                    sensorRepository.updateSensor(latestSensor);

                    // Retrieve the latest MedicationSchedule from the repository
                    MedicationSchedule latestMedSchedule = medScheduleRepository.findAllMedSchedules().stream().findFirst().orElse(null);

                    // Update the MedicationSchedule based on weight sensor data
                    if (latestMedSchedule != null) {

                        // Process the weight data and update relevant fields in MedicationSchedule
                        processWeightData(latestMedSchedule, latestSensor);
                        logger.info("Weight of single pill: {}", latestMedSchedule.getWeightOfSinglePill());
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


        // the new logic I am trying to detect if pill is taken

    public double calculateWeightOfSinglePillUpdatedMethod(WeightSensor weightSensor, int nrOfPillsPlaced) {
                return (weightSensor.getWeight() - BOX_WEIGHT) / nrOfPillsPlaced;
    }

    // Process weight data and update relevant fields in MedicationSchedule
    public void processWeightData(MedicationSchedule medicationSchedule, WeightSensor weightSensor) {
                // Get the user-provided value for nrOfPillsPlaced
                int nrOfPillsPlaced = medicationSchedule.getNrOfPillsPlaced();

                // Calculate the weight of a single pill
                double weightOfSinglePill = calculateWeightOfSinglePillUpdatedMethod(weightSensor, nrOfPillsPlaced);

                // Update the MedicationSchedule fields
                medicationSchedule.setWeightOfSinglePill(weightOfSinglePill);
                logger.info("Weight of single pill: {}", weightOfSinglePill);
                // Perform additional logic if needed, e.g., check if weight reduction indicates pill taken
                // For example, if the weight decreases by the weight of a single pill, increment nrOfPillsTaken
                if (weightReductionIndicatesPillTaken(weightSensor, medicationSchedule)) {
                    int nrOfPillsTaken = medicationSchedule.getNrOfPillsTaken() /*+ 1*/;
                    int nrOfPillsPlacedUpdated = medicationSchedule.getNrOfPillsPlaced() /*- 1*/;
                    medicationSchedule.setNrOfPillsTaken(nrOfPillsTaken);
                    medicationSchedule.setNrOfPillsPlaced(nrOfPillsPlacedUpdated);
                    logger.info("Number of pills taken increased: {}", nrOfPillsTaken);
                    logger.info("Number of pills placed decreased: {}", nrOfPillsPlacedUpdated);

                }

                // Save or update the MedicationSchedule in the repository or database
                medScheduleRepository.updateMedSchedule(medicationSchedule);
                logger.info("Updated MedicationSchedule: {}", medicationSchedule);

    }

            // Method to check if weight reduction indicates a pill taken
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
                logger.info("Previous total weight: {}", previousTotalWeight);


                // Round the weights to a specific number of decimal places
                int decimalPlaces = 3; // Adjust this based on your requirements
                double roundedCurrentWeight = round(currentTotalWeight, decimalPlaces);
                logger.info("Rounded current weight: {}", roundedCurrentWeight);
                double roundedPreviousWeight = round(previousTotalWeight, decimalPlaces);
                logger.info("Rounded previous weight: {}", roundedPreviousWeight);

                int weightOfSinglePill = (int) medicationSchedule.getWeightOfSinglePill();

                // If the rounded difference is equal to the weight of a single pill, consider it a pill taken
                if (roundedPreviousWeight - roundedCurrentWeight >= weightOfSinglePill) {
                    logger.info("Weight reduction indicates a pill taken.... incrementing nrOfPillsTaken and decrementing nrOfPillsPlaced");
                    // Increment nrOfPillsTaken
                    int nrOfPillsTaken = medicationSchedule.getNrOfPillsTaken() + 1;
                    medicationSchedule.setNrOfPillsTaken(nrOfPillsTaken);

                    // Decrement nrOfPillsPlaced
                    int nrOfPillsPlacedUpdated = medicationSchedule.getNrOfPillsPlaced() - 1;
                    medicationSchedule.setNrOfPillsPlaced(nrOfPillsPlacedUpdated);

                    logger.info("Number of pills taken increased: {}", nrOfPillsTaken);
                    logger.info("Number of pills placed decreased: {}", nrOfPillsPlacedUpdated);
                    medScheduleRepository.updateMedSchedule(medicationSchedule);
                    return true;
                }

                return false;
            }

    // Helper method to round a double value to a specific number of decimal places
    private double round(double value, int decimalPlaces) {
        double scale = Math.pow(10, decimalPlaces);
        return Math.round(value * scale) / scale;
    }


    public void disconnect() throws IOException {
        if (input != null) {
            input.close();
        }
    }

}