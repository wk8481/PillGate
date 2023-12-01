package be.kdg.programming3.pillgate.pres.controllers;//package be.kdg.programming3.presentation.controllers;
//
//import be.kdg.programming3.domain.user.CareGiver;
//import be.kdg.programming3.domain.user.Customer;
//import be.kdg.programming3.service.UserService;
//import jakarta.servlet.http.HttpSession;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//
//
//@Controller
//@RequestMapping("/")
//public class UserController {
//
//    private final UserService userService;
//
//    private static final Logger log = LoggerFactory.getLogger(UserController.class);
//
//    @Autowired
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping("/customers")
//    public String findAllCustomers(Model model, HttpSession session) {
//        List<Customer> customers = userService.getCustomers();
//        model.addAttribute("customers", customers);
//        log.info("Customers: {}", customers);
//        return "customers";
//    }
//
//    @GetMapping("/customers/addCustomer")
//    public String addCustomer(Customer customer) {
//        log.debug("Adding a customer: " + customer);
//        userService.addCustomer(customer);
//        return "redirect:customers";
//    }
//
//
//    @GetMapping("/caregivers")
//    public String findAllCaregivers(Model model, HttpSession session) {
//        List<CareGiver> careGivers = userService.getCaregivers();
//        model.addAttribute("caregivers", careGivers);
//        log.info("Caregivers: {} ", careGivers);
//        return "caregivers";
//    }
//    @GetMapping("/caregivers/addCaregiver")
//    public String addCaregiver(CareGiver careGiver) {
//        log.info("Adding a caregiver: " + careGiver);
//        userService.createCareGiver(careGiver);
//        return "redirect:caregivers";
//    }
//
//}
