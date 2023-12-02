/*
package be.kdg.programming3.oldproj.service;

import be.kdg.programming3.oldproj.repository.LSDataHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class LSensorServiceImpl implements PIRSensorService {
    Logger logger = LoggerFactory.getLogger(LSensorServiceImpl.class);
    private boolean lockLow = true;
    private long lowIn;
    private final long pause = 5000; // ms
    private final LSDataHandler pirDataHandler;

    @Autowired
    public LSensorServiceImpl(LSDataHandler pirDataHandler) {
        this.pirDataHandler = pirDataHandler;
    }

    @Override
    public void checkSensor() {

        boolean isMotionDetected = Math.random() > 0.5; // Randomly simulates motion detection

        if (isMotionDetected) {
            if (lockLow) {
                lockLow = false;
                lowIn = System.currentTimeMillis(); // Record the time when motion is detected
                pirDataHandler.handleData("Motion detected");
            }
        } else {
            if (!lockLow && (System.currentTimeMillis() - lowIn) > pause) {
                lockLow = true;
                pirDataHandler.handleData("Motion ended");
            }
        }
    }
    @Override
    public String detectMotion() {
        // Simulates checking the PIR sensor for motion
        // In a real scenario, replace this with actual sensor interaction logic.
        boolean isMotionDetected = Math.random() > 0.5; // Randomly simulates motion detection

        if (isMotionDetected) {
            if (lockLow) {
                lockLow = false;
                lowIn = System.currentTimeMillis(); // Record the time when motion is detected
                pirDataHandler.handleData("Motion detected");
                return "Motion Detected";
            }
        }
        return "No Motion";
    }

    @Override
    public String endMotion() {
        // Simulates checking if motion has ended
        if (!lockLow && (System.currentTimeMillis() - lowIn) > pause) {
            lockLow = true;
            pirDataHandler.handleData("Motion ended");
            return "Motion Ended";
        }
        return "Motion Ongoing";
    }

    @Override
    public List<String> readCSVFile(String fileName) {
        // Implement logic to read the CSV file and return data as a list
        // You can use a CSV parsing library or manually parse the file
        // For simplicity, assuming it's a text file
        List<String> csvData = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                csvData.add(line);
            }
            reader.close();
        } catch (IOException e) {
            logger.error("Error reading CSV file: {}", e.getMessage());
        }
        return csvData;
    }

}
*/
