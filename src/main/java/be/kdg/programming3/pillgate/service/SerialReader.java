package be.kdg.programming3.pillgate.service;

import be.kdg.programming3.pillgate.domain.sensor.WeightSensor;

import java.io.IOException;

/**
 * The {@code SerialReader} interface defines methods for reading data from an Arduino device.
 *
 * <p>This interface is part of the PillGate application developed by Team PillGate.</p>
 *
 * @author Team PillGate
 */
public interface SerialReader {

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
