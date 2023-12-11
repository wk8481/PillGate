package be.kdg.programming3.pillgate.service;


import be.kdg.programming3.pillgate.domain.user.CareGiver;


import java.util.List;

public interface CareGiverService {



    CareGiver createCareGiver(CareGiver careGiver);

    List<CareGiver> getCaregivers();
}
