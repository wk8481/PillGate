package be.kdg.programming3.domain.pillbox;

import be.kdg.programming3.domain.user.Customer;

public class Medicine {
    private String name;
    private String color;
    private String shape;
    private String type;
    private double weight;
    private int medicine_id;

    private PillBox pillBox;
    private Customer customer;

    public Medicine(String name, String color, String shape, String type, double weight, int medicine_id) {
        this.name = name;
        this.color = color;
        this.shape = shape;
        this.type = type;
        this.weight = weight;
        this.medicine_id = medicine_id;
    }


    // to be implemented later
    public Medicine() {

    }


    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getShape() {
        return shape;
    }

    public String getType() {
        return type;
    }

    public double getWeight() {
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

    public void setColor(String color) {
        this.color = color;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setPillBox(PillBox pillBox) {
        this.pillBox = pillBox;
    }

    public PillBox getPillBox() {
        return pillBox;
    }

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
                ", color='" + color + '\'' +
                ", shape='" + shape + '\'' +
                ", type='" + type + '\'' +
                ", weight=" + weight +
                ", medicine_id=" + medicine_id +
                '}';
    }


    public void addPillbox(PillBox pillBox) {
        this.pillBox = pillBox;
    }

    public void addCustomer(Customer customer) {
        this.customer = customer;
    }



}
