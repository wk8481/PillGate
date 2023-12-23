package be.kdg.programming3.pillgate.repo.sensorRepo;

import be.kdg.programming3.pillgate.domain.sensor.WeightSensorData;
import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;

import java.util.List;

/**
 * The {@code SensorRepository} interface provides methods for interacting with Weight Sensors in the repository.
 * @author Team PillGate
 * @see WeightSensorData
 */

public interface SensorRepository {

    /**
     * Creates a new Weight Sensor in the repository.
     *
     * @param weightSensorData The Weight Sensor to be created.
     * @return The created Weight Sensor.
     */
    WeightSensorData createSensor(WeightSensorData weightSensorData);

    /**
     * Retrieves a list of all Weight Sensors in the repository.
     *
     * @return A list of Weight Sensors.
     */
    List<WeightSensorData> findAllWSensors();

    /**
     * Retrieves a Weight Sensor by its unique identifier.
     *
     * @param sensor_ID The unique identifier of the Weight Sensor.
     * @return The Weight Sensor with the specified identifier, or {@code null} if not found.
     */
    WeightSensorData findSensorByID(int sensor_ID);

    /**
     * Updates an existing Weight Sensor in the repository.
     *
     * @param existingWSensor The existing Weight Sensor to be updated.
     * @return The updated Weight Sensor.
     */
    WeightSensorData updateSensor(WeightSensorData existingWSensor);
}
