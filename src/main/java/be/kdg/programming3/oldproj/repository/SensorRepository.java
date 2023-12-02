package be.kdg.programming3.oldproj.repository;

import be.kdg.programming3.oldproj.domain.sensor.WeightSensor;
import java.util.List;

public interface SensorRepository {

    /* These methods show:
     *  - WeightSensor Repository methods */
    WeightSensor createSensor(WeightSensor weightSensor);
    List<WeightSensor> findAllWSensors();
    WeightSensor findSenorByID(int sensor_ID);
    WeightSensor updateSensor(WeightSensor existingWSensor);
}
