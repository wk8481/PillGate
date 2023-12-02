package be.kdg.programming3.pillgate.service;

import be.kdg.programming3.pillgate.domain.user.CareGiver;
import be.kdg.programming3.pillgate.domain.user.Customer;
import be.kdg.programming3.pillgate.pres.controllers.viewmodels.CustomerRegistrationDto;
import be.kdg.programming3.pillgate.repo.userRepo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    private UserRepository userRepository;
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
    public Customer loginCustomer(CustomerRegistrationDto registrationDto) {
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
