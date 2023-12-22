package be.kdg.programming3.pillgate.pres.controllers;
import be.kdg.programming3.pillgate.repo.customerRepo.CustomerRepository;
import be.kdg.programming3.pillgate.repo.medSchedRepo.MedScheduleRepository;
import be.kdg.programming3.pillgate.service.ReminderService;
import be.kdg.programming3.pillgate.service.SerialReader;
import be.kdg.programming3.pillgate.domain.user.Customer;
import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;

import be.kdg.programming3.pillgate.repo.customerRepo.CustomerRepository;
import be.kdg.programming3.pillgate.repo.medSchedRepo.MedScheduleRepository;
import be.kdg.programming3.pillgate.service.ReminderService;

import be.kdg.programming3.pillgate.service.SerialReader;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/alarm")
public class ReminderController {

    private CustomerRepository customerRepository;
    private ReminderService reminderService;
    private SerialReader sensorservice;

    private final Logger logger = LoggerFactory.getLogger(ReminderController.class);

    @Autowired
    public ReminderController(CustomerRepository customerRepository, ReminderService reminderService) {
        this.customerRepository = customerRepository;
        this.reminderService =  reminderService;

    }


    @GetMapping(path = "/now", produces = "application/json")
    public @ResponseBody AlarmResponse getCurrentAlarm() {
        String message = reminderService.getMedScheduleAlert();
        return new AlarmResponse(message);
    }

    @GetMapping(path = "/repeat", produces = "application/json")
    public @ResponseBody AlarmResponse getRepeatAlarm() {
        String message = reminderService.getAlertForRepeatIn();
        return new AlarmResponse(message);
    }


    public static class AlarmResponse {
        private String message;

        public AlarmResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}

