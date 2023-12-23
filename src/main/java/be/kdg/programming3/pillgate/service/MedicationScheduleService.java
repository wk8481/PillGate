package be.kdg.programming3.pillgate.service;

import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;
import be.kdg.programming3.pillgate.repo.medSchedRepo.JDBCMedScheduleRepository;

import java.util.List;
import java.util.Map;

public interface MedicationScheduleService {
    List<MedicationSchedule> getAllMedicationSchedules();

    MedicationSchedule getMedicationScheduleById(int medScheduleId);

    MedicationSchedule createMedicationSchedule(MedicationSchedule medSchedule);

    MedicationSchedule updateMedicationSchedule(MedicationSchedule medSchedule);

    MedicationSchedule deleteMedicationSchedule(int medScheduleId);

    // Logic to get pills taken per day
    List<JDBCMedScheduleRepository.DailyCount> getPillsTakenPerDay(int nDays);

    // Logic to get time of day data
   /* public List<Integer> getTimeOfDayData() {
        return medScheduleRepository.getTimeOfDayData();
    }*/
    Map<Integer, Map<String, Integer>> getTimeOfDayPillData();

    // Logic to get time of day data
    //List<Integer> getTimeOfDayData();
}
