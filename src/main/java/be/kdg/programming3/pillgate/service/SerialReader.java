package be.kdg.programming3.pillgate.service;

import java.io.IOException;

public interface SerialReader {
    void readArduinoData(String portName);
    void disconnect()throws IOException;
}