package be.kdg.programming3.pillgate.repo.customerRepo;

import be.kdg.programming3.pillgate.domain.user.CareGiver;
import be.kdg.programming3.pillgate.domain.user.Customer;
import be.kdg.programming3.pillgate.domain.user.Dashboard;
import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import jakarta.persistence.EntityManager;

import java.util.List;

@Profile("jpa")
@Repository
//@Primary
public class
JPAUserRepository implements UserRepository{

    @PersistenceContext
    private EntityManager em;

    /*Customer methods*/
    @Override
    public List<Customer> findAllCustomers() {
        List<Customer> customer = em.createQuery("SELECT c FROM Customer c").getResultList();
        return customer;
    }

    @Override
    public Customer findCustomerById(int id) {
        Customer customer = em.find(Customer.class, id);
        return customer;
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        String query = "SELECT c FROM Customer c WHERE c.email = :email";
        List<Customer> customers = em.createQuery(query, Customer.class)
                .setParameter("email", email)
                .getResultList();

        return customers.isEmpty() ? null : customers.get(0);
    }

    @Override
    public Customer findCustomerByUsername(String username) {
        try {
            String query = "SELECT c FROM Customer c WHERE c.customer_name = :username";
            TypedQuery<Customer> typedQuery = em.createQuery(query, Customer.class);
            typedQuery.setParameter("username", username);
            return typedQuery.getSingleResult();
        } catch (NoResultException ex) {
            // No user found with the given username
            return null;
        }
    }

    @Override
    @Transactional
    public Customer createCustomer(Customer customer) {
        em.persist(customer);
        return customer;
    }

    @Override
    @Transactional
    public Customer updateCustomer(Customer customer) {
        return em.merge(customer);
    }

    @Override
    @Transactional
    public Customer deleteCustomer(Customer customer) {
        em.remove(em.contains(customer) ? customer : em.merge(customer));
        return customer;
    }

    /*Caregiver methods*/

    @Override
    @Transactional
    public CareGiver createCareGiver(CareGiver careGiver) {
        em.persist(careGiver);
        return careGiver;
    }

    @Override
    public List<CareGiver> findAllCareGivers() {
        return em.createQuery("SELECT c FROM CAREGIVER c", CareGiver.class).getResultList();
    }

    @Override
    public CareGiver findCaregiverByID(int caregiver_id) {
        return em.find(CareGiver.class, caregiver_id);
    }

    @Override
    @Transactional
    public CareGiver updateCareGiver(CareGiver existingCareGiver)  {
        return em.merge(existingCareGiver);
    }

    @Override
    @Transactional
    public CareGiver deleteCaregiver(int caregiver_id) {
        CareGiver careGiver = em.find(CareGiver.class, caregiver_id);
        if (careGiver != null) {
            em.remove(careGiver);
        }
        return careGiver;
    }


    /*Dashboard methods*/
    @Override
    @Transactional
    public Dashboard createDashboard(Dashboard dashboard) {
        em.persist(dashboard);
        return dashboard;
    }

    @Override
    public List<Dashboard> findAllDashboards() {
        return em.createQuery("SELECT d FROM Dashboard d", Dashboard.class).getResultList();
    }

    @Override
    public Dashboard findDashboardByID(int dashboard_ID) {
        return em.find(Dashboard.class, dashboard_ID);
    }

    @Override
    @Transactional
    public Dashboard updateDashboard(Dashboard dashboard) {
        return em.merge(dashboard);
    }

    @Override
    @Transactional
    public Dashboard deleteDashboard(int dashboard_ID) {
        Dashboard dashboard = em.find(Dashboard.class, dashboard_ID);
        if (dashboard != null) {
            em.remove(dashboard);
        }
        return dashboard;
    }

    /*MedicationSchedule methods*/
    @Override
    @Transactional
    public MedicationSchedule createMedSchedule(MedicationSchedule medicationSchedule) {
        em.persist(medicationSchedule);
        return medicationSchedule;
    }

    @Override
    public List<MedicationSchedule> findAllMedSchedules() {
        return em.createQuery("SELECT m FROM MedicationSchedule m", MedicationSchedule.class).getResultList();
    }

    @Override
    public MedicationSchedule findMedScheduleById(int medSchedule_id) {
        return em.find(MedicationSchedule.class, medSchedule_id);
    }

    @Override
    @Transactional
    public MedicationSchedule updateMedSchedule(MedicationSchedule existingMedSchedule) {
        return em.merge(existingMedSchedule);
    }

    @Override
    @Transactional
    public MedicationSchedule deleteMedSchedule(int medicationSchedule_id) {
        MedicationSchedule medicationSchedule = em.find(MedicationSchedule.class, medicationSchedule_id);
        if (medicationSchedule != null) {
            em.remove(medicationSchedule);
        }
        return medicationSchedule;
    }

}
