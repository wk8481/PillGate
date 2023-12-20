package be.kdg.programming3.pillgate.service;

import be.kdg.programming3.pillgate.domain.sensor.WeightSensor;

import java.io.IOException;

public interface SerialReader {
    void readArduinoData(String portName);

//    void saveWeightSensorDataToJson(WeightSensor weightSensor);

    void disconnect()throws IOException;
}