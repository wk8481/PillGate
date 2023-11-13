package be.kdg.programming3.domain.sensor;

public class Sensor {

    private int sensor_ID;
    private String sensor_type;
    private String status;
    private String manufacturer;

    public Sensor(int sensor_ID, String sensor_type, String status, String manufacturer) {
        this.sensor_ID = sensor_ID;
        this.sensor_type = sensor_type;
        this.status = status;
        this.manufacturer = manufacturer;
    }

    public Sensor() {

    }
}
