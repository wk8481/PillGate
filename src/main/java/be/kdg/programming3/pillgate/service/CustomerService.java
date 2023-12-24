package be.kdg.programming3.pillgate.service;

import be.kdg.programming3.pillgate.domain.user.Customer;
import be.kdg.programming3.pillgate.pres.controllers.viewmodels.CustomerLoginDto;
import be.kdg.programming3.pillgate.pres.controllers.viewmodels.CustomerRegistrationDto;
import be.kdg.programming3.pillgate.repo.customerRepo.CustomerRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

import java.util.List;

/**
 * Service interface for managing customers.
 * This service defines methods to handle customer-related operations.
 *
 * @author Team PillGate
 * @see CustomerRepository
 * @see Customer
 */
public interface CustomerService {

    /**
     * Creates a new customer.
     *
     * @param customer The customer entity to be created.
     * @return The created customer entity.
     */
    Customer createCustomer(Customer customer);

    /**
     * Retrieves a list of all customers.
     *
     * @return A list of customer entities.
     */
    List<Customer> getCustomers();

    /**
     * Authenticates a customer based on login credentials.
     *
     * @param login The login credentials provided by the user.
     * @return The authenticated customer entity, or null if authentication fails.
     */
    Customer loginCustomer(CustomerLoginDto login, Model model);

    /**
     * Extracts the email address of the currently authenticated customer from the session.
     *
     * @return The email address of the authenticated customer.
     */
    String extractEmailFromSession();

    /**
     * Registers a new customer.
     *
     * @param registrationDto The registration details provided by the user.
     * @param session         The HTTP session.
     * @return The registered customer entity.
     */
    Customer registerNewCustomer(CustomerRegistrationDto registrationDto, HttpSession session);

}
