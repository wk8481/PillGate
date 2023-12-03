package be.kdg.programming3.pillgate.domain.sensor;


import be.kdg.programming3.pillgate.domain.user.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//@Entity
//@Table(name = "WeightSensor")
@Component
public class WeightSensor {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "SENSOR_ID")
    private int sensor_ID;
    private final int WEIGHT_CAPACITY_GRAMS = 1000;

    private LocalDate calibrationDate;

    private double weight;

    private double calibrationFactor;

//    @OneToOne
//    @JoinColumn(name = "customer_id")
    private transient Customer customer;


    public WeightSensor(int sensor_ID, LocalDate calibrationDate, double weight, double calibrationFactor, int customer_id) {
        this.calibrationDate = calibrationDate;
        this.sensor_ID = sensor_ID;
        this.weight = weight;
        this.calibrationFactor = calibrationFactor;
        this.customer = new Customer(customer_id);
    }
    public WeightSensor(int sensor_ID, int customer_id, int WEIGHT_CAPACITY_GRAMS, LocalDate calibrationDate , double weight) {
        this.calibrationDate = calibrationDate;
        this.sensor_ID = sensor_ID;
        this.weight = weight;
        this.customer = new Customer(customer_id);
    }

    public WeightSensor(int sensor_ID, LocalDate calibrationDate, double weight, double calibrationFactor, Customer customer) {
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

    public int getWEIGHT_CAPACITY_GRAMS() {
        return WEIGHT_CAPACITY_GRAMS;
    }

    public LocalDate getCalibrationDate() {
        return calibrationDate;
    }

    public Customer getCustomer() {return customer;}

    public void setCustomer(Customer customer) {this.customer = customer;}


//    public void addPillbox(PillBox pillBox) {
//        this.pillBox = pillBox;
//    }




    public void setCalibrationDate(LocalDate calibrationDate) {
        this.calibrationDate = calibrationDate;
    }

//    public PillBox getPillBox() {
//        return pillBox;
//    }
//
//    public void setPillBox(PillBox pillBox) {
//        this.pillBox = pillBox;
//    }

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
                ", WEIGHT_CAPACITY_GRAMS=" + WEIGHT_CAPACITY_GRAMS +
                ", calibrationDate=" + formattedDate +
                ", weight=" + weight +
                ", calibrationFactor=" + calibrationFactor +
                '}');
    }


    public int getWeightCapacityGrams() {
        return getWEIGHT_CAPACITY_GRAMS();
    }
    public void assignToCustomer(Customer customer) {
        this.customer = customer;
        customer.setWeightSensor(this);
    }

}
