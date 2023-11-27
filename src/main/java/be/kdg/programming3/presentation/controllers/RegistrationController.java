package be.kdg.programming3.presentation.controllers;

import be.kdg.programming3.domain.user.Customer;
import be.kdg.programming3.model.CustomerRegistrationDto;
import be.kdg.programming3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("registration")
    public String showRegistration(Model model) {
        model.addAttribute("customerDTO", new CustomerRegistrationDto());
        return "registration";
    }

    @PostMapping("/register")
    public String registerCustomer(@ModelAttribute CustomerRegistrationDto registrationDto) {
        userService.registerNewCustomer(registrationDto);
        return "redirect:/login";
    }

    public Customer convertDtoToCustomer(CustomerRegistrationDto registrationDto) {
        Customer customer = new Customer();

        customer.setCustomer_name(registrationDto.getUsername());
        customer.setPassword(registrationDto.getPassword());
        customer.setEmail(registrationDto.getEmail());

        return customer;
    }
}
