package be.kdg.programming3.presentation.controllers;

import be.kdg.programming3.presentation.controllers.viewmodels.MedicationScheduleViewModel;
import be.kdg.programming3.service.ReminderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/")
public class PillController{

    Logger logger = LoggerFactory.getLogger(PillController.class);

    @Autowired
    private final ReminderService reminderService;

    public PillController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @GetMapping("/reminder")
    public String showForm(Model model){
        logger.info("Showing reminder form");
        model.addAttribute("pillForm", new MedicationScheduleViewModel());
        return "reminder";
    }

//    @PostMapping("/reminder")
//    public String scheduleReminder(PillForm pillForm){
//        logger.info("Scheduling reminder for pill: " + pillForm.getPillName());
//        reminderService.scheduleReminder(pillForm);
//        return "redirect:/reminder2";
////    }
//    @PostMapping("/reminder")
//    public String submitForm(@ModelAttribute MedicationSchedule medicationSchedule){
//        logger.info("Scheduling reminder for pill: " + medicationSchedule.getPillName());
//        reminderService.scheduleReminder(medicationSchedule);
//        return "redirect:/reminder";
//    }


    @PostMapping("/reminder")
    public String submitForm(@ModelAttribute("pillForm") MedicationScheduleViewModel pillForm) {
        logger.info("Processing " + pillForm.toString());

        // Validation errors will be automatically handled by Spring MVC

        logger.info("saving medicationSchedule....");
        reminderService.saveMedicationSchedule(pillForm);

        return "redirect:/reminder"; // Redirect to a confirmation page or back to the form
    }

//    private MedicationSchedule convertToMedicationSchedule(PillForm pillForm) {
//        MedicationSchedule medicationSchedule = new MedicationSchedule();
//        medicationSchedule.setPillName(pillForm.getPillName());
//        medicationSchedule.setTimeTakePill(pillForm.getTimeTakePill());
//        medicationSchedule.setRepeatIn(pillForm.getRepeatIn());
//        return medicationSchedule;
//    }

    @GetMapping("/reminder2")
    public String showReminder2() {
        logger.info("Showing reminder2");
        return "reminder2";
    }
//@GetMapping("/reminder2")
//public String showReminder2(Model model) {
//    logger.info("Checking if it's time to show reminder2");
//
//    if (reminderService.checkMedicationSchedule()) {
//        logger.info("Showing reminder2");
//        return "reminder2";
//    } else {
//        logger.info("Not time for reminder2 yet");
//        return "redirect:/reminder";  // Redirect to the form if it's not time for the reminder
//    }
//}

//    @GetMapping("/reminder2")
//    public String showReminder2(Model model) {
//        logger.info("Checking if it's time to show reminder2");
//
//        if (reminderService.isExactTimeForReminder()) {
//            logger.info("Showing reminder2");
//            return "reminder2";
//        } else {
//            logger.info("Not time for reminder2 yet");
//            return "redirect:/reminder";  // Redirect to the form if it's not time for the reminder
//        }
//    }
    //TODO make sure that the reminder2 page is only shown when it's time to take the pill or as popup link to pollReminders


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




//    @GetMapping("/pollReminders")
//    @ResponseBody
//    public String pollReminders() {
//        logger.info("Polling reminders");
//        reminderService.checkMedicationSchedule();
////        if (reminderService.checkMedicationSchedule()) {
////            return "It's time to take your medication";
////        } else {
////            return "No reminders at the moment";
////        }
//        return "redirect:/reminder2";
//    }

//    @GetMapping("/pollReminders")
//    @ResponseBody
//    public Map<String, String> pollReminders(){
//        logger.info("Polling reminders");
//        reminderService.checkMedicationSchedule();
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "It's time to take your medication");
//        return response;
//    }


}