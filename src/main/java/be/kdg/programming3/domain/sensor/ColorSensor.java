package be.kdg.programming3.domain.sensor;

public class ColorSensor extends Sensor {
    private String colorSpace;
    private double wavelengthRange;

    public ColorSensor(int sensor_ID, String sensor_type, String status, String manufacturer, String colorSpace, double wavelengthRange) {
        super(sensor_ID, sensor_type, status, manufacturer);
        this.colorSpace = colorSpace;
        this.wavelengthRange = wavelengthRange;
    }


}
