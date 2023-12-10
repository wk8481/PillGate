package be.kdg.programming3.pillgate.repo.customerRepo;


import be.kdg.programming3.pillgate.domain.user.CareGiver;
import be.kdg.programming3.pillgate.domain.user.Customer;
import be.kdg.programming3.pillgate.domain.user.Dashboard;
import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;

import java.util.List;

/**
 * This interface shows the methods that are used in the repository.
 * These methods are implemented in the repository implementation for the user package.
 */



public interface UserRepository {
    /* These methods show:
     *  - Customer Repository methods */
    Customer createCustomer(Customer customer);
    List<Customer> findAllCustomers();
    Customer findCustomerById(int customer_id);
    Customer findCustomerByUsername(String username);
    Customer findCustomerByEmail(String email);
    Customer updateCustomer(Customer existingCustomer);
    Customer deleteCustomer(Customer customer);


    /* These methods show:
     *  - Caregiver methods */
    CareGiver createCareGiver(CareGiver careGiver);
    List<CareGiver> findAllCareGivers();
    CareGiver findCaregiverByID(int caregiver_id);
    CareGiver updateCareGiver(CareGiver existingCareGiver);
    CareGiver deleteCaregiver(int caregiver_id);

    /* These methods show:
    /*Dashboard methods*/
    List<Dashboard> findAllDashboards();
    Dashboard findDashboardByID(int dashboard_ID);
    Dashboard createDashboard(Dashboard dashboard);
    Dashboard updateDashboard(Dashboard dashboard);
    Dashboard deleteDashboard(int dashboard_ID);


    /* These methods show:
     *  - Medication Schedule methods */
    MedicationSchedule createMedSchedule(MedicationSchedule medicationSchedule);
    List<MedicationSchedule> findAllMedSchedules(); //findall
    MedicationSchedule findMedScheduleById(int medSchedule_id); //findbyid
    MedicationSchedule updateMedSchedule(MedicationSchedule existingMedSchedule);
    MedicationSchedule deleteMedSchedule(int medicationSchedule_id);

}
