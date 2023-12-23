package be.kdg.programming3.pillgate.service;

import be.kdg.programming3.pillgate.domain.user.Customer;
import be.kdg.programming3.pillgate.pres.controllers.viewmodels.CustomerLoginDto;
import be.kdg.programming3.pillgate.pres.controllers.viewmodels.CustomerRegistrationDto;
import be.kdg.programming3.pillgate.repo.customerRepo.CustomerRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;


/**
 * Implementation of the {@link CustomerService} interface.
 * This class provides methods to manage customer-related operations.
 *
 * @author Team PillGate
 * @see
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    // Logger for logging information and debugging.
    private final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    // Repository for customer data access.
    CustomerRepository customerRepository;



    // Autowired HttpServletRequest for handling HTTP requests.
    @Autowired
    private HttpServletRequest request;

    /**
     * Constructs a new instance of {@code CustomerServiceImpl}.
     *
     * @param customerRepository The repository for customer data access.
     */

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Creates a new customer.
     *
     * @param customer The customer entity to be created.
     * @return The created customer entity.
     */
    @Override
    public Customer createCustomer(Customer customer) {
        logger.info("Adding a customer with id {} ", customer.getCustomer_id());
        return customerRepository.createCustomer(customer);
    }

    /**
     * Retrieves a list of all customers.
     *
     * @return A list of all customers.
     */

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAllCustomers();
    }

    /**
     * Logs in a customer using the provided login information.
     *
     * @param login The login information provided by the customer.
     * @return The authenticated customer or {@code null} if login fails.
     */
   @Override
    public Customer loginCustomer(CustomerLoginDto login, Model model) {
        String email = login.getEmail();
        String password = login.getPassword();

        Customer authenticatedCustomer = customerRepository.findCustomerByEmail(email, password);

        if (authenticatedCustomer != null) {
            logger.info("Retrieved email from the database: {}", authenticatedCustomer.getEmail());

            String customerPassword = authenticatedCustomer.getPassword();

            if (customerPassword != null && customerPassword.equals(password)) {
                logger.info("Password correctly inputted: {}", password);
            } else {
                logger.info("Incorrect password inputted: {}", password);
                // Handle incorrect password scenario, e.g., set a session attribute
                model.addAttribute("warning", "Incorrect password");
                return null; // Return null to indicate login failure
            }
            HttpSession session = request.getSession(true);
            session.setAttribute("customer", authenticatedCustomer);
            return authenticatedCustomer;
        }
        logger.info("Logging failed. {} does not exist.", email);
        // Handle the case when authenticatedCustomer is null (customer not found)
       model.addAttribute("warning", "User not found");
       return null;
    }



    /**
     * Extracts the email of the authenticated user from the session.
     *
     * @return The email of the authenticated user.
     * @throws IllegalStateException If the user is not authenticated.
     */
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

    /**
     * Registers a new customer and saves information to the session.
     *
     * @param registrationDto The registration information provided by the customer.
     * @param session         The HTTP session to store customer information.
     * @return The newly registered customer.
     */
    @Override
    public Customer registerNewCustomer(CustomerRegistrationDto registrationDto, HttpSession session) {

        Customer newCustomer = new Customer();

        String fullName = registrationDto.getFirstName() + " " + registrationDto.getLastName();
        newCustomer.setCustomer_name(fullName);
        newCustomer.setPassword(registrationDto.getPassword());
        newCustomer.setBirthDate(registrationDto.getBirthDate());
        newCustomer.setEmail(registrationDto.getEmail());
        newCustomer.setHasCareGiver(registrationDto.getHasCareGiver());

        // Save the new customer
        Customer savedCustomer = customerRepository.createCustomer(newCustomer);

        // Add customer information to the session
        if (savedCustomer != null) {
            session.setAttribute("authenticatedUser", savedCustomer);
        }

        return savedCustomer;
    }



}
