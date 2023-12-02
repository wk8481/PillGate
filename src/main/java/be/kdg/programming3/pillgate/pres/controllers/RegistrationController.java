//package be.kdg.programming3.pillgate.pres.controllers;//package be.kdg.programming3.oldproj.controllers;
//import be.kdg.programming3.pillgate.pres.controllers.viewmodels.CustomerRegistrationDto;
//import be.kdg.programming3.pillgate.service.UserService;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import org.slf4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@RequestMapping("/")
//@Controller
//public class RegistrationController {
//    Logger logger = org.slf4j.LoggerFactory.getLogger(RegistrationController.class);
//
//    private final UserService userService;
//
//    @Autowired
//    public RegistrationController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping
//    public String showHomeView() {
//        logger.info("Request for home view!");
//
//        return "home";
//    }
//}
//
//    @GetMapping("/registration")
//    public String showRegistration(Model model) {
//        logger.info("Showing registration form");
//        model.addAttribute("customerDTO", new CustomerRegistrationDto());
//        return "registration";
//    }
//
//    @PostMapping("/registration")
//    public String registerCustomer(@ModelAttribute CustomerRegistrationDto registrationDto) {
//        logger.info("Registering customer: " + registrationDto.toString());
//        userService.registerNewCustomer(registrationDto);
//
//        return "redirect:/registration";
//    }
//
//
//
//@RestController
//@RequestMapping("/api/customers")
//public class CustomerController {
//
//    @Autowired
//    private UserService customerService;
//
//    @PostMapping("/register")
//    public ResponseEntity<String> registerCustomer(@RequestBody CustomerRegistrationDto customerRegistrationDto) {
//        // Map properties from CustomerRegistrationDto to Customer
//        Customer customer = new Customer();
//        customer.setCustomer_name(customerRegistrationDto.getFirstName() + " " + customerRegistrationDto.getLastName());
//        customer.setBirthDate(customerRegistrationDto.getBirthDate());
//        customer.setEmail(customerRegistrationDto.getEmail());
//        customer.setHasCareGiver(false); // Set default value, adjust as needed
//        customer.setPassword(customerRegistrationDto.getPassword());
//
//        // Call service method to register the customer
//        customerService.registerNewCustomer(customer);
//
//        return new ResponseEntity<>("Customer registered successfully", HttpStatus.CREATED);
//    }
//}
