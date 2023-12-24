package be.kdg.programming3.pillgate.pres.controllers;

import be.kdg.programming3.pillgate.pres.controllers.viewmodels.CustomerRegistrationDto;
import be.kdg.programming3.pillgate.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * The {@code RegistrationController} class is a Spring MVC controller that handles registration-related requests.
 * It manages the registration process for new customers, including displaying the registration form and processing
 * user input.
 * @author PillGate
 */
@RequestMapping("/")
@Controller
public class RegistrationController {

    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private final CustomerService customerService;

    @Autowired
    private HttpSession session; // Autowire HttpSession

    /**
     * Constructs a new {@code RegistrationController} with the specified {@link CustomerService}.
     * @param customerService The service responsible for customer-related operations.
     */
    @Autowired
    public RegistrationController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Handles HTTP GET requests to display the registration form.
     * @param model The model that holds attributes for the view.
     * @return The view name for the registration form.
     */
    @GetMapping("/registration")
    public String showRegistration(Model model) {
        logger.info("Showing registration form");
        model.addAttribute("customerDTO", new CustomerRegistrationDto());
        return "registration";
    }

    /**
     * Handles HTTP POST requests to process customer registration.
     * @param registrationDto The data transfer object containing customer registration information.
     * @param bindingResult   The result of the validation, containing possible errors.
     * @return The view name for the registration form or a success/confirmation page.
     */
    @PostMapping("/registration")
    public String registerCustomer(@ModelAttribute("customerDTO") @Valid CustomerRegistrationDto registrationDto,
                                   BindingResult bindingResult) {
        logger.info("Registering customer: " + registrationDto.toString());

        if (bindingResult.hasErrors()) {
            logger.info("Validation errors, returning to registration form");
            bindingResult.getAllErrors().forEach(error -> logger.info(error.toString()));
            // If there are validation errors, return to the registration form with error messages
            return "registration";
        }

        customerService.registerNewCustomer(registrationDto, session);

        return "registration"; // Redirect to a success or confirmation page
    }

    /**
     * Handles HTTP GET requests to display the home view.
     * @return The view name for the home view.
     */
    @GetMapping
    public String showHomeView() {
        logger.info("Request for home view!");
        return "home";
    }
}
