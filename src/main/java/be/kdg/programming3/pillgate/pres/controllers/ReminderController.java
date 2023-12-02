package be.kdg.programming3.pillgate.pres.controllers;//package be.kdg.programming3.oldproj.controllers;
import be.kdg.programming3.pillgate.domain.user.Customer;
import be.kdg.programming3.pillgate.pres.controllers.viewmodels.MedicationScheduleViewModel;
import be.kdg.programming3.pillgate.repo.userRepo.JDBCUserRepository;
import be.kdg.programming3.pillgate.repo.userRepo.UserRepository;
import be.kdg.programming3.pillgate.service.ReminderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/api")
public class ReminderController {

    private final UserRepository userRepository;
    private ReminderService reminderService;
    private Logger logger = LoggerFactory.getLogger(ReminderController.class);

    @Autowired
    public ReminderController(UserRepository userRepository, ReminderService reminderService) {
        this.userRepository = userRepository;
        this.reminderService = reminderService;
    }


    @GetMapping("/checkreminder")
    public ResponseEntity<String> checkReminder(){
        logger.info("Checking if it is time for a reminder");
        if (reminderService.isTimeForReminder()){
            return ResponseEntity.ok("true");
        }else{
            return ResponseEntity.ok("false");
        }
    }

    @PostMapping("/reminder")
    public String handleReminderForm(@ModelAttribute("pillForm") MedicationScheduleViewModel pillForm, Principal principal) {
        // Assuming the username is the email or a unique identifier that maps to customer_id
        String username = principal.getName();
        Customer customer = userRepository.findCustomerByEmail(username);

        if (customer != null) {
            pillForm.setCustomer_id(customer.getCustomer_id());
        } else {
            // Handle the case where the customer is not found
            return "errorPage"; // Redirect to an error page or handle appropriately
        }

        reminderService.saveMedicationSchedule(pillForm);
        return "redirect:/successPage"; // Redirect to a success or confirmation page
    }
}

