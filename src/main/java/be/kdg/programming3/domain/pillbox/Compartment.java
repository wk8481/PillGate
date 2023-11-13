package be.kdg.programming3.domain.pillbox;

public class Compartment {
    private String medicationName;
    private int pillCount;
    private String color;
    private boolean hasBeenTaken;


    public Compartment(String medicationName, int pillCount, String color, boolean hasBeenTaken) {
        this.medicationName=medicationName;
        this.pillCount = pillCount;
        this.color = color;
        this.hasBeenTaken = hasBeenTaken;
    }

    public Compartment() {
      //implemented later
    }

    public String getMedicationName() {
        return medicationName;
    }

    public int getPillCount() {
        return pillCount;
    }

    public String getColor() {
        return color;
    }

    public boolean isHasBeenTaken() {
        return hasBeenTaken;
    }
}
