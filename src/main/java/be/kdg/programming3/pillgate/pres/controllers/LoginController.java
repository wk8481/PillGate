package be.kdg.programming3.pillgate.pres.controllers;

import be.kdg.programming3.pillgate.pres.controllers.viewmodels.CustomerRegistrationDto;
import be.kdg.programming3.pillgate.service.UserService;
import org.slf4j.Logger;
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

    Logger logger = org.slf4j.LoggerFactory.getLogger(be.kdg.programming3.pillgate.pres.controllers.LoginController.class);

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        logger.info("Showing login form");
        model.addAttribute("customerDTO", new CustomerRegistrationDto());
        return "login";
    }

    @PostMapping("/login")
    public String loginCustomer(@ModelAttribute CustomerRegistrationDto registrationDto) {
        logger.info("Logging in customer: " + registrationDto.getFirstName() + registrationDto.getLastName());
        userService.loginCustomer(registrationDto);

        return "redirect:/dashboard";
    }
}
