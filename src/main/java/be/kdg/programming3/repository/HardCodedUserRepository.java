package be.kdg.programming3.repository;

import be.kdg.programming3.domain.user.Customer;
import be.kdg.programming3.domain.user.CareGiver;
import be.kdg.programming3.domain.user.Dashboard;
import be.kdg.programming3.domain.user.MedicationSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@Profile("list")
public class HardCodedUserRepository implements UserRepository {

    private Logger logger = LoggerFactory.getLogger(HardCodedUserRepository.class);
    private static List<Customer> customers = new ArrayList<>();
    private static List<CareGiver> careGivers = new ArrayList<>();
    private static List<Dashboard> dashboards = new ArrayList<>();
    private static List<MedicationSchedule> medSchedules = new ArrayList<>();
    private static AtomicInteger nextId = new AtomicInteger(1);


    @Override
    public Customer createCustomer(Customer customer) {
        if (customer == null) {
            logger.error("Customer should never be null");
            return null;
        }
        logger.info("Creating customer {}", customer);
        customer.setCustomer_id(nextId.getAndIncrement());
        customers.add(customer);
        return customer;
    }

    @Override
    public List<Customer> findAllCustomers() {
        logger.info("Reading pillboxes from database...");
        return customers;
    }

    @Override
    public Customer findCustomerById(int customer_id) {
        return customers.stream()
                .filter(customer -> customer.getCustomer_id() == customer_id)
                .findFirst()
                .orElse(null);
    }

    /**
     * @param email
     * @return
     */
    @Override
    public Customer findCustomerByEmail(String email) {
        return null;
    }


    public Optional<Customer> findByUsername(String username) {
        return customers.stream()
                .filter(c -> c.getCustomer_name().equals(username))
                .findFirst();
    }

    @Override
    public Customer updateCustomer(Customer existingCustomer) {
        int index = -1;
        for (int i=0; i < customers.size(); i++) {
            if(customers.get(i).getCustomer_id() == existingCustomer.getCustomer_id()) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            customers.set(index, existingCustomer);
            return existingCustomer;
        } else {
            return null;
        }
    }

    /**
     * @param customer_id
     * @return
     */
    @Override
    public Customer deleteCustomer(int customer_id) {
        return null;
    }

    @Override
    public CareGiver createCareGiver(CareGiver careGiver) {
        if (careGiver == null) {
            logger.error("Caregiver should never be null");
            return null;
        }
        logger.info("Creating caregiver {}", careGiver);
        careGiver.setCaregiver_id(nextId.getAndIncrement());
        careGivers.add(careGiver);
        return careGiver;
    }



    @Override
    public List<CareGiver> findAllCareGivers() {
        logger.info("Reading caregiver from database...");
        return careGivers;
    }

    @Override
    public CareGiver findCaregiverByID(int caregiver_id) {
        return careGivers.stream()
                .filter(careGiver -> careGiver.getCaregiver_id() == caregiver_id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public CareGiver updateCareGiver(CareGiver existingCareGiver) {
        int index = -1;
        for (int i=0; i < careGivers.size(); i++) {
            if(careGivers.get(i).getCaregiver_id() == existingCareGiver.getCaregiver_id()) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            careGivers.set(index, existingCareGiver);
            return existingCareGiver;
        } else {
            return null;
        }
    }

    /**
     * @param caregiver_id
     * @return
     */
    @Override
    public CareGiver deleteCaregiver(int caregiver_id) {
        return null;
    }

    @Override
    public Dashboard createDashboard(Dashboard dashboard) {
        if (dashboard == null) {
            logger.error("Dashboard should never be null!");
            return null;
        }
        logger.info("Creating dashboard {}", dashboard);
        dashboard.setDashboard_id(nextId.getAndIncrement());
        dashboards.add(dashboard);
        return dashboard;
    }

    /**
     * @return
     */



    @Override
    public List<Dashboard> findAllDashboards() {
        logger.info("Reading dashboard from database...");
        return dashboards;
    }

    @Override
    public Dashboard findDashboardByID(int dashboard_id) {
        return dashboards.stream()
                .filter(dashboard -> dashboard.getDashboard_id() == dashboard_id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Dashboard updateDashboard(Dashboard existingDashboard) {
        int index = -1;
        for (int i=0; i < dashboards.size(); i++) {
            if(dashboards.get(i).getDashboard_id() == existingDashboard.getDashboard_id()) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            dashboards.set(index, existingDashboard);
            return existingDashboard;
        } else {
            return null;
        }
    }

    /**
     * @param dashboard_ID
     * @return
     */
    @Override
    public Dashboard deleteDashboard(int dashboard_ID) {
        return null;
    }

    @Override
    public MedicationSchedule createMedSchedule(MedicationSchedule medicationSchedule) {
        if (medicationSchedule == null) {
            logger.error("Medication schedule should never be null!");
            return null;
        }
        logger.info("Creating medication schedule {}", medicationSchedule);
        medicationSchedule.setMedSchedule_id(nextId.getAndIncrement());
        medSchedules.add(medicationSchedule);
        return medicationSchedule;
    }





    @Override
    public List<MedicationSchedule> findAllMedSchedules() {
        logger.info("Reading med schedule from database...");
        return medSchedules;
    }

    @Override
    public MedicationSchedule findMedScheduleById(int medicationSchedule_id) {
        return medSchedules.stream()
                .filter(medicationSchedule -> medicationSchedule.getMedSchedule_id() == medicationSchedule_id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public MedicationSchedule updateMedSchedule(MedicationSchedule existingMedSchedule) {
        int index = -1;
        for (int i=0; i < medSchedules.size(); i++) {
            if(medSchedules.get(i).getMedSchedule_id() == existingMedSchedule.getMedSchedule_id()) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            medSchedules.set(index, existingMedSchedule);
            return existingMedSchedule;
        } else {
            return null;
        }
    }

    /**
     * @param medicationSchedule_id
     * @return
     */
    @Override
    public MedicationSchedule deleteMedSchedule(int medicationSchedule_id) {
        return null;
    }

}
