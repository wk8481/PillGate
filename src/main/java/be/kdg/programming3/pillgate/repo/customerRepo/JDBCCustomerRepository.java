package be.kdg.programming3.pillgate.repo.customerRepo;

import be.kdg.programming3.pillgate.domain.user.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@Profile("jdbctemplate")
@Primary
public class JDBCCustomerRepository implements CustomerRepository {

    private static Logger logger = LoggerFactory.getLogger(JDBCCustomerRepository.class);
    private static List<Customer> customers = new ArrayList<>();

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert customerInserter;

    public JDBCCustomerRepository(JdbcTemplate jdbcTemplate) {
        logger.info("Setting up the customer repository...");
        this.jdbcTemplate = jdbcTemplate;
        this.customerInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Customer")
                .usingGeneratedKeyColumns("customer_id");
    }

    private RowMapper<Customer> customerRowMapper() {
        logger.info("Setting up the customer row mapper...");
        return (rs, rowNum) -> new Customer(
                rs.getInt("customer_id"),
                rs.getString("customer_name"),
                rs.getDate("birthDate").toLocalDate(),
                rs.getString("email"),
                rs.getBoolean("hasCareGiver"),
                rs.getString("password")
        );
    }

    @Override
    public Customer createCustomer(Customer customer) {
        logger.info("Creating customer {}", customer);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customer_id", customer.getCustomer_id());
        parameters.put("customer_name", customer.getCustomer_name());
        parameters.put("birthDate", customer.getBirthDate());
        parameters.put("email", customer.getEmail());
        parameters.put("hasCareGiver", customer.getCareGivers());
        customer.setCustomer_id(customerInserter.executeAndReturnKey(parameters).intValue());
        logger.info("Creating customer {}", customer);

        return customer;
    }

    @Override
    public List<Customer> findAllCustomers() {
        logger.info("Finding all customers...");
        return jdbcTemplate.query("SELECT * FROM Customer", customerRowMapper());
    }

 /*   @Override
    public Customer findCustomerById(int customer_id) {
        logger.info("Finding customer by id {} ", customer_id);
        return (Customer) jdbcTemplate.query("SELECT * FROM Customer where customer_id = ? ",
                customerRowMapper(), customer_id);
    }*/

    public Customer findCustomerById(int customer_id) {
        logger.info("Finding customer by id {} ", customer_id);
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Customer where customer_id = ?",
                    customerRowMapper(),
                    customer_id
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        logger.info("Finding customers by email: {}", email);
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Customer WHERE email = ?",
                    new Object[]{email},
                    customerRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null; // No customer found with the given email
        }
    }

    @Override
    public Customer findCustomerByUsername(String username) {
        logger.info("Finding customers by username: {}", username);
        String query = "SELECT * FROM Customer WHERE customer_name = ?";

        try {
            return jdbcTemplate.queryForObject(query, new Object[]{username}, new BeanPropertyRowMapper<>(Customer.class));
        } catch (Exception e) {
            // No user found with the given username
            return null;
        }
    }

    @Override
    public Customer updateCustomer(Customer existingCustomer) {
        logger.info("Updating customer: {}", existingCustomer);
        String updateQuery = "UPDATE Customer SET customer_name = ?, birthDate = ?, email = ?, hasCareGiver = ?, password = ? WHERE customer_id = ?";

        int updatedRows = jdbcTemplate.update(updateQuery,
                existingCustomer.getCustomer_name(),
                existingCustomer.getBirthDate(),
                existingCustomer.getEmail(),
                existingCustomer.isHasCareGiver(),
                existingCustomer.getCustomer_id(),
                existingCustomer.getPassword());

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
        Objects.requireNonNull(customer, "Customer must not be null");
        String deleteQuery = "DELETE FROM Customer WHERE customer_id = ?";

        int deletedRows = jdbcTemplate.update(deleteQuery, customer.getCustomer_id());

        if (deletedRows > 0) {
            logger.info("Customer with ID {} deleted successfully", customer.getCustomer_id());
            return customer;
        } else {
            logger.warn("Failed to delete customer with ID: {}", customer.getCustomer_id());
            return null;
        }
    }
}
