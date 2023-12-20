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


//    PREVIOUS CONSTRUCTOR USING JDBC SENSOR REPOSITORY
//    @Autowired
//    public SerialReaderServiceImpl(@Qualifier("JDBCSensorRepository") SensorRepository sensorRepository, MedScheduleRepository medScheduleRepository) {
//        this.sensorRepository = sensorRepository;
//        this.medScheduleRepository=medScheduleRepository;
//    }



//    public SerialReaderServiceImpl(SensorRepository sensorRepository) {
//        this.sensorRepository = sensorRepository;
//    }

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
        logger.info("Received Arduino data: {}", inputLine);
        // Assuming inputLine is in the format: "Reading: X.XX grams calibration factor: YYY.ZZZ"

        String[] parts = inputLine.split("\\s+|:");
        if (parts.length >= 7 && parts[0].trim().equals("Reading") && parts[1].trim().equals("")) {
            try {
                logger.info("Parsing weight value: {}", parts[2]);
                logger.info("Parsing calibration factor: {}", parts[6]);
                double weight = Double.parseDouble(parts[2]);
                double calibrationFactor = Double.parseDouble(parts[6]);

                // Retrieve the latest WeightSensor from the repository
                WeightSensor latestSensor = sensorRepository.findAllWSensors().stream().findFirst().orElse(null);

                sensorRepository.createSensor(latestSensor);
                logger.info("Latest weight sensor: {}", latestSensor);

                // Update the values in the latest WeightSensor
                if (latestSensor != null) {
                    latestSensor.updateValues(weight, calibrationFactor);

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
                    int nrOfPillsTaken = medicationSchedule.getNrOfPillsTaken() + 1;
                    medicationSchedule.setNrOfPillsTaken(nrOfPillsTaken);
                    logger.info("Number of pills taken increased: {}", nrOfPillsTaken);
                    // You might want to add additional logic or notifications here
                }

                // Save or update the MedicationSchedule in the repository or database
                medScheduleRepository.updateMedSchedule(medicationSchedule);
                logger.info("Updated MedicationSchedule: {}", medicationSchedule);
    }

            // Example method to check if weight reduction indicates a pill taken
    private boolean weightReductionIndicatesPillTaken(WeightSensor weightSensor, MedicationSchedule medicationSchedule) {
                double weightReductionThreshold = medicationSchedule.getWeightOfSinglePill();
                return (weightSensor.getWeight() - BOX_WEIGHT) <= weightReductionThreshold;
    }





/*        private void calculateWeightOfSinglePill(MedicationSchedule latestMedSchedule, WeightSensor latestSensor) {
            if (latestMedSchedule != null) {
                // Add logic to check if the weight is reduced from the total weight of the box and pills
                double weightReduction = latestMedSchedule.getNrOfPillsPlaced() * latestMedSchedule.getWeightOfSinglePill();

                // Check if the actual weight reduction is below the expected
                if (latestSensor.getWeight() < weightReduction) {

                int pillsTaken = (int) ((weightReduction - latestSensor.getWeight()) / latestMedSchedule.getWeightOfSinglePill());

                // Update the number of pills placed in the MedicationSchedule
                latestMedSchedule.setNrOfPillsPlaced(latestMedSchedule.getNrOfPillsPlaced() + pillsTaken);

                // Calculate the weight of a single pill
                if (latestMedSchedule.getNrOfPillsPlaced() > 0) {
                    double totalWeightWithPills = latestSensor.getWeight() + weightReduction;
                    double weightOfSinglePill = weightReduction / latestMedSchedule.getNrOfPillsPlaced();

                    // Set the calculated weightOfSinglePill in the MedicationSchedule
                    latestMedSchedule.setWeightOfSinglePill(weightOfSinglePill);
                    logger.info("Weight of single pill: {}", weightOfSinglePill);

                    // Update the number of pills taken
                    int pillsTakenUpdated = latestMedSchedule.getNrOfPillsPlaced() - (int) (totalWeightWithPills / weightOfSinglePill);
                    latestMedSchedule.setNrOfPillsTaken(pillsTakenUpdated);
                     logger.info("Number of pills taken: {}", pillsTakenUpdated);

                    // Save the updated MedicationSchedule back to the repository
                    medScheduleRepository.updateMedSchedule(latestMedSchedule);
                    logger.info("Updated MedicationSchedule: {}", latestMedSchedule);

                    } else {
                        logger.error("Invalid MedicationSchedule or WeightSensor: latestMedSchedule={}, latestSensor={}", latestMedSchedule, latestSensor);
                    }
                }
            }
        }*/


        // there is no need to save the weight sensor data to the json file
/*        @Override
    public void saveWeightSensorDataToJson(WeightSensor weightSensor) {
        try {
            // Check if the JSON file exists
            File file = new File(jsonFilePath);
            boolean fileExists = file.exists();

            // Append or create a new JSON file
            try (FileWriter fileWriter = new FileWriter(file, true)) {
                if (!fileExists) {
                    // If the file doesn't exist, add the opening bracket for JSON array
                    fileWriter.write("[\n");
                } else {
                    // If the file exists, add a comma to separate objects in the array
                    fileWriter.write(",\n");
                }

                // Convert WeightSensor object to JSON
                String json = objectMapper.writeValueAsString(weightSensor);

                // Write JSON to file
                fileWriter.write(json);

                // Close the array if it's the last object
                if (!fileExists) {
                    fileWriter.write("\n]");
                }
            }

            logger.info("Weight sensor data appended to JSON file: {}", jsonFilePath);

        } catch (IOException e) {
            logger.error("Error appending weight sensor data to JSON", e);
            throw new RuntimeException("Error appending weight sensor data to JSON", e);
        }
    }*/

    public void disconnect() throws IOException {
        if (input != null) {
            input.close();
        }
    }

}