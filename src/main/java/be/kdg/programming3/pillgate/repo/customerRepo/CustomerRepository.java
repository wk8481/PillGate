package be.kdg.programming3.pillgate.repo.customerRepo;

import be.kdg.programming3.pillgate.domain.user.Customer;

import java.util.List;

/**
 * The {@code CustomerRepository} interface defines methods for interacting with a data store
 * to perform CRUD operations on Customer entities.
 *
 * @author Team PillGate
 * @see Customer
 */
public interface CustomerRepository {

    /**
     * Creates a new Customer in the data store.
     *
     * @param customer The Customer entity to be created.
     * @return The created Customer entity.
     */
    Customer createCustomer(Customer customer);

    /**
     * Retrieves all Customers from the data store.
     *
     * @return A list of Customer entities.
     */
    List<Customer> findAllCustomers();

    /**
     * Retrieves a Customer by its ID from the data store.
     *
     * @param customer_id The ID of the Customer to be retrieved.
     * @return The Customer entity with the specified ID.
     */
    Customer findCustomerById(int customer_id);

    /**
     * Retrieves a Customer by its username from the data store.
     *
     * @param username The username of the Customer to be retrieved.
     * @return The Customer entity with the specified username.
     */
    Customer findCustomerByUsername(String username);

    /**
     * Retrieves a Customer by its email and password from the data store.
     *
     * @param email    The email of the Customer to be retrieved.
     * @param password The password of the Customer to be retrieved.
     * @return The Customer entity with the specified email and password.
     */
    Customer findCustomerByEmail(String email, String password);

    /**
     * Updates an existing Customer in the data store.
     *
     * @param existingCustomer The Customer entity to be updated.
     * @return The updated Customer entity.
     */
    Customer updateCustomer(Customer existingCustomer);

    /**
     * Deletes a Customer from the data store.
     *
     * @param customer The Customer entity to be deleted.
     * @return The deleted Customer entity.
     */
    Customer deleteCustomer(Customer customer);
}
