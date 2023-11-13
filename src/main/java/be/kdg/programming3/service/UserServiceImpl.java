package be.kdg.programming3.service;

import be.kdg.programming3.domain.user.CareGiver;
import be.kdg.programming3.domain.user.Customer;
import be.kdg.programming3.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Customer addCustomer(Customer customer) {
        logger.info("Adding a customer with id {} ", customer.getCustomer_id());
        return userRepository.createCustomer(customer);
    }

    @Override
    public CareGiver createCareGiver(CareGiver careGiver) {
        logger.info("Adding a caregiver with id {}", careGiver.getCaregiver_id());
        return userRepository.createCareGiver(careGiver);
    }

}
