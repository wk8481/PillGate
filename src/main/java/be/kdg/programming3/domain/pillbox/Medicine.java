package be.kdg.programming3.domain.pillbox;

public class Medicine {
    private String name;
    private String color;
    private String shape;
    private String type;
    private double weight;

    public Medicine(String name, String color, String shape, String type, double weight) {
        this.name = name;
        this.color = color;
        this.shape = shape;
        this.type = type;
        this.weight = weight;
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
}
