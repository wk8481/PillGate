package be.kdg.programming3.pillgate.pres.controllers;//package be.kdg.programming3.oldproj.controllers;

import be.kdg.programming3.pillgate.domain.user.Customer;
import be.kdg.programming3.pillgate.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/")
public class CustomerController {

    private final CustomerService customerService;

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

 @GetMapping("/customer")
    public String findAllCustomers(Model model, HttpSession session) {
        List<Customer> customers = customerService.getCustomers();
        model.addAttribute("customers", customers);
        log.info("Customers: {}", customers);
        return "customer";
    }

    @GetMapping("/customers/addCustomer")
    public String addCustomer(Customer customer) {
        log.debug("Adding a customer: " + customer);
        customerService.createCustomer(customer);
        return "redirect:customer";
    }



}
