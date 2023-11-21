package be.kdg.programming3.repository;

import be.kdg.programming3.domain.sensor.WeightSensor;
import java.util.List;

public interface SensorRepository {

    /* These methods show:
     *  - WeightSensor Repository methods */
    WeightSensor createSensor(WeightSensor w_sensor);
    List<WeightSensor> readWeightSensor();
    WeightSensor getWeightSensorById(int sensor_ID);

    WeightSensor updateSensor(WeightSensor existingWSensor);
}
