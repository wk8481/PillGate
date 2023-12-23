package be.kdg.programming3.pillgate.pres.controllers;

import be.kdg.programming3.pillgate.repo.customerRepo.CustomerRepository;
import be.kdg.programming3.pillgate.repo.medSchedRepo.MedScheduleRepository;
import be.kdg.programming3.pillgate.service.ReminderService;
import be.kdg.programming3.pillgate.service.SerialReader;
import be.kdg.programming3.pillgate.domain.user.Customer;
import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The {@code ReminderController} class is a Spring MVC controller that handles requests related to medication reminders.
 * It provides endpoints to retrieve current medication alerts and alerts for repeated medication schedules.
 */
@RestController
@RequestMapping("/alarm")
public class ReminderController {

    private CustomerRepository customerRepository;
    private ReminderService reminderService;
    private SerialReader sensorservice;

    private final Logger logger = LoggerFactory.getLogger(ReminderController.class);

    /**
     * Constructs a new {@code ReminderController} with the specified repositories and services.
     *
     * @param customerRepository The repository for customer-related operations.
     * @param reminderService    The service responsible for medication reminder-related operations.
     */
    @Autowired
    public ReminderController(CustomerRepository customerRepository, ReminderService reminderService) {
        this.customerRepository = customerRepository;
        this.reminderService = reminderService;
    }

    /**
     * Retrieves the current medication alert.
     *
     * @return An {@link AlarmResponse} containing the current medication alert message.
     */
    @GetMapping(path = "/now", produces = "application/json")
    public @ResponseBody AlarmResponse getCurrentAlarm() {
        String message = reminderService.getMedScheduleAlert();
        return new AlarmResponse(message);
    }

    /**
     * Retrieves the medication alert for repeated medication schedules.
     *
     * @return An {@link AlarmResponse} containing the medication alert message for repeated schedules.
     */
    @GetMapping(path = "/repeat", produces = "application/json")
    public @ResponseBody AlarmResponse getRepeatAlarm() {
        String message = reminderService.getAlertForRepeatIn();
        return new AlarmResponse(message);
    }

    /**
     * The nested class representing the response for medication alerts.
     */
    public static class AlarmResponse {
        private String message;

        /**
         * Constructs a new {@code AlarmResponse} with the specified message.
         *
         * @param message The message representing the medication alert.
         */
        public AlarmResponse(String message) {
            this.message = message;
        }

        /**
         * Gets the message representing the medication alert.
         *
         * @return The message of the medication alert.
         */
        public String getMessage() {
            return message;
        }
    }
}
