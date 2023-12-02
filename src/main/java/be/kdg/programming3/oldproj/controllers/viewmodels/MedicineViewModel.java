package be.kdg.programming3.oldproj.controllers.viewmodels;

import be.kdg.programming3.oldproj.domain.sensor.WeightSensor;
import be.kdg.programming3.oldproj.domain.user.Customer;
import jakarta.validation.constraints.NotBlank;

public class MedicineViewModel {
    @NotBlank(message = "name should not be blank")
    private String name;
    private WeightSensor weight;
    private int medicine_id;

    //    private  transient PillBox pillBox;
    private transient Customer customer;

    public MedicineViewModel(String name, WeightSensor weight, int medicine_id) {
        this.name = name;
        this.weight = weight;
        this.medicine_id = medicine_id;
    }

    // to be implemented later
    public MedicineViewModel() {

    }


    public String getName() {
        return name;
    }


    public WeightSensor getWeight() {
        return weight;
    }

    public int getMedicine_id() {
        return medicine_id;
    }

    public void setMedicine_id(int medicine_id) {
        this.medicine_id = medicine_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(WeightSensor weight) {
        this.weight = weight;
    }

/*
    public void setPillBox(PillBox pillBox) {
        this.pillBox = pillBox;
    }
*/

/*
    public PillBox getPillBox() {
        return pillBox;
    }
*/

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", medicine_id=" + medicine_id +
                '}';
    }


/*
    public void addPillbox(PillBox pillBox) {
        this.pillBox = pillBox;
    }
*/

    public void addCustomer(Customer customer) {
        this.customer = customer;
    }

}
