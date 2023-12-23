package be.kdg.programming3.pillgate.service;


import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;
import be.kdg.programming3.pillgate.repo.medSchedRepo.JDBCMedScheduleRepository;
import be.kdg.programming3.pillgate.repo.medSchedRepo.MedScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * The {@code MedicationScheduleServiceImp} class implements the {@link MedicationScheduleService} interface
 * and provides methods for managing medication schedules and retrieving statistical data.
 * This service interacts with the underlying {@link MedScheduleRepository} to perform CRUD operations
 * on {@link MedicationSchedule} entities.
 *
 * @author Team PillGate
 * @see MedicationScheduleService
 * @see MedScheduleRepository
 */
@Service
public class MedicationScheduleServiceImp implements MedicationScheduleService {
    private final MedScheduleRepository medScheduleRepository;

    /**
     * Constructs a new {@code MedicationScheduleServiceImp} with the specified repository.
     * @param medScheduleRepository The repository for MedicationSchedule entities.
     */
    @Autowired
    public MedicationScheduleServiceImp(MedScheduleRepository medScheduleRepository) {
        this.medScheduleRepository = medScheduleRepository;
    }

    /**
     * Retrieves all medication schedules from the repository.
     * @return A list of all medication schedules.
     */
    @Override
    public List<MedicationSchedule> getAllMedicationSchedules() {
        return medScheduleRepository.findAllMedSchedules();
    }

    /**
     * Retrieves a specific medication schedule by its ID.
     * @param medScheduleId The ID of the medication schedule to retrieve.
     * @return The medication schedule with the specified ID.
     */
    @Override
    public MedicationSchedule getMedicationScheduleById(int medScheduleId) {
        return medScheduleRepository.findMedScheduleById(medScheduleId);
    }

    /**
     * Creates a new medication schedule in the repository.
     * @param medSchedule The medication schedule to create.
     * @return The created medication schedule.
     */
    @Override
    public MedicationSchedule createMedicationSchedule(MedicationSchedule medSchedule) {
        return medScheduleRepository.createMedSchedule(medSchedule);
    }

    /**
     * Updates an existing medication schedule in the repository.
     * @param medSchedule The medication schedule to update.
     * @return The updated medication schedule.
     */
    @Override
    public MedicationSchedule updateMedicationSchedule(MedicationSchedule medSchedule) {
        return medScheduleRepository.updateMedSchedule(medSchedule);
    }

    /**
     * Deletes a medication schedule from the repository.
     * @param medScheduleId The ID of the medication schedule to delete.
     * @return The deleted medication schedule.
     */
    @Override
    public MedicationSchedule deleteMedicationSchedule(int medScheduleId) {
        return medScheduleRepository.deleteMedSchedule(medScheduleId);
    }

    /**
     * Retrieves the count of pills taken per day for the specified number of days.
     * @param nDays The number of days to consider for the statistics.
     * @return A list of daily pill counts.
     */
    @Override
    public List<JDBCMedScheduleRepository.DailyCount> getPillsTakenPerDay(int nDays) {
        return medScheduleRepository.getPillsTakenPerDay(nDays);
    }

    /**
     * Retrieves a map containing time-of-day pill data, grouped by hour and minute.
     * @return A nested map containing time-of-day pill data.
     */
    @Override
    public Map<Integer, Map<String, Integer>> getTimeOfDayPillData() {
        return medScheduleRepository.getTimeOfDayDataWithPillName();
    }


}
