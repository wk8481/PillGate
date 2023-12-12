package be.kdg.programming3.pillgate.service;

import be.kdg.programming3.pillgate.domain.user.Customer;
import be.kdg.programming3.pillgate.pres.controllers.viewmodels.CustomerLoginDto;
import be.kdg.programming3.pillgate.pres.controllers.viewmodels.CustomerRegistrationDto;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface CustomerService {

    Customer createCustomer(Customer customer);
    List<Customer> getCustomers();

    Customer loginCustomer(CustomerLoginDto login);
    String extractEmailFromSession();

    Customer loginCustomer(CustomerLoginDto login, HttpSession session);

    Customer registerNewCustomer(CustomerRegistrationDto registrationDto, HttpSession session);
}
