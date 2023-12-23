package be.kdg.programming3.pillgate.pres.controllers;

import be.kdg.programming3.pillgate.repo.sensorRepo.SensorRepository;
import be.kdg.programming3.pillgate.service.SerialReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/serial")
public class SensorButtonController {

    private SerialReader serialReader;
    private SensorRepository sensorRepository;
    private Logger logger = LoggerFactory.getLogger(SensorButtonController.class);


    public SensorButtonController(SensorService sensorService) {
        this.sensorService = sensorService;
    }


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
