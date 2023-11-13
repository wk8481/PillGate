package be.kdg.programming3.domain.pillbox;

public class Medicine {
    private String name;
    private String color;
    private String shape;
    private String type;
    private double weight;
    private int medicine_id;

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
}
