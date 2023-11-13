package be.kdg.programming3.service;

import be.kdg.programming3.domain.user.CareGiver;
import be.kdg.programming3.domain.user.Customer;

public interface UserService {

    Customer addCustomer(Customer customer);

    CareGiver createCareGiver(CareGiver careGiver);

}
