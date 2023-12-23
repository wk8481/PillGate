package be.kdg.programming3.pillgate.repo.customerRepo;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import be.kdg.programming3.pillgate.domain.user.Customer;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The {@code PGCustomerRepository} class is an implementation of the {@link CustomerRepository} interface
 * that interacts with a PostgreSQL database. It provides methods to perform CRUD operations on the Customer entities.
 *
 * <p>This class is part of the PillGate application developed by Team PillGate.</p>
 *
 * @author Team PillGate
 * @see CustomerRepository
 * @see Customer
 */
@Repository
@Profile("postgres")
@Primary
public class PGCustomerRepository implements CustomerRepository {

    private static Logger logger = LoggerFactory.getLogger(PGCustomerRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Constructs a new {@code PGCustomerRepository} with the specified {@link NamedParameterJdbcTemplate}.
     *
     * @param jdbcTemplate The {@link NamedParameterJdbcTemplate} used to interact with the PostgreSQL database.
     */
    public PGCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        logger.info("Setting up the PostgreSQL customer repository...");
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public Customer createCustomer(Customer customer) {
        logger.info("Creating customer {}", customer);
        String insertQuery = "INSERT INTO customer (customer_name, birthDate, email, hasCareGiver, password) " +
                "VALUES (:customer_name, :birthDate, :email, :hasCareGiver, :password)";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customer_name", customer.getCustomer_name());
        parameters.put("birthDate", customer.getBirthDate());
        parameters.put("email", customer.getEmail());
        parameters.put("hasCareGiver", customer.isHasCareGiver());
        parameters.put("password", customer.getPassword());

        int customerId = jdbcTemplate.update(insertQuery, parameters);
        customer.setCustomer_id(customerId);
        logger.info("Customer created: {}", customer);

        return customer;
    }

    @Override
    public List<Customer> findAllCustomers() {
        logger.info("Finding all customers...");
        String selectQuery = "SELECT * FROM customer";
        return jdbcTemplate.query(selectQuery, new CustomerRowMapper());
    }

    @Override
    public Customer findCustomerById(int customer_id) {
        logger.info("Finding customer by id {}", customer_id);
        String selectQuery = "SELECT * FROM customer WHERE customer_id = :customer_id";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customer_id", customer_id);

        return jdbcTemplate.queryForObject(selectQuery, parameters, new CustomerRowMapper());
    }

    @Override
    public Customer findCustomerByEmail(String email, String password) {
        logger.info("Finding customer by email: {}", email);
        String selectQuery = "SELECT * FROM customer WHERE email = :email and password = :password";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("email", email);
        parameters.put("password", password);

        return jdbcTemplate.queryForObject(selectQuery, parameters, new CustomerRowMapper());
    }

    @Override
    public Customer findCustomerByUsername(String username) {
        logger.info("Finding customers by username: {}", username);
        String selectQuery = "SELECT * FROM customer WHERE customer_name = :customer_name";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customer_name", username);

        return jdbcTemplate.queryForObject(selectQuery, parameters, new CustomerRowMapper());
    }

    @Override
    public Customer updateCustomer(Customer existingCustomer) {
        logger.info("Updating customer: {}", existingCustomer);
        String updateQuery = "UPDATE customer SET customer_name = :customer_name, birthDate = :birthDate, " +
                "email = :email, hasCareGiver = :hasCareGiver, password = :password WHERE customer_id = :customer_id";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customer_name", existingCustomer.getCustomer_name());
        parameters.put("birthDate", existingCustomer.getBirthDate());
        parameters.put("email", existingCustomer.getEmail());
        parameters.put("hasCareGiver", existingCustomer.isHasCareGiver());
        parameters.put("password", existingCustomer.getPassword());
        parameters.put("customer_id", existingCustomer.getCustomer_id());

        int updatedRows = jdbcTemplate.update(updateQuery, parameters);

        if (updatedRows > 0) {
            logger.info("Customer updated: {}", existingCustomer);
            return existingCustomer;
        } else {
            logger.warn("Failed to update customer with ID: {}", existingCustomer.getCustomer_id());
            return null;
        }
    }

    @Override
    public Customer deleteCustomer(Customer customer) {
        logger.info("Deleting customer: {}", customer);
        String deleteQuery = "DELETE FROM customer WHERE customer_id = :customer_id";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customer_id", customer.getCustomer_id());

        int deletedRows = jdbcTemplate.update(deleteQuery, parameters);

        if (deletedRows > 0) {
            logger.info("Customer with ID {} deleted successfully", customer.getCustomer_id());
            return customer;
        } else {
            logger.warn("Failed to delete customer with ID: {}", customer.getCustomer_id());
            return null;
        }
    }

    // Inner class to map rows to Customer objects
    private static class CustomerRowMapper implements org.springframework.jdbc.core.RowMapper<Customer> {
        @Override
        public Customer mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
            return new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("customer_name"),
                    rs.getDate("birthDate").toLocalDate(),
                    rs.getString("email"),
                    rs.getBoolean("hasCareGiver"),
                    rs.getString("password")
            );
        }
    }
}
