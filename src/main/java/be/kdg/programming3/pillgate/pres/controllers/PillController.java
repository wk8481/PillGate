package be.kdg.programming3.pillgate.pres.controllers;

import be.kdg.programming3.pillgate.pres.controllers.viewmodels.MedicationScheduleViewModel;
import be.kdg.programming3.pillgate.service.CustomerService;
import be.kdg.programming3.pillgate.service.ReminderService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;




@Controller
@RequestMapping("/")
public class PillController {

    Logger logger = LoggerFactory.getLogger(PillController.class);

    private final ReminderService reminderService;

    private final CustomerService customerService;


    @Autowired
    public PillController(ReminderService reminderService, CustomerService customerService) {
        this.reminderService = reminderService;
        this.customerService = customerService;
    }

    @GetMapping("/reminder")
    public String showForm(Model model, HttpSession session) {
        logger.info("Showing reminder form");

        if (session != null) {
            String username = session.getClass().getSimpleName();
            // Now you have the authenticated username, you can retrieve the user details as needed
            // Example: Customer customer = userService.findCustomerByUsername(username);
        } else {
            logger.info("session is null");
            // Handle the case where the principal is null
        }

        model.addAttribute("pillForm", new MedicationScheduleViewModel());
        return "reminder";
    }


    @PostMapping("/reminder")
    public String submitForm(@ModelAttribute("pillForm") @Valid MedicationScheduleViewModel pillForm,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "reminder";
        }


        reminderService.saveMedicationSchedule(pillForm);
        logger.info("Processing " + pillForm.toString());
        return "redirect:reminder";
    }



    @GetMapping(path = "/now", produces = "application/json")
    public @ResponseBody AlarmResponse getCurrentAlarm() {
        String message = reminderService.getMedScheduleAlert();
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


    @GetMapping("/reminder2")
    public String showReminder2() {
        logger.info("Showing reminder2");
        return "reminder2";
    }


    @PostMapping("/reminder2")
    public String processReminder2(@ModelAttribute("pillForm") MedicationScheduleViewModel pillForm) {
        // Logic to process the POST request for /reminder2
        logger.info("Processing " + pillForm.toString());

        // Additional logic if needed

        return "redirect:/reminder2";  // Redirect to the /reminder2 page or another appropriate page
    }

    @PostMapping("/dismissReminder")
    public String dismissReminder() {
        // Perform any necessary actions for dismissal
        logger.info("Reminder dismissed");
        return "redirect:/reminder";  // Redirect back to the reminder page
    }


}

