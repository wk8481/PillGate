package be.kdg.programming3.pillgate.pres.controllers;//package be.kdg.programming3.oldproj.controllers;

import be.kdg.programming3.pillgate.domain.sensor.WeightSensor;
import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;
//import be.kdg.programming3.pillgate.repo.medSchedRepo.JDBCMedscheduleRepository;
import be.kdg.programming3.pillgate.repo.medSchedRepo.MedScheduleRepository;
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
    private final MedScheduleRepository medscheduleRepository;
    private Logger logger = LoggerFactory.getLogger(LoadSensorController.class);

    public LoadSensorController(SerialReader serialReader, SensorRepository sensorRepository, MedScheduleRepository medScheduleRepository ) {
        this.serialReader = serialReader;
        this.sensorRepository = sensorRepository;
        this.medscheduleRepository = medScheduleRepository;
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


    @GetMapping("readArduino/showPillsTaken")
    public String showNumberOfPillsTaken(Model model) {
        // Assuming you have a method in the service to get the latest medication schedule
        //MedicationSchedule latestMedSchedule = medicationScheduleService.getLatestMedicationSchedule();
        MedicationSchedule latestMedSchedule = medscheduleRepository.findAllMedSchedules().stream().findFirst().orElse(null);

        if (latestMedSchedule != null) {
            model.addAttribute("nrOfPillsTaken", latestMedSchedule.getNrOfPillsTaken());
        }

        return "dashboard";
    }


    @GetMapping("/createSensor")
    public String createSensor(Model model) {
        WeightSensor newSensor = new WeightSensor(/* initialize with necessary values */);
        sensorRepository.createSensor(newSensor);
        model.addAttribute("sensors", sensorRepository.findAllWSensors());
        return "dashboard";
    }

}
