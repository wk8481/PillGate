package be.kdg.programming3.pillgate.service;

import be.kdg.programming3.pillgate.domain.user.CareGiver;
import be.kdg.programming3.pillgate.repo.careGiverRepo.CareGiverRepository;

import java.util.List;

/**
 * The {@code CareGiverService} interface defines the contract for managing CareGiver entities.
 * It declares methods for creating and retrieving CareGiver instances.
 *
 * @author Team PillGate
 * @see CareGiverRepository
 * @see CareGiver
 */
public interface CareGiverService {

    /**
     * Creates a new CareGiver instance.
     *
     * @param careGiver The CareGiver object to be created.
     * @return The created CareGiver instance.
     */
    CareGiver createCareGiver(CareGiver careGiver);

    /**
     * Retrieves a list of all CareGivers.
     *
     * @return A list containing all CareGiver instances.
     */
    List<CareGiver> getCaregivers();
}
