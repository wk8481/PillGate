package be.kdg.programming3.pillgate.repo.sensorRepo;

import be.kdg.programming3.pillgate.domain.sensor.WeightSensor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The {@code SensorRepository} interface provides methods for interacting with Weight Sensors in the repository.
 */

public interface SensorRepository {

    /**
     * Creates a new Weight Sensor in the repository.
     *
     * @param weightSensor The Weight Sensor to be created.
     * @return The created Weight Sensor.
     */
    WeightSensor createSensor(WeightSensor weightSensor);

    /**
     * Retrieves a list of all Weight Sensors in the repository.
     *
     * @return A list of Weight Sensors.
     */
    List<WeightSensor> findAllWSensors();

    /**
     * Retrieves a Weight Sensor by its unique identifier.
     *
     * @param sensor_ID The unique identifier of the Weight Sensor.
     * @return The Weight Sensor with the specified identifier, or {@code null} if not found.
     */
    WeightSensor findSensorByID(int sensor_ID);

    /**
     * Updates an existing Weight Sensor in the repository.
     *
     * @param existingWSensor The existing Weight Sensor to be updated.
     * @return The updated Weight Sensor.
     */
    WeightSensor updateSensor(WeightSensor existingWSensor);
}
