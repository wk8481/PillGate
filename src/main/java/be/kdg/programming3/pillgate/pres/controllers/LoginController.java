package be.kdg.programming3.pillgate.pres.controllers;

import be.kdg.programming3.pillgate.domain.user.Customer;
import be.kdg.programming3.pillgate.pres.controllers.viewmodels.CustomerLoginDto;
import be.kdg.programming3.pillgate.pres.controllers.viewmodels.CustomerRegistrationDto;
import be.kdg.programming3.pillgate.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The {@code LoginController} class is a Spring MVC controller that handles requests related to user authentication.
 * It provides endpoints for displaying the login form, processing user login, and logging out.
 */
@RequestMapping("/")
@Controller
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final CustomerService customerService;

    /**
     * Constructs a new {@code LoginController} with the specified customer service.
     *
     * @param customerService The service responsible for customer-related operations.
     */
    @Autowired
    public LoginController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Displays the login form.
     *
     * @param model The model to which attributes are added for rendering the view.
     * @return The logical view name for the login form.
     */
    @GetMapping("/login")
    public String showLogin(Model model) {
        logger.info("Showing login form");
        model.addAttribute("customerDTO", new CustomerLoginDto());
        return "login";
    }

    /**
     * Processes user login and redirects to the appropriate page.
     *
     * @param loginDto       The data transfer object containing login credentials.
     * @param bindingResult  The result of the validation.
     * @param model          The model to which attributes are added for rendering the view.
     * @param session        The HTTP session to store user-related attributes.
     * @return The logical view name for the next page based on the login result.
     */
    @PostMapping("/login")
    public String loginCustomer(@ModelAttribute("customerDTO") @Valid CustomerLoginDto loginDto,
                                BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors, returning to reminder form");
            bindingResult.getAllErrors().forEach(error -> logger.info(error.toString()));
            return "login";
        }

        Customer authenticatedCustomer = customerService.loginCustomer(loginDto);
        if (authenticatedCustomer != null) {
            session.setAttribute("customer_id", authenticatedCustomer.getCustomer_id());
            session.setAttribute("authenticatedUser", authenticatedCustomer);
            session.setAttribute("isLoggedIn", true);

            logger.info("Login successful. Customer {} authenticated", authenticatedCustomer);
            return "redirect:/reminder";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    /**
     * Logs out the user by invalidating the session.
     *
     * @param session The HTTP session to be invalidated.
     * @return The logical view name for redirecting to the home page.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/home";
    }
}
