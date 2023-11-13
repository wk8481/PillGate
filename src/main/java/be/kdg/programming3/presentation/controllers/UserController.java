package be.kdg.programming3.presentation.controllers;

import be.kdg.programming3.domain.user.Customer;
import be.kdg.programming3.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class UserController {

    private final UserService userService;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*public String showUsers() {
    }*/ // to implement later

    public String addUser(Customer customer) {
        log.debug("Adding a customer: " + customer);
        userService.addCustomer(customer);
        return "redirect:customers";
    }

    /*
    public String addUserForm() {
    }*/ // to implement later
}
