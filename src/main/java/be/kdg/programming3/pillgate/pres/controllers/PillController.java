package be.kdg.programming3.pillgate.pres.controllers;//package be.kdg.programming3.pillgate.controllers;

import be.kdg.programming3.pillgate.pres.controllers.viewmodels.MedicationScheduleViewModel;
import be.kdg.programming3.pillgate.service.ReminderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequestMapping("/")
public class PillController {

    Logger logger = LoggerFactory.getLogger(PillController.class);

    private final ReminderServiceImpl reminderService;

    @Autowired
    public PillController(ReminderServiceImpl reminderService) {
        this.reminderService = reminderService;
    }

    @GetMapping("/reminder")
    public String showForm(Model model, Principal principal) {
        logger.info("Showing reminder form");

        if (principal != null) {
            String username = principal.getName();
            // Now you have the authenticated username, you can retrieve the user details as needed
            // Example: Customer customer = userService.findCustomerByUsername(username);
        } else {
            logger.info("Principal is null");
            // Handle the case where the principal is null
        }

        model.addAttribute("pillForm", new MedicationScheduleViewModel());
        return "reminder";
    }


    @PostMapping("/reminder")
    public String submitForm(@ModelAttribute("pillForm") MedicationScheduleViewModel pillForm) {
        logger.info("Processing " + pillForm.toString());

        reminderService.saveMedicationSchedule(pillForm);

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

//
//        // Validation errors will be automatically handled by Spring MVC
//        logger.info("Trying to get customerID...");
//      //  int customerID = reminderService.getCustomerID();
//
//        logger.info("saving medicationSchedule....");
//        reminderService.saveMedicationSchedule(pillForm);
//
//        return "redirect:reminder"; // Redirect to a confirmation page or back to the form
////////

//        if (principal == null || principal.getName() == null) {
//            // Handle the case where the principal is null or has a null name
//            logger.info("Principal name is null");
//            return "errorPage == principal is null";

//        }
//
//
//        String username = principal.getName();
//        logger.info("Processing " + pillForm.toString());
//
//        // Validation errors will be automatically handled by Spring MVC
//        logger.info("Trying to get customerID...");
//
//        logger.info("saving medicationSchedule....");
//        reminderService.saveMedicationSchedule(pillForm, username);
//
//        return "redirect:reminder"; // Redirect to a confirmation page or back to the form



////    @GetMapping("/pollReminders")
////    @ResponseBody
////    public String pollReminders() {
////        logger.info("Polling reminders");
////        reminderService.checkMedicationSchedule();
//////        if (reminderService.checkMedicationSchedule()) {
//////            return "It's time to take your medication";
//////        } else {
//////            return "No reminders at the moment";
//////        }
////        return "redirect:/reminder2";
////    }
//
////    @GetMapping("/pollReminders")
////    @ResponseBody
////    public Map<String, String> pollReminders(){
////        logger.info("Polling reminders");
////        reminderService.checkMedicationSchedule();
////        Map<String, String> response = new HashMap<>();
////        response.put("message", "It's time to take your medication");
////        return response;
////    }
//
//
//}