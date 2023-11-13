package be.kdg.programming3.presentation;

import be.kdg.programming3.service.PIRSensorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import java.util.List;


@Controller
@RequestMapping("/PillGate")
public class PIRController {
    private Logger logger = LoggerFactory.getLogger(PIRController.class);

    private final PIRSensorService sensorService;

    public PIRController(PIRSensorService sensorService) {
        this.sensorService = sensorService;
    }
//    @GetMapping("/viewCSV")
//    public String viewCSV(Model model) {
//        // Read "pillData.csv" and provide data to the template
//        List<String> csvData = readCSVFile("pillData.csv");
//        model.addAttribute("csvData", csvData);
//
//        return "viewCSV";
//    }


//    @GetMapping("/viewCSV")
//    public String viewCSV(Model model) {
//        // Read "pillData.csv" and provide data to the template
//        List<String> csvData = sensorService.readCSVFile("pillData.csv");
//        model.addAttribute("csvData", csvData);
//
//        return "viewCSV";
//    }



    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Read "pillData.csv" and provide data to the template
        List<String> csvData = sensorService.readCSVFile("pillData.csv");
        model.addAttribute("csvData", csvData);

        // Add the "dashboard" attribute with the desired content
        model.addAttribute("dashboardContent", "Your Dashboard Content Goes Here");

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

