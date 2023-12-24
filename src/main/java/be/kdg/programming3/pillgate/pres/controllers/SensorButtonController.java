package be.kdg.programming3.pillgate.pres.controllers;

import be.kdg.programming3.pillgate.domain.sensor.WeightSensorData;
import be.kdg.programming3.pillgate.repo.sensorRepo.SensorRepository;
import be.kdg.programming3.pillgate.service.SensorService;
import be.kdg.programming3.pillgate.domain.sensor.WeightSensorData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
/**
 * The {@code SensorButtonController} class is a Spring MVC controller that handles requests related to the Sensor Button actions.
 * It provides buttons to read the {@link  WeightSensorData}  and to stop it to.
 * This controller class defines two endpoints, one for reading sensor data and another for disconnecting the sensor.
 * @author Team PillGate
 */
@Controller
@RequestMapping("/serial")
public class SensorButtonController {

    private SensorService sensorService;


    public SensorButtonController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    /**
     * Handles the GET request to read sensor data.
     * @return A response entity indicating the success or failure of the sensor reading operation.
     */
    @GetMapping("/readSensor")
    public ResponseEntity<String> readSensor() {
        try {
            sensorService.readArduinoData("COM5");
            return ResponseEntity.ok("Read sensor success");
        } catch(Exception e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading sensor");
        }
        return null;
    }

    /**
     * Handles the GET request to disconnect the sensor.
     * @return A response entity indicating the success or failure of the sensor disconnection operation.
     */
    @GetMapping("/disconnectSensor")
    public ResponseEntity<String> disconnectSensor(){
        try{
            sensorService.disconnect();
            return ResponseEntity.ok("Disconnect success");
        }catch(IOException e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error disconnectiong sensor");
        }
        return null;
    }

}
