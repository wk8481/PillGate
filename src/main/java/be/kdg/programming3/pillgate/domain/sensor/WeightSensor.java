package be.kdg.programming3.pillgate.domain.sensor;


import be.kdg.programming3.pillgate.domain.user.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
public class WeightSensor {

    private int sensor_ID;

    private LocalDateTime calibrationDate;

    private double weight;

    private double calibrationFactor;


    private transient Customer customer;


    public WeightSensor(int sensor_ID, LocalDateTime calibrationDate, double weight, double calibrationFactor, int customer_id) {
        this.calibrationDate = calibrationDate;
        this.sensor_ID = sensor_ID;
        this.weight = weight;
        this.calibrationFactor = calibrationFactor;
        this.customer = new Customer(customer_id);
    }
    public WeightSensor(int sensor_ID, int customer_id, LocalDateTime calibrationDate , double weight) {
        this.calibrationDate = calibrationDate;
        this.sensor_ID = sensor_ID;
        this.weight = weight;
        this.customer = new Customer(customer_id);
    }

    public WeightSensor(int sensor_ID, LocalDateTime calibrationDate, double weight, double calibrationFactor, Customer customer) {
        this.sensor_ID = sensor_ID;
        this.calibrationDate = calibrationDate;
        this.weight = weight;
        this.calibrationFactor = calibrationFactor;
        this.customer = customer;
    }

    public WeightSensor() {

    }

    public void updateValues(double weight, double calibrationFactor) {
        this.weight = weight;
        this.calibrationFactor = calibrationFactor;
    }


    public WeightSensor(double weight){
        this.weight = weight;
    }

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
//


    public LocalDateTime getCalibrationDate() {
        return calibrationDate;
    }

    public Customer getCustomer() {return customer;}

    public void setCustomer(Customer customer) {this.customer = customer;}

    public void setCalibrationDate(LocalDateTime calibrationDate) {
        this.calibrationDate = calibrationDate;
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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");

        String formattedDate = dtf.format(calibrationDate);
        return String.format("WeightSensor{" +
                "sensor_ID=" + sensor_ID +
                ", customer_id=" + customer.getCustomer_id() +
                ", calibrationDate=" + formattedDate +
                ", weight=" + weight +
                ", calibrationFactor=" + calibrationFactor +
                '}');
    }

    public void assignToCustomer(Customer customer) {
        this.customer = customer;
        customer.setWeightSensor(this);
    }

}


