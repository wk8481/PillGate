package be.kdg.programming3.pillgate.repo.medSchedRepo;

import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;

import java.util.List;
import java.util.Map;

/**
 * The {@code MedScheduleRepository} interface provides methods for interacting with Medication Schedules in the repository.
 * @author Team PillGate
 * @see MedicationSchedule
 */

public interface MedScheduleRepository {

    /**
     * Creates a new Medication Schedule in the repository.
     *
     * @param medicationSchedule The Medication Schedule to be created.
     * @return The created Medication Schedule.
     */
    MedicationSchedule createMedSchedule(MedicationSchedule medicationSchedule);

    /**
     * Retrieves a list of all Medication Schedules in the repository.
     *
     * @return A list of Medication Schedules.
     */
    List<MedicationSchedule> findAllMedSchedules();

    /**
     * Retrieves a Medication Schedule by its unique identifier.
     *
     * @param medSchedule_id The unique identifier of the Medication Schedule.
     * @return The Medication Schedule with the specified identifier, or {@code null} if not found.
     */
    MedicationSchedule findMedScheduleById(int medSchedule_id);

    /**
     * Updates an existing Medication Schedule in the repository.
     *
     * @param existingMedSchedule The existing Medication Schedule to be updated.
     * @return The updated Medication Schedule.
     */
    MedicationSchedule updateMedSchedule(MedicationSchedule existingMedSchedule);

    /**
     * Deletes a Medication Schedule from the repository by its unique identifier.
     *
     * @param medicationSchedule_id The unique identifier of the Medication Schedule to be deleted.
     * @return The deleted Medication Schedule, or {@code null} if not found.
     */
    MedicationSchedule deleteMedSchedule(int medicationSchedule_id);

    List<JDBCMedScheduleRepository.DailyCount> getPillsTakenPerDay(int nDays);

   // List<Integer> getTimeOfDayData();

    Map<Integer, Map<String, Integer>> getTimeOfDayDataWithPillName();
}
