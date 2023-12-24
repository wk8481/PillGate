package be.kdg.programming3.pillgate.service;

import be.kdg.programming3.pillgate.domain.sensor.WeightSensorData;
import be.kdg.programming3.pillgate.repo.sensorRepo.SensorRepository;

import java.io.IOException;
import java.util.List;


/**
 * The {@code SensorService} interface defines methods for reading data from an Arduino device.
 *
 * @author Team PillGate
 * @see SensorRepository
 * @see WeightSensorData
 */
public interface SensorService {

    List<WeightSensorData> findAllWSensors();

    WeightSensorData createSensor(WeightSensorData weightSensorData);

    /**
     * Reads data from an Arduino device connected to the specified port.
     *
     * @param portName The name of the port to which the Arduino device is connected.
     */
    void readArduinoData(String portName);

    /**
     * Disconnects from the Arduino device.
     *
     * @throws IOException If an I/O error occurs while disconnecting.
     */
    void disconnect() throws IOException;


}
