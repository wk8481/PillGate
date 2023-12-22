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

@RequestMapping("/")
@Controller
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final CustomerService customerService;

    @Autowired
    public LoginController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        logger.info("Showing login form");
        model.addAttribute("customerDTO", new CustomerLoginDto());
        return "login";
    }

    @PostMapping("/login")
    public String loginCustomer(@ModelAttribute("customerDTO") @Valid CustomerLoginDto loginDto,
                                BindingResult bindingResult, Model model, HttpSession session) {
        logger.info("Processing login request");

        if (session.getAttribute("authenticatedUser") != null) {
            logger.info("User already logged in, redirecting to /reminder");
            return "redirect:/reminder";
        }

        if (bindingResult.hasErrors()) {
            logger.info("Validation errors, returning to login form");
            bindingResult.getAllErrors().forEach(error -> logger.info(error.toString()));

            return "login";
        }

        Customer authenticatedCustomer = customerService.loginCustomer(loginDto);
        if (authenticatedCustomer != null) {
            // Store customer_id in the session
            session.setAttribute("customer_id", authenticatedCustomer.getCustomer_id());
            session.setAttribute("authenticatedUser", authenticatedCustomer);
            logger.info("Login successful. Customer {} authenticated", authenticatedCustomer);
            return "redirect:/reminder";
        } else {
            logger.info("Login failed. Invalid username or password");
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}

