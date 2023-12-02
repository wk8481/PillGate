//package be.kdg.programming3.pillgate.repo.userRepo;
//
//import be.kdg.programming3.pillgate.domain.user.Customer;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Repository;
//import jakarta.persistence.PersistenceContext;
//import jakarta.transaction.Transactional;
//
//import jakarta.persistence.EntityManager;
//
//import java.util.List;
//
//@Profile("jpa")
//@Repository
//public class JPAUserRepository implements UserRepository{
//
//    @PersistenceContext
//    private EntityManager em;
//
//    @Override
//    public List<Customer> findAllCustomers() {
//        List<Customer> customer = em.createQuery("select b from Book b").getResultList();
//        return customer;
//    }
//
//    @Override
//    public Customer findCustomerById(int id) {
//        Customer customer = em.find(Customer.class, id);
//        return customer;
//    }
//
//    @Override
//    @Transactional
//    public Customer createCustomer(Customer customer) {
//        em.persist(customer);
//        return customer;
//    }
//
//    @Override
//    @Transactional
//    public Customer updateCustomer(Customer customer) {
//        return em.merge(customer);
//    }
//
//    @Override
//    @Transactional
//    public Customer deleteCustomer(Customer customer) {
//        em.remove(em.contains(customer) ? customer : em.merge(customer));
//        return customer;
//    }
//}
