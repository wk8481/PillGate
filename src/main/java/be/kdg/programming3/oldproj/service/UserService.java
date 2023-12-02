package be.kdg.programming3.oldproj.service;

import be.kdg.programming3.oldproj.domain.user.CareGiver;
import be.kdg.programming3.oldproj.domain.user.Customer;
import be.kdg.programming3.oldproj.domain.user.MedicationSchedule;
import be.kdg.programming3.oldproj.controllers.viewmodels.CustomerRegistrationDto;

import java.util.List;

public interface UserService {

    Customer addCustomer(Customer customer);

    List<Customer> getCustomers();

    CareGiver createCareGiver(CareGiver careGiver);

    List<CareGiver> getCaregivers();

    MedicationSchedule createMedSchedule(MedicationSchedule medSchedule);

    Customer registerNewCustomer(CustomerRegistrationDto registrationDto);

    Customer convertDtoToCustomer(CustomerRegistrationDto registrationDto);

}
