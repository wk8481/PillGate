package be.kdg.programming3.oldproj.repository;

import be.kdg.programming3.oldproj.domain.user.MedicationSchedule;
import java.util.List;

public interface MedicationScheduleRepository {

    /**
     * Creates a new medication schedule in the repository.
     *
     * @param schedule the medication schedule to create
     * @return the created medication schedule
     */
    MedicationSchedule createMedicationSchedule(MedicationSchedule schedule);

    /**
     * Finds a medication schedule by its ID.
     *
     * @param medScheduleId the ID of the medication schedule
     * @return the medication schedule, or null if not found
     */
    MedicationSchedule findMedicationScheduleById(int medScheduleId);

    /**
     * Retrieves all medication schedules in the repository.
     *
     * @return a list of all medication schedules
     */
    List<MedicationSchedule> findAllMedicationSchedules();

    /**
     * Updates an existing medication schedule.
     *
     * @param schedule the medication schedule with updated information
     * @return the updated medication schedule, or null if the update was not successful
     */
    MedicationSchedule updateMedicationSchedule(MedicationSchedule schedule);

    /**
     * Deletes a medication schedule by its ID.
     *
     * @param medScheduleId the ID of the medication schedule to delete
     * @return the deleted medication schedule, or null if the schedule was not found
     */
    MedicationSchedule deleteMedicationSchedule(int medScheduleId);
}
