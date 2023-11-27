package be.kdg.programming3.service;

import be.kdg.programming3.domain.user.CareGiver;
import be.kdg.programming3.domain.user.Customer;
import be.kdg.programming3.domain.user.Dashboard;
import be.kdg.programming3.domain.user.MedicationSchedule;
import be.kdg.programming3.model.CustomerRegistrationDto;

import java.util.List;

public interface UserService {

    Customer addCustomer(Customer customer);

    CareGiver createCareGiver(CareGiver careGiver);

    MedicationSchedule createMedSchedule(MedicationSchedule medSchedule);

    Customer registerNewCustomer(CustomerRegistrationDto registrationDto);

    Customer convertDtoToCustomer(CustomerRegistrationDto registrationDto);

}
