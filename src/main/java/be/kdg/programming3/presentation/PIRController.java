package be.kdg.programming3.presentation;

import be.kdg.programming3.service.PIRSensorServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PIRController {
    private final PIRSensorServiceImpl sensorService;

    public PIRController(PIRSensorServiceImpl sensorService) {
        this.sensorService = sensorService;
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
