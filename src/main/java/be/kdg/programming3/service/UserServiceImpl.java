package be.kdg.programming3.service;

import be.kdg.programming3.domain.user.CareGiver;
import be.kdg.programming3.domain.user.Customer;
import be.kdg.programming3.domain.user.MedicationSchedule;
import be.kdg.programming3.model.CustomerRegistrationDto;
import be.kdg.programming3.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    private final PasswordEncryptionServiceImpl passwordEncryptionService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncryptionServiceImpl passwordEncryptionService) {
        this.userRepository = userRepository;
        this.passwordEncryptionService = passwordEncryptionService;
    }

    @Override
    public List<Customer> getCustomerList() {
        return userRepository.readCustomers();
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

    @Override
    public MedicationSchedule createMedSchedule(MedicationSchedule medSchedule) {
        logger.info("Creating a medication schedule with id {}", medSchedule.getMedSchedule_id());
        return userRepository.createMedSchedule(medSchedule);
    }

    @Override
    public Customer registerNewCustomer(CustomerRegistrationDto registrationDto) {
        // TODO: Validate registration data (e.g check for existing username)

        Customer newCustomer = new Customer();
        newCustomer.setCustomer_name(registrationDto.getUsername());
        newCustomer.setPassword(passwordEncryptionService.encrypt(registrationDto.getPassword()));
        newCustomer.setEmail(registrationDto.getEmail());

        return userRepository.createCustomer(newCustomer);
    }

    @Override
    public Customer convertDtoToCustomer(CustomerRegistrationDto registrationDto) {
        Customer customer = new Customer();

        customer.setCustomer_name(registrationDto.getUsername());
        customer.setPassword(registrationDto.getPassword());
        customer.setEmail(registrationDto.getEmail());

        return customer;
    }

}
