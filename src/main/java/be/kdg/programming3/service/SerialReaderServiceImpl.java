package be.kdg.programming3.service;

import be.kdg.programming3.domain.sensor.WeightSensor;
import be.kdg.programming3.repository.SensorRepository;
import com.fazecast.jSerialComm.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class SerialReaderServiceImpl implements SerialReader{
    private BufferedReader input;
    private final SensorRepository sensorRepository;
    private Logger logger = LoggerFactory.getLogger(SerialReaderServiceImpl.class);

    public SerialReaderServiceImpl(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
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
            logger.error("Port not found: {}", portName);
            throw new RuntimeException("Port not found: " + portName);
        }

        if (serialPort.openPort()) {
            // Set up the input stream
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));

            // Start a thread to continuously read data
            new Thread(() -> {
                try {
                    while (true) {
                        logger.info("Arduino data reading thread started.");
                        String inputLine = input.readLine();
                        if (inputLine != null) {
                            processArduinoData(inputLine);
                        }
                    }
                } catch (IOException e) {
                    logger.error("Error reading Arduino data", e);
                    e.printStackTrace();
                } finally {
                    logger.info("Arduino data reading thread terminated.");
                }
            }).start();
        } else {
            logger.error("Failed to open port: {}", portName);
            throw new RuntimeException("Failed to open port: " + portName);
        }
    }

    private void processArduinoData(String inputLine) {
        logger.info("Received Arduino data: {}", inputLine);
        // Assuming inputLine is in the format: "Reading: X.XX grams calibration factor: YYY.ZZZ"
        // Extract the relevant information
        String[] parts = inputLine.split(" ");
        if (parts.length >= 8 && parts[0].equals("Reading:")) {
            try {
                double weight = Double.parseDouble(parts[1]);
                double calibrationFactor = Double.parseDouble(parts[7]);

                // Retrieve the latest WeightSensor from the repository
                WeightSensor latestSensor = sensorRepository.readWeightSensor().stream().findFirst().orElse(null);

                // Update the values in the latest WeightSensor
                if (latestSensor != null) {
                    latestSensor.updateValues(weight, calibrationFactor);
                    // Save the updated sensor back to the repository
                    sensorRepository.updateSensor(latestSensor);
                    // Log the updated sensor
                    System.out.println("Weight sensor updated: " + latestSensor);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format in data: " + inputLine);
            }
        } else {
            System.out.println("Invalid data format: " + inputLine);
        }
    }

    public void disconnect() throws IOException {
        if (input != null) {
            input.close();
        }
    }

}