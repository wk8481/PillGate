package be.kdg.programming3.presentation.controllers;

import be.kdg.programming3.domain.user.Customer;
import be.kdg.programming3.presentation.controllers.viewmodels.CustomerRegistrationDto;
import be.kdg.programming3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/home")
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
}


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
