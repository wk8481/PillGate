package be.kdg.programming3.pillgate.service;//package be.kdg.programming3.pillgate.service;
//
//import be.kdg.programming3.pillgate.domain.user.Customer;
//import be.kdg.programming3.pillgate.repo.userRepo.UserRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//
//public class UserServiceImpl implements UserService {
//
//    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
//
//    private UserRepository userRepository;
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
//}
