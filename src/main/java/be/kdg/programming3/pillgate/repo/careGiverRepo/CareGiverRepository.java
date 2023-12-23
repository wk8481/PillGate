package be.kdg.programming3.pillgate.repo.careGiverRepo;

import be.kdg.programming3.pillgate.domain.user.CareGiver;

import java.util.List;

/**
 * The {@code CareGiverRepository} interface defines methods for performing CRUD operations on {@link CareGiver} entities.
 * Implementing classes provide the actual logic to interact with the underlying data storage.
 *
 * @author Team PillGate
 * @see CareGiver
 */
public interface CareGiverRepository {

    /**
     * Creates a new caregiver in the data storage.
     *
     * @param careGiver The {@link CareGiver} object to be created.
     * @return The created {@link CareGiver} object.
     */
    CareGiver createCareGiver(CareGiver careGiver);

    /**
     * Retrieves a list of all caregivers from the data storage.
     *
     * @return A {@link List} of {@link CareGiver} objects.
     */
    List<CareGiver> findAllCareGivers();

    /**
     * Finds a care giver by its unique identifier.
     *
     * @param caregiver_id The unique identifier of the care giver.
     * @return The {@link CareGiver} object if found, otherwise {@code null}.
     */
    CareGiver findCaregiverByID(int caregiver_id);

    /**
     * Updates an existing caregiver in the data storage.
     *
     * @param existingCareGiver The {@link CareGiver} object with updated information.
     * @return The updated {@link CareGiver} object.
     */
    CareGiver updateCareGiver(CareGiver existingCareGiver);

    /**
     * Deletes a caregiver from the data storage.
     *
     * @param caregiver_id The unique identifier of the care giver to be deleted.
     * @return The deleted {@link CareGiver} object.
     */
    CareGiver deleteCaregiver(int caregiver_id);
}
