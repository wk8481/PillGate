package be.kdg.programming3.pillgate.service;

import be.kdg.programming3.pillgate.domain.sensor.WeightSensorData;
import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;
import be.kdg.programming3.pillgate.repo.medSchedRepo.MedScheduleRepository;
import be.kdg.programming3.pillgate.repo.sensorRepo.SensorRepository;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

/**
 * This service class {@code SensorServiceImpl} provides functionality related to handling sensor data,
 * particularly data received from an Arduino device through a serial port.
 * It interacts with repositories to manage weight sensor data and medication schedules.
 * @author PillGate
 * @see SensorService
 * @see SensorRepository
 * @see MedScheduleRepository
 */
@Service
public class SensorServiceImpl implements SensorService {
    private BufferedReader input;
    private final SensorRepository sensorRepository;
    private final MedScheduleRepository medScheduleRepository;
    private Logger logger = LoggerFactory.getLogger(SensorServiceImpl.class);

    private boolean stopReadingArduinoData = false;
    private static final double BOX_WEIGHT = 100.0;

    /**
     * Constructs a new {@code SensorServiceImpl} with the specified repository.
     * @param sensorRepository is for WeightSensorData entity and medScheduleRepository is the repository for MedicationSchedule entity.
     */

    @Autowired
    public SensorServiceImpl(SensorRepository sensorRepository, MedScheduleRepository medScheduleRepository) {
        this.sensorRepository = sensorRepository;
        this.medScheduleRepository=medScheduleRepository;
    }


    /**
     * Retrieves a list of all WeightSensorDatas.
     *
     * @return A list of WeightSensorData entity.
     */
    @Override
    public List<WeightSensorData> findAllWSensors(){
        return sensorRepository.findAllWSensors();
    }


    /**
     * Creates a new weightSensorData.
     *
     * @param weightSensorData The weightSensorData entity to be created.
     * @return The created weightSensorData entity.
     */
    @Override
    public WeightSensorData createSensor(WeightSensorData weightSensorData){
        sensorRepository.createSensor(weightSensorData);
        return weightSensorData;
    }


    /**
     * Reads data from an Arduino device connected to the specified serial port.
     * The data is continuously read in a separate thread until {@link #disconnect()} is called.
     *
     * @param portName The name of the serial port to which the Arduino is connected.
     * @throws RuntimeException If there is an issue opening or reading from the specified port.
     */
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

    /**
     * Processes data received from the Arduino device.
     * Extracts weight and calibration factor information and updates WeightSensorData
     * and MedicationSchedule entities.
     *
     * @param inputLine The raw input line received from the Arduino device.
     */
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

                // Retrieve the first WeightSensorData created from the repository

                WeightSensorData firstSensor = sensorRepository.findAllWSensors().stream().findFirst().orElse(null);
                logger.info("====================================");
                logger.info("First weight sensor: {}", firstSensor);

                // Update the values in the latest WeightSensorData
                if (firstSensor != null) {
                    firstSensor.updateValues(weight, calibrationFactor);
                    logger.info("Updating the values in the first WeightSensorData: {},{}", weight, calibrationFactor);
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
     * Calculates the weight of a single pill based on weight sensor data.
     *
     * Since the weight of a single pill can not be 0 or a negative number,
     * we are getting weight of single pill as 0.1 to avoid any issues with the calculation
     * due to the sensitivity problem of the weight sensor.
     *
     * @param weightSensorData The weight sensor data used for calculation.
     * @param nrOfPillsPlaced  The number of pills placed in the pill box.
     * @return The weight of a single pill.
     */

    public double calculateWeightOfSinglePill(WeightSensorData weightSensorData, int nrOfPillsPlaced) {
        double weightOfSinglePill = (weightSensorData.getWeight() - BOX_WEIGHT) / nrOfPillsPlaced;
        return Math.max(weightOfSinglePill,0.1);
    }

    /**
     * Processes weight data and updates relevant fields in a {@link MedicationSchedule}.
     * Prints the nrOfPillsTaken and nrOfPillsPlacedUpdated.
     *
     * @param medicationSchedule The medication schedule to update.
     * @param weightSensorData   The weight sensor data used for processing.
     */

    public void processWeightData(MedicationSchedule medicationSchedule, WeightSensorData weightSensorData) {
        int nrOfPillsPlaced = medicationSchedule.getNrOfPillsPlaced();
        logger.info("Number of pills placed: {}", nrOfPillsPlaced);

        double weightOfSinglePill = calculateWeightOfSinglePill(weightSensorData, nrOfPillsPlaced);

        medicationSchedule.setWeightOfSinglePill(weightOfSinglePill);
        logger.info("Weight of single pill: {}", weightOfSinglePill);

        // Checks if weight reduction indicates pill taken
        if (weightReductionIndicatesPillTaken(weightSensorData, medicationSchedule)) {

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
            medScheduleRepository.updateMedSchedule(medicationSchedule);
        }
    }


    /**
     * Checks if weight reduction indicates that a pill has been taken by the customer.
     *
     * @param weightSensorData    The weight sensor data used for comparison.
     * @param medicationSchedule  The medication schedule to update if a pill is taken.
     * @return {@code true} if weight reduction indicates a pill is taken, otherwise {@code false}.
     */
     private boolean weightReductionIndicatesPillTaken(WeightSensorData weightSensorData, MedicationSchedule medicationSchedule) {

            //CHECK THIS PART IF IT IS WORKING FINE
         // Get the latest weight sensor from the repository
//              List<WeightSensorData> weightSensorData = sensorRepository.findAllWSensors();
                List<WeightSensorData> weightSensors = findAllWSensors();

                if (weightSensors.size() < 2) {
                    logger.warn("Not enough weight sensor data found.");
                    return false;
                }

                logger.info("Weight sensor data: {}", weightSensors);

                double currentTotalWeight = weightSensors.get(0).getWeight();
                double previousTotalWeight = weightSensors.get(1).getWeight();

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


    /**
     * Rounds a given double value to a specified number of decimal places.
     *
     * @param value         The double value to be rounded.
     * @param decimalPlaces The number of decimal places to round to.
     * @return The rounded double value.
     */
    private double round(double value, int decimalPlaces) {
        double scale = Math.pow(10, decimalPlaces);
        return Math.round(value * scale) / scale;
    }


    /**
     * Disconnects the Arduino data reading thread by setting the stop flag to {@code true}.
     * This will stop the continuous reading of data from the Arduino device.
     */

    public void disconnect() {
        stopReadingArduinoData = true;
    }

}
