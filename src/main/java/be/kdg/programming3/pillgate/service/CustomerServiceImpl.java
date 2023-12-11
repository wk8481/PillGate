package be.kdg.programming3.pillgate.service;

import be.kdg.programming3.pillgate.domain.user.Customer;
import be.kdg.programming3.pillgate.pres.controllers.viewmodels.CustomerLoginDto;
import be.kdg.programming3.pillgate.repo.customerRepo.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomerServiceImpl implements CustomerService {

    private final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    CustomerRepository customerRepository;

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
        Customer customer = customerRepository.findCustomerByUsername(username);

        if (customer != null) {
            logger.info("Retrieved username from the database: {}", customer.getCustomer_name());
            if (customer.getPassword().equals(password)) {
                logger.info("Password correctly inputted: {}", password);
                return customer;
            } else if (!customer.getPassword().equals(password)) {
                logger.info("password incorrectly inputted {}", password);
            }
        }

        logger.info("Logging failed. {} does not exist.", username);

        // Login failed
        return null;
    }
}
