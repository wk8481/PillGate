package be.kdg.programming3.repository;

import be.kdg.programming3.domain.pillbox.PillBox;
import be.kdg.programming3.domain.user.CareGiver;
import be.kdg.programming3.domain.user.Customer;
import be.kdg.programming3.domain.user.Dashboard;
import be.kdg.programming3.domain.user.MedicationSchedule;

import java.util.List;

public interface UserRepository {
    /* These methods show:
     *  - Customer Repository methods */
    Customer createCustomer(Customer customer);
    List<Customer> readCustomers();
    Customer getCustomerByID(int customer_id);
    Customer updateCustomer(Customer existingCustomer);


    /* These methods show:
     *  - Customer Repository methods */
    CareGiver createCareGiver(CareGiver careGiver);
    List<CareGiver> readCareGivers();
    CareGiver getCaregiverByID(int customer_id);
    CareGiver updateCareGiver(CareGiver existingCareGiver);

    /* These methods show:
     *  - Customer Repository methods */
    Dashboard createDashboard(Dashboard dashboard);
    List<Dashboard> readDashBoards();
    Dashboard getDashboardByID(int dashboard_id);
    Dashboard updateDashboard(Dashboard existingDashboard);


    /* These methods show:
     *  - Customer Repository methods */
    MedicationSchedule createMedSchedule(MedicationSchedule medicationSchedule);
    List<MedicationSchedule> readMedSchedule();
    MedicationSchedule getMedScheduleByID(int medicationSchedule_id);
    MedicationSchedule updateMedSchedule(MedicationSchedule existingMedSchedule);
}
