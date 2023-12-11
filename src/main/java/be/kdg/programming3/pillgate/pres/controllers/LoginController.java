package be.kdg.programming3.pillgate.pres.controllers;

import be.kdg.programming3.pillgate.domain.user.Customer;
import be.kdg.programming3.pillgate.pres.controllers.viewmodels.CustomerLoginDto;
import be.kdg.programming3.pillgate.pres.controllers.viewmodels.CustomerRegistrationDto;
import be.kdg.programming3.pillgate.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

 /*   @PostMapping("/login")
    public String loginCustomer(@ModelAttribute CustomerLoginDto loginDto, Model model, HttpSession session) {


        Customer authenticatedUser = customerService.loginCustomer(loginDto);

        if (authenticatedUser != null) {
            session.setAttribute("authenticatedUser", authenticatedUser);

            model.addAttribute("message", "Login successful");
            logger.info("Login successful. User {} authenticated", authenticatedUser);
            return "redirect:/reminder";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }

    }*/

    @PostMapping("/login")
    public String loginCustomer(@ModelAttribute CustomerLoginDto loginDto, Model model, HttpSession session) {
        if (session.getAttribute("authenticatedUser") != null) {
            // User already logged in
            return "redirect:/dashboard";
        }
        Customer authenticatedUser = customerService.loginCustomer(loginDto);
        if (authenticatedUser != null) {
            session.setAttribute("authenticatedUser", authenticatedUser);
            logger.info("Login successful. User {} authenticated", authenticatedUser);
            return "redirect:/reminder";
        } else {
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

