package be.kdg.programming3.domain.sensor;

import java.time.LocalDate;

public class WeightSensor {
    private int sensor_ID;
    private String sensor_type;
    private String manufacturer;
    private final int WEIGHT_CAPACITY_GRAMS = 1000;
    private LocalDate calibrationDate;

    public WeightSensor(int sensor_ID, String sensor_type, String manufacturer, LocalDate calibrationDate) {
        this.calibrationDate = calibrationDate;
        this.sensor_ID = sensor_ID;
        this.sensor_type = sensor_type;
        this.manufacturer = manufacturer;
    }
    public WeightSensor(){}

    public int getSensor_ID() {
        return sensor_ID;
    }

    public void setSensor_ID(int sensor_ID) {
        this.sensor_ID = sensor_ID;
    }

    public String getSensor_type() {
        return sensor_type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getWEIGHT_CAPACITY_GRAMS() {
        return WEIGHT_CAPACITY_GRAMS;
    }

    public LocalDate getCalibrationDate() {
        return calibrationDate;
    }

    @Override
    public String toString() {
        return "WeightSensor{" +
                "sensor_ID=" + sensor_ID +
                ", sensor_type='" + sensor_type + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", WEIGHT_CAPACITY_GRAMS=" + WEIGHT_CAPACITY_GRAMS +
                ", calibrationDate=" + calibrationDate +
                '}';
    }
}
