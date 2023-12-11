package be.kdg.programming3.pillgate.repo.customerRepo;

import be.kdg.programming3.pillgate.domain.user.Customer;

import java.util.List;

public interface CustomerRepository {


    Customer createCustomer(Customer customer);
    List<Customer> findAllCustomers();
    Customer findCustomerById(int customer_id);
    Customer findCustomerByUsername(String username);
    Customer findCustomerByEmail(String email);
    Customer updateCustomer(Customer existingCustomer);
    Customer deleteCustomer(Customer customer);
}
