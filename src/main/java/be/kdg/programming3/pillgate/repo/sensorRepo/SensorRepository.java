package be.kdg.programming3.pillgate.repo.sensorRepo;


import be.kdg.programming3.pillgate.domain.sensor.WeightSensor;

import java.util.List;

public interface SensorRepository {

    /* These methods show:
     *  - WeightSensor Repository methods */
    WeightSensor createSensor(WeightSensor weightSensor);
    List<WeightSensor> findAllWSensors();

    WeightSensor findSensorByID(int sensor_ID);

    WeightSensor updateSensor(WeightSensor existingWSensor);
}
