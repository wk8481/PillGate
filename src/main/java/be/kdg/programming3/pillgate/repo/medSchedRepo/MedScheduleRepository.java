package be.kdg.programming3.pillgate.repo.medSchedRepo;

import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;

import java.util.List;
import java.util.Map;

public interface MedScheduleRepository {

    MedicationSchedule createMedSchedule(MedicationSchedule medicationSchedule);
    List<MedicationSchedule> findAllMedSchedules();
    MedicationSchedule findMedScheduleById(int medSchedule_id);
    MedicationSchedule updateMedSchedule(MedicationSchedule existingMedSchedule);
    MedicationSchedule deleteMedSchedule(int medicationSchedule_id);

    List<PGMedScheduleRepository.DailyCount> getPillsTakenPerDay(int nDays);

   // List<Integer> getTimeOfDayData();

    Map<Integer, Map<String, Integer>> getTimeOfDayDataWithPillName();
}
