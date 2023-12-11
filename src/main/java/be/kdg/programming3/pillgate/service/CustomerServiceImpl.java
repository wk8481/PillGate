package be.kdg.programming3.pillgate.service;

import be.kdg.programming3.pillgate.domain.user.Customer;
import be.kdg.programming3.pillgate.pres.controllers.viewmodels.CustomerLoginDto;
import be.kdg.programming3.pillgate.repo.customerRepo.CustomerRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomerServiceImpl implements CustomerService {

    private final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    CustomerRepository customerRepository;

    @Autowired
    private HttpServletRequest request;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        logger.info("Adding a customer with id {} ", customer.getCustomer_id());
        return customerRepository.createCustomer(customer);
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAllCustomers();
    }

    @Override
    public Customer loginCustomer(CustomerLoginDto login) {
        String username = login.getUsername();
        String password = login.getPassword();

        Customer authenticatedCustomer = customerRepository.findCustomerByEmail(email);

        if (customer != null) {
            logger.info("Retrieved username from the database: {}", customer.getCustomer_name());
            if (customer.getPassword().equals(password)) {
                logger.info("Password correctly inputted: {}", password);
                return customer;
            } else if (!customer.getPassword().equals(password)) {
                logger.info("password incorrectly inputted {}", password);
                HttpSession session = request.getSession(true);
                session.setAttribute("customer", authenticatedCustomer);
                return authenticatedCustomer;
            }
        }


        logger.info("Logging failed. {} does not exist.", email);

        return null;
    }

    @Override
    public String extractEmailFromSession() {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("authenticatedUser") != null) {
            Customer authenticatedUser = (Customer) session.getAttribute("authenticatedUser");
            return authenticatedUser.getEmail();
        } else {
            throw new IllegalStateException("User not authenticated");
        }
    }
}
