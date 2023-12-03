package be.kdg.programming3.pillgate.service;

import be.kdg.programming3.pillgate.domain.user.CareGiver;
import be.kdg.programming3.pillgate.domain.user.Customer;
import be.kdg.programming3.pillgate.pres.controllers.viewmodels.CustomerLoginDto;
import be.kdg.programming3.pillgate.pres.controllers.viewmodels.CustomerRegistrationDto;
import be.kdg.programming3.pillgate.repo.userRepo.JPAUserRepository;
import be.kdg.programming3.pillgate.repo.userRepo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncryptionService passwordEncoder;

    @Override
    public Customer createCustomer(Customer customer) {
        logger.info("Adding a customer with id {} ", customer.getCustomer_id());
        return userRepository.createCustomer(customer);
    }

    @Override
    public List<Customer> getCustomers() {
        return userRepository.findAllCustomers();
    }

    @Override
    public Customer loginCustomer(CustomerLoginDto login) {
        String username = login.getUsername();
        String password = login.getPassword();
        Customer customer = userRepository.findCustomerByUsername(username);

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

    @Override
    public List<CareGiver> getCaregivers() {
        return userRepository.findAllCareGivers();
    }

    @Override
    public CareGiver createCareGiver(CareGiver careGiver) {
        return userRepository.createCareGiver(careGiver);
    }

}
