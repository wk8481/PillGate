package be.kdg.programming3.pillgate.pres.controllers;

import be.kdg.programming3.pillgate.domain.user.Customer;
import be.kdg.programming3.pillgate.pres.controllers.viewmodels.MedicationScheduleViewModel;
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

/**
 * The {@code PillController} class is a Spring MVC controller that handles requests related to medication reminders.
 * It is responsible for displaying the medication Schedule form and processing the submitted medication schedule.
 * @author Team PillGate
 */
@Controller
@RequestMapping("/")
public class PillController {

    private final Logger logger = LoggerFactory.getLogger(PillController.class);
    private final ReminderService reminderService;

    /**
     * Constructs a new instance of {@code PillController}.
     * @param reminderService The service responsible for managing medication reminders.
     */
    @Autowired
    public PillController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    /**
     * Handles HTTP GET requests to display the medication reminder form.
     * @param model   The model to be populated with data for rendering the view.
     * @param session The HttpSession object to check the user's authentication status.
     * @return The view name for the reminder form or a redirect to the login page if not authenticated.
     */
    @GetMapping("/reminder")
    public String showForm(Model model, HttpSession session) {
        Object isLoggedIn = session.getAttribute("isLoggedIn");
        if (isLoggedIn == null || !(isLoggedIn instanceof Boolean) || !(Boolean) isLoggedIn) {
            return "redirect:/login";
        } else {
            logger.info("Showing reminder form");
            model.addAttribute("pillForm", new MedicationScheduleViewModel());
            model.addAttribute("customerName", new Customer().getCustomer_name()); // Pass customer name to the model
            return "reminder";
        }
    }

    /**
     * Handles HTTP POST requests to process the submitted medication schedule form.
     * @param pillForm      The MedicationScheduleViewModel object containing the form data.
     * @param bindingResult The BindingResult for validation errors.
     * @return The view name for the reminder form or a redirect to the reminder page after successful submission.
     */
    @PostMapping("/reminder")
    public String submitForm(@ModelAttribute("pillForm") @Valid MedicationScheduleViewModel pillForm,
                             BindingResult bindingResult) {
        logger.info("Processing " + pillForm.toString());
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors, returning to reminder form");
            bindingResult.getAllErrors().forEach(error -> logger.info(error.toString()));
            return "reminder";
        }

        logger.info("Saving medicationSchedule....");
        reminderService.saveMedicationSchedule(pillForm);

        return "redirect:reminder";
    }
}
