
package be.kdg.programming3.pillgate.pres.controllers;
import be.kdg.programming3.pillgate.pres.controllers.viewmodels.CustomerRegistrationDto;
import be.kdg.programming3.pillgate.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/")
@Controller
public class RegistrationController {
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(RegistrationController.class);

    private final CustomerService customerService;

    @Autowired
    private HttpSession session; // Autowire HttpSession

    @Autowired
    public RegistrationController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/registration")
    public String showRegistration(Model model) {
        logger.info("Showing registration form");
        model.addAttribute("customerDTO", new CustomerRegistrationDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerCustomer(@ModelAttribute("customerDTO") CustomerRegistrationDto registrationDto) {
        logger.info("Registering customer: " + registrationDto.toString());
        customerService.registerNewCustomer(registrationDto, session); // Use the method with HttpSession

        return "registration"; // Redirect to a success or confirmation page
    }

    @GetMapping
    public String showHomeView() {
        logger.info("Request for home view!");
        return "home";
    }
}

