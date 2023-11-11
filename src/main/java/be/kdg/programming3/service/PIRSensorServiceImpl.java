package be.kdg.programming3.service;

import be.kdg.programming3.repository.PIRDataHandler;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class PIRSensorServiceImpl implements PIRSensorService {
    private boolean lockLow = true;
    private long lowIn;
    private final long pause = 5000; // ms
    private final PIRDataHandler pirDataHandler;

    @Autowired
    public PIRSensorServiceImpl(PIRDataHandler pirDataHandler) {
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

    public String endMotion() {
        // Simulates checking if motion has ended
        if (!lockLow && (System.currentTimeMillis() - lowIn) > pause) {
            lockLow = true;
            pirDataHandler.handleData("Motion ended");
            return "Motion Ended";
        }
        return "Motion Ongoing";
    }

}
