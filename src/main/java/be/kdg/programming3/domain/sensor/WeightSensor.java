package be.kdg.programming3.domain.sensor;

import java.time.LocalDate;

public class WeightSensor extends Sensor {
    private final int WEIGHT_CAPACITY_GRAMS = 1000;
    private LocalDate calibrationDate;

    public WeightSensor(int sensor_ID, String sensor_type, String status, String manufacturer, LocalDate calibrationDate) {
        super(sensor_ID, sensor_type, status, manufacturer);
        this.calibrationDate = calibrationDate;
    }

}
