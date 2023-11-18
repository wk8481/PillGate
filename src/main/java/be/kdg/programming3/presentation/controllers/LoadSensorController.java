package be.kdg.programming3.presentation.controllers;

import be.kdg.programming3.repository.SensorRepository;
import be.kdg.programming3.service.SerialReader;
import be.kdg.programming3.service.SerialReaderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/loadSensor")
public class LoadSensorController {

    private final SerialReader serialReader;
    private final SensorRepository sensorRepository;
    private Logger logger = LoggerFactory.getLogger(LoadSensorController.class);
    public LoadSensorController(SerialReader serialReader, SensorRepository sensorRepository) {
        this.serialReader = serialReader;
        this.sensorRepository = sensorRepository;
    }

    @GetMapping("dashboard")
    public String showSensor(Model model) {
        logger.debug("Showing load sensor data ..");
        model.addAttribute("sensors", sensorRepository.readWeightSensor());
        return "dashboard";
    }

    @GetMapping("/readArduino")
    public String readArduino() {
        try {
            serialReader.readArduinoData("COM5");
        } catch (Exception e) {
            logger.error("Error reading Arduino data", e);
        }
        return "dashboard";
    }

}
