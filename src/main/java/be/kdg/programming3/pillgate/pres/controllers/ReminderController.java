package be.kdg.programming3.pillgate.pres.controllers;

import be.kdg.programming3.pillgate.repo.customerRepo.CustomerRepository;
import be.kdg.programming3.pillgate.service.ReminderService;

import be.kdg.programming3.pillgate.service.SensorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * The {@code ReminderController} class is a Spring MVC controller that handles requests related to medication reminders.
 * It provides endpoints to retrieve current medication alerts and alerts for repeated medication schedules.
 * @author Team PillGate
 */
@RestController
@RequestMapping("/alarm")
public class ReminderController {
    private ReminderService reminderService;
    private SensorService sensorservice;
    private final Logger logger = LoggerFactory.getLogger(ReminderController.class);

    /**
     * Constructs a new {@code ReminderController} with the specified repositories and services.
     * @param reminderService    The service responsible for medication reminder-related operations.
     */
    @Autowired
    public ReminderController(ReminderService reminderService) {
     
        this.reminderService = reminderService;
    }

    /**
     * Retrieves the current medication alert.
     * @return An {@link AlarmResponse} containing the current medication alert message.
     */
    @GetMapping(path = "/now", produces = "application/json")
    public @ResponseBody AlarmResponse getCurrentAlarm() {
        String message = reminderService.getMedScheduleAlert();
        return new AlarmResponse(message);
    }

    /**
     * Retrieves the medication alert for repeated medication schedules.
     * @return An {@link AlarmResponse} containing the medication alert message for repeated schedules.
     */
    @GetMapping(path = "/repeat", produces = "application/json")
    public @ResponseBody AlarmResponse getRepeatAlarm() {
        String message = reminderService.getAlertForRepeatIn();
        return new AlarmResponse(message);
    }

    /**
     * The nested class representing the response for sending reminder.
     * The attribute message contains the message for reminder.
     */
    public static class AlarmResponse {
        private String message;

        /**
         * Constructs a new {@code AlarmResponse} with the specified message.
         * @param message The message representing the medication alert.
         */
        public AlarmResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
