/*
package be.kdg.programming3.service;

import org.springframework.stereotype.Service;

@Service
public class SensorService {
    private boolean lockLow = true;
    private boolean takeLowTime;
    private long lowIn;
    private final long pause = 5000; // ms

    public String detectMotion() {
        // Logic to handle motion detection
        if (lockLow) {
            lockLow = false;
            // Log or return the response
            return "Motion detected";
        }
        takeLowTime = true;
        return "No motion";
    }

    public String endMotion() {
        // Logic to handle motion end
        if (takeLowTime) {
            lowIn = System.currentTimeMillis();
            takeLowTime = false;
        }

        if (!lockLow && System.currentTimeMillis() - lowIn > pause) {
            lockLow = true;
            return "Motion ended";
        }
        return "Motion ongoing";
    }
}
*/
