package be.kdg.programming3.presentation;

import be.kdg.programming3.service.SensorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PIRController {
    private final SensorService sensorService;

    public PIRController(SensorService sensorService) {
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
