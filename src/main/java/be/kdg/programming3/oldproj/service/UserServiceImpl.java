//package be.kdg.programming3.oldproj.service;
//
//import be.kdg.programming3.oldproj.domain.user.CareGiver;
//import be.kdg.programming3.oldproj.domain.user.Customer;
//import be.kdg.programming3.oldproj.domain.user.MedicationSchedule;
//import be.kdg.programming3.oldproj.controllers.viewmodels.CustomerRegistrationDto;
//import be.kdg.programming3.oldproj.repository.UserRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class UserServiceImpl implements UserService {
//    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
//
//    private UserRepository userRepository;
//    private final PasswordEncryptionServiceImpl passwordEncryptionService;
//
//    public UserServiceImpl(UserRepository userRepository, PasswordEncryptionServiceImpl passwordEncryptionService) {
//        this.userRepository = userRepository;
//        this.passwordEncryptionService = passwordEncryptionService;
//    }
//
//    @Override
//    public Customer addCustomer(Customer customer) {
//        logger.info("Adding a customer with id {} ", customer.getCustomer_id());
//        return userRepository.createCustomer(customer);
//    }
//
//    @Override
//    public List<Customer> getCustomers() {
//        return userRepository.findAllCustomers();
//    }
//
//    @Override
//    public CareGiver createCareGiver(CareGiver careGiver) {
//        logger.info("Adding a caregiver with id {}", careGiver.getCaregiver_id());
//        return userRepository.createCareGiver(careGiver);
//    }
//
//    @Override
//    public List<CareGiver> getCaregivers() {
//        return userRepository.findAllCareGivers();
//    }
//    @Override
//    public MedicationSchedule createMedSchedule(MedicationSchedule medSchedule) {
//        logger.info("Creating a medication schedule with id {}", medSchedule.getMedSchedule_id());
//        return userRepository.createMedSchedule(medSchedule);
//    }
//
//    @Override
//    public Customer registerNewCustomer(CustomerRegistrationDto registrationDto) {
//        // TODO: Validate registration data (e.g check for existing username)
//        if (userRepository.findCustomerByEmail(registrationDto.getEmail()) != null) {
//            throw new RuntimeException("Email is already registered");
//        }
//
//        Customer newCustomer = new Customer();
//        newCustomer.setCustomer_name(registrationDto.getUsername());
//        newCustomer.setPassword(passwordEncryptionService.encrypt(registrationDto.getPassword()));
//        newCustomer.setEmail(registrationDto.getEmail());
//
//        return userRepository.createCustomer(newCustomer);
//    }
//
//    @Override
//    public Customer convertDtoToCustomer(CustomerRegistrationDto registrationDto) {
//        Customer customer = new Customer();
//
//        customer.setCustomer_name(registrationDto.getUsername());
//        customer.setPassword(registrationDto.getPassword());
//        customer.setEmail(registrationDto.getEmail());
//
//        return customer;
//    }
//
//    public ResponseEntity<String> registerCustomerFromDto(CustomerRegistrationDto customerRegistrationDto) {
//        // Map properties from CustomerRegistrationDto to Customer
//        Customer customer = new Customer();
//        customer.setCustomer_name(customerRegistrationDto.getFirstName() + " " + customerRegistrationDto.getLastName());
//        customer.setBirthDate(customerRegistrationDto.getBirthDate());
//        customer.setEmail(customerRegistrationDto.getEmail());
//        customer.setHasCareGiver(false); // Set default value, adjust as needed
//        customer.setPassword(passwordEncryptionService.encrypt(customerRegistrationDto.getPassword()));
//
//        // Call service method to register the customer
//        Customer registeredCustomer = userRepository.createCustomer(customer);
//
//        if (registeredCustomer != null) {
//            return new ResponseEntity<>("Customer registered successfully", HttpStatus.CREATED);
//        } else {
//            return new ResponseEntity<>("Failed to register customer", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//}
