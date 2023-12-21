package be.kdg.programming3.pillgate.pres.controllers;//package be.kdg.programming3.oldproj.controllers;

import be.kdg.programming3.pillgate.domain.sensor.WeightSensor;
import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;
import be.kdg.programming3.pillgate.repo.medSchedRepo.MedScheduleRepository;
import be.kdg.programming3.pillgate.repo.sensorRepo.SensorRepository;
import be.kdg.programming3.pillgate.service.ReminderService;
import be.kdg.programming3.pillgate.service.SerialReader;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("/loadSensor")
public class LoadSensorController {

    private final SerialReader serialReader;
    private final SensorRepository sensorRepository;
    private final ReminderService reminderService;
    private final Logger logger = LoggerFactory.getLogger(LoadSensorController.class);

    public LoadSensorController(SerialReader serialReader, SensorRepository sensorRepository, ReminderService reminderService) {
        this.serialReader = serialReader;
        this.sensorRepository = sensorRepository;
        this.reminderService = reminderService;
    }

    @GetMapping()
    public String showSensor(Model model, HttpSession session) {
        if (session.getAttribute("authenticatedUser") != null) {
            logger.info("Dashboard ..");
            model.addAttribute("sensors", sensorRepository.findAllWSensors());
        return "dashboard";
        } else {
            logger.info("Customer not authenticated. Session details: {}", session.getAttributeNames());
        }
        return "dashboard";
    }

    @GetMapping("/readArduino")
    public String readArduino(Model model, HttpSession session) {
        if (session.getAttribute("authenticatedUser") != null) {

            try {
                serialReader.readArduinoData("COM5");
                model.addAttribute("sensors", sensorRepository.findAllWSensors());
            } catch (Exception e) {
                logger.info("Error reading Arduino data", e);
            }
            return "dashboard";
        } else {
            logger.info("Customer not authenticated. Session details: {}", session.getAttributeNames());
        }
        return "dashboard";
    }


    @GetMapping("/readArduino/showPillsTaken")
    public String showNumberOfPillsTaken(Model model, HttpSession session) {
        if (session.getAttribute("authenticatedUser") != null) {
            MedicationSchedule latestMedSchedule = reminderService.getLatestMedicationSchedule();
            logger.info("Getting latest medication schedule {}", latestMedSchedule);

            if (latestMedSchedule != null) {
                model.addAttribute("nrOfPillsTaken", latestMedSchedule.getNrOfPillsTaken());
                logger.info("Showing number of pills taken {}", latestMedSchedule.getNrOfPillsTaken());

                model.addAttribute("weightOfSinglePill", latestMedSchedule.getWeightOfSinglePill());
                logger.info("Weight of Single Pill: {}", latestMedSchedule.getWeightOfSinglePill());

                model.addAttribute("nrOfPillsPlaced", latestMedSchedule.getNrOfPillsPlaced());
                logger.info("Number of Pills Placed: {}", latestMedSchedule.getNrOfPillsPlaced());
            }
        } else {
            logger.info("Customer not authenticated. Session details: {}", session.getAttributeNames());
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
