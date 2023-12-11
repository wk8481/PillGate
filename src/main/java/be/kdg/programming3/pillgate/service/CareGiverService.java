package be.kdg.programming3.pillgate.service;


import be.kdg.programming3.pillgate.domain.user.CareGiver;
import be.kdg.programming3.pillgate.domain.user.Customer;
import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;
import be.kdg.programming3.pillgate.pres.controllers.viewmodels.CustomerLoginDto;
import be.kdg.programming3.pillgate.pres.controllers.viewmodels.CustomerRegistrationDto;

import java.util.List;

public interface CareGiverService {



    CareGiver createCareGiver(CareGiver careGiver);

    List<CareGiver> getCaregivers();
}
