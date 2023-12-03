package be.kdg.programming3.pillgate.pres.controllers;//package be.kdg.programming3.oldproj.controllers;

import be.kdg.programming3.pillgate.repo.sensorRepo.SensorRepository;
import be.kdg.programming3.pillgate.service.SerialReader;
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

    @GetMapping()
    public String showSensor(Model model) {
        logger.info("Showing load sensor data ..");
        model.addAttribute("sensors", sensorRepository.findAllWSensors());
        return "dashboard";
    }

    @GetMapping("/readArduino")
    public String readArduino(Model model) {
        try {
            serialReader.readArduinoData("COM5");
            model.addAttribute("sensors", sensorRepository.findAllWSensors());
        } catch (Exception e) {
            logger.info("Error reading Arduino data", e);
        }
        return "dashboard";
    }


}
