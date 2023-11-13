package be.kdg.programming3.domain.sensor;

public class PIR extends Sensor {
    private double detectionRange;
    private boolean isMotionDetected;

    public PIR(int sensor_ID, String sensor_type, String status, String manufacturer, double detectionRange, boolean isMotionDetected) {
        super(sensor_ID, sensor_type, status, manufacturer);
        this.detectionRange = detectionRange;
        this.isMotionDetected = isMotionDetected;
    }
}
