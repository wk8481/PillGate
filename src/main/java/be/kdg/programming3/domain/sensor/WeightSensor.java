package be.kdg.programming3.domain.sensor;

import be.kdg.programming3.domain.pillbox.PillBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WeightSensor {
    private int sensor_ID;
    private String sensor_type;
    private String manufacturer;
    private final int WEIGHT_CAPACITY_GRAMS = 1000;
    private LocalDate calibrationDate;

    private double weight;
    private double calibrationFactor;

    private PillBox pillBox;
    public WeightSensor(int sensor_ID, String sensor_type, String manufacturer, LocalDate calibrationDate, double weight, double calibrationFactor) {
        this.calibrationDate = calibrationDate;
        this.sensor_ID = sensor_ID;
        this.sensor_type = sensor_type;
        this.manufacturer = manufacturer;
        this.weight = weight;
        this.calibrationFactor = calibrationFactor;
    }

    public void updateValues(double weight, double calibrationFactor) {
        this.weight = weight;
        this.calibrationFactor = calibrationFactor;
    }
    public WeightSensor(){}

    public double getWeight() {
        return weight;
    }

    public double getCalibrationFactor() {
        return calibrationFactor;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setCalibrationFactor(double calibrationFactor) {
        this.calibrationFactor = calibrationFactor;
    }

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



    public void addPillbox(PillBox pillBox) {
        this.pillBox = pillBox;
    }

    public void setSensor_type(String sensor_type) {
        this.sensor_type = sensor_type;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setCalibrationDate(LocalDate calibrationDate) {
        this.calibrationDate = calibrationDate;
    }

    public PillBox getPillBox() {
        return pillBox;
    }

    public void setPillBox(PillBox pillBox) {
        this.pillBox = pillBox;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        WeightSensor that = (WeightSensor) obj;
        return sensor_ID == that.sensor_ID;
    }
    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String formattedDate = dtf.format(calibrationDate);
        return String.format("WeightSensor{" +
                "sensor_ID=" + sensor_ID +
                ", sensor_type='" + sensor_type + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", WEIGHT_CAPACITY_GRAMS=" + WEIGHT_CAPACITY_GRAMS +
                ", calibrationDate=" + formattedDate +
                ", weight=" + weight +
                ", calibrationFactor=" + calibrationFactor +
                '}');
    }


    public int getWeightCapacityGrams() {
        return getWEIGHT_CAPACITY_GRAMS();
    }
}
