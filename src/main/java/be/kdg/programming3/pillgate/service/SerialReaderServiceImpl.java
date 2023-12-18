package be.kdg.programming3.pillgate.service;

import be.kdg.programming3.pillgate.domain.sensor.WeightSensor;
import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;
import be.kdg.programming3.pillgate.repo.medSchedRepo.MedScheduleRepository;
import be.kdg.programming3.pillgate.repo.sensorRepo.SensorRepository;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


@Service
@Qualifier
public class SerialReaderServiceImpl implements SerialReader{
    private BufferedReader input;
    private final SensorRepository sensorRepository;
    private final MedScheduleRepository medScheduleRepository;
    private Logger logger = LoggerFactory.getLogger(SerialReaderServiceImpl.class);


    @Autowired
    public SerialReaderServiceImpl(@Qualifier("JDBCSensorRepository") SensorRepository sensorRepository, MedScheduleRepository medScheduleRepository) {
        this.sensorRepository = sensorRepository;
        this.medScheduleRepository=medScheduleRepository;
    }
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

        logger.info("Attempting to open port: {}", portName);
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
        // Extract the relevant information

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
                // Update the values in the latest WeightSensor
                if (latestSensor != null) {
                    latestSensor.updateValues(weight, calibrationFactor);
                    // Save the updated sensor back to the repository
                    sensorRepository.updateSensor(latestSensor);

                    // Retrieve the latest MedicationSchedule from the repository
                    MedicationSchedule latestMedSchedule = medScheduleRepository.findAllMedSchedules().stream().findFirst().orElse(null);

                    // Update the MedicationSchedule based on weight sensor data
                    if (latestMedSchedule != null) {
                        // Call the method to calculate the weight of a single pill
                        calculateWeightOfSinglePill(latestMedSchedule, latestSensor);
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


        private void calculateWeightOfSinglePill(MedicationSchedule latestMedSchedule, WeightSensor latestSensor) {
            if (latestMedSchedule != null) {
                // Add logic to check if the weight is reduced from the total weight of the box and pills
                double weightReduction = latestMedSchedule.getNrOfPillsPlaced() * latestMedSchedule.getWeightOfSinglePill();
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
        }

    public void disconnect() throws IOException {
        if (input != null) {
            input.close();
        }
    }

}