package be.kdg.programming3.pillgate.service;

import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;
import be.kdg.programming3.pillgate.repo.medSchedRepo.JDBCMedScheduleRepository;

import java.util.List;
import java.util.Map;

/**
 * The {@code MedicationScheduleService} interface defines methods for managing medication schedules
 * and retrieving statistical data related to pill consumption.
 *
 * Implementing classes, such as {@link MedicationScheduleServiceImp}, provide the actual
 * functionality by interacting with underlying repositories, such as {@link JDBCMedScheduleRepository}.
 *
 * It includes standard CRUD operations for medication schedules (Create, Read, Update, Delete),
 * as well as methods for retrieving statistical information, such as pills taken per day and time-of-day pill data.
 *
 * @author Team PillGate
 * @see MedicationSchedule
 * @see JDBCMedScheduleRepository
 * @see MedicationScheduleServiceImp
 */

public interface MedicationScheduleService {

    /**
     * Retrieves a list of all medication schedules.
     * @return A list containing all medication schedules.
     */
    List<MedicationSchedule> getAllMedicationSchedules();
    /**
     * Retrieves a specific medication schedule by its ID.
     * @param medScheduleId The ID of the medication schedule to retrieve.
     * @return The medication schedule with the specified ID.
     */
    MedicationSchedule getMedicationScheduleById(int medScheduleId);
    /**
     * Creates a new medication schedule.
     * @param medSchedule The medication schedule to create.
     * @return The created medication schedule.
     */
    MedicationSchedule createMedicationSchedule(MedicationSchedule medSchedule);
    /**
     * Updates an existing medication schedule.
     * @param medSchedule The medication schedule to update.
     * @return The updated medication schedule.
     */
    MedicationSchedule updateMedicationSchedule(MedicationSchedule medSchedule);
    /**
     * Updates an existing medication schedule.
     * @param medScheduleId The medication schedule to update.
     * @return The updated medication schedule.
     */
    MedicationSchedule deleteMedicationSchedule(int medScheduleId);

    /**
     * Retrieves the count of pills taken per day for the specified number of days.
     * @param nDays The number of days to consider for the statistics.
     * @return A list of daily pill counts.
     */
    List<JDBCMedScheduleRepository.DailyCount> getPillsTakenPerDay(int nDays);

    /**
     * Retrieves a map containing time-of-day pill data, grouped by hour and minute.
     * @return A nested map containing time-of-day pill data.
     */
    Map<Integer, Map<String, Integer>> getTimeOfDayPillData();


}
