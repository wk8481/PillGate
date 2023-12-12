package be.kdg.programming3.pillgate.pres.controllers;//package be.kdg.programming3.oldproj.controllers;
import be.kdg.programming3.pillgate.domain.user.Customer;
import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;
import be.kdg.programming3.pillgate.pres.controllers.viewmodels.MedicationScheduleViewModel;
import be.kdg.programming3.pillgate.repo.customerRepo.CustomerRepository;
import be.kdg.programming3.pillgate.repo.medSchedRepo.MedScheduleRepository;
import be.kdg.programming3.pillgate.service.ReminderService;
import be.kdg.programming3.pillgate.service.ReminderServiceImpl;
import be.kdg.programming3.pillgate.service.SerialReader;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.Serial;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ReminderController {

    private MedScheduleRepository medScheduleRepository;
    private CustomerRepository customerRepository;
    private ReminderService reminderService;
    private SerialReader sensorservice;

    private final Logger logger = LoggerFactory.getLogger(ReminderController.class);

    @Autowired
    public ReminderController(CustomerRepository customerRepository, ReminderService reminderService) {
        this.customerRepository = customerRepository;
        this.reminderService =  reminderService;

    }

    @GetMapping("/checkreminder")
    public ResponseEntity<String> checkReminder() {
        logger.info("Checking if it is time for a reminder");
        if (reminderService.isTimeForReminder()) {
            return ResponseEntity.ok("true");
        } else {
            return ResponseEntity.ok("false");
        }
    }




    @GetMapping("/reminder")
    public String showMedicationSchedule(Model model, HttpSession session) {
        // Retrieve the logged-in user (customer)
        Customer customer = customerRepository.findCustomerByUsername(session.getId());

        // Retrieve the customer's medication schedule
        List<MedicationSchedule> medicationSchedule = customer.getDashboard().getMedicationSchedules();

        // Pass the medication schedule to the template
        model.addAttribute("medicationSchedule", medicationSchedule);

        return "reminder";
    }
}

