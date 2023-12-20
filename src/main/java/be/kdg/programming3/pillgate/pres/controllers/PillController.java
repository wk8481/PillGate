package be.kdg.programming3.pillgate.pres.controllers;

import be.kdg.programming3.pillgate.domain.user.Customer;
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
        model.addAttribute("pillForm", new MedicationScheduleViewModel());
        return "reminder";
    }


    @PostMapping("/reminder")
    public String submitForm(@ModelAttribute("pillForm") MedicationScheduleViewModel pillForm) {
        logger.info("Processing " + pillForm.toString());

        logger.info("saving medicationSchedule....");

        reminderService.saveMedicationSchedule(pillForm);

        return "redirect:reminder";
    }


    @GetMapping(path = "/now", produces = "application/json")
    public @ResponseBody ReminderController.AlarmResponse getCurrentAlarm() {
        String message = reminderService.getMedScheduleAlert();
        return new ReminderController.AlarmResponse(message);


//        logger.info("saving medicationSchedule....");

//        reminderService.saveMedicationSchedule(pillForm);

//        return "redirect:reminder";
        //TODO: FIX THE COMMENTED LINES
    }



}

