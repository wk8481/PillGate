package be.kdg.programming3.presentation;

import be.kdg.programming3.service.PIRSensorService;
import be.kdg.programming3.service.PIRSensorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/PillGate")
public class PIRController {
    private Logger logger = LoggerFactory.getLogger(PIRController.class);

    private final PIRSensorService sensorService;

    public PIRController(PIRSensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping("/dashboard")
    public String dashboard() {

        return "dashboard";
    }
    @GetMapping("/detectMotion")
    public String detectMotion() {
        return sensorService.detectMotion();
    }

    @GetMapping("/endMotion")
    public String endMotion() {
        return sensorService.endMotion();
    }
}

