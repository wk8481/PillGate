package be.kdg.programming3.pillgate.repo.careGiverRepo;

import be.kdg.programming3.pillgate.domain.user.CareGiver;

import java.util.List;

public interface CareGiverRepository {

    CareGiver createCareGiver(CareGiver careGiver);
    List<CareGiver> findAllCareGivers();
    CareGiver findCaregiverByID(int caregiver_id);
    CareGiver updateCareGiver(CareGiver existingCareGiver);
    CareGiver deleteCaregiver(int caregiver_id);

}
