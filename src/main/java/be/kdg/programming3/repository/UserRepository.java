package be.kdg.programming3.repository;

import be.kdg.programming3.domain.user.CareGiver;
import be.kdg.programming3.domain.user.Customer;
import be.kdg.programming3.domain.user.Dashboard;
import be.kdg.programming3.domain.user.MedicationSchedule;

/**
 * @author William, Narjiss, Alperen, and Manami
 */

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
    Customer updateCustomer(Customer existingCustomer);
    Customer deleteCustomer(int customer_id);


    /* These methods show:
     *  - Customer Repository methods */
    CareGiver createCareGiver(CareGiver careGiver);
    List<CareGiver> findAllCareGivers();
    CareGiver findCaregiverByID(int customer_id);
    CareGiver updateCareGiver(CareGiver existingCareGiver);
    CareGiver deleteCaregiver(int caregiver_id);

    /* These methods show:
     *  - Customer Repository methods */
    Dashboard createDashboard(Dashboard dashboard);
    List<Dashboard> findAllDashboards();
    Dashboard getDashboardByID(int dashboard_id);
    Dashboard updateDashboard(Dashboard existingDashboard);
    Dashboard deleteDashboard(int dashboard_id);


    /* These methods show:
     *  - Customer Repository methods */
    MedicationSchedule createMedSchedule(MedicationSchedule medicationSchedule);
    List<MedicationSchedule> findAllMedSchedules(); //findall
    MedicationSchedule findByMedScheduleId(int medicationSchedule_id); //findbyid
    MedicationSchedule updateMedSchedule(MedicationSchedule existingMedSchedule);
    MedicationSchedule deleteMedSchedule(int medicationSchedule_id);

/*  findall
    findbyId
    create
    update
    delete

 */




}
