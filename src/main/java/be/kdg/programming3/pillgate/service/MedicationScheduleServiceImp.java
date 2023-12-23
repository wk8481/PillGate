package be.kdg.programming3.pillgate.service;


import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;
import be.kdg.programming3.pillgate.repo.medSchedRepo.MedScheduleRepository;
import be.kdg.programming3.pillgate.repo.medSchedRepo.PGMedScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MedicationScheduleServiceImp implements MedicationScheduleService {
    private final MedScheduleRepository medScheduleRepository;

    @Autowired
    public MedicationScheduleServiceImp(MedScheduleRepository medScheduleRepository) {
        this.medScheduleRepository = medScheduleRepository;
    }

    @Override
    public List<MedicationSchedule> getAllMedicationSchedules() {
        return medScheduleRepository.findAllMedSchedules();
    }
    @Override
    public MedicationSchedule getMedicationScheduleById(int medScheduleId) {
        return medScheduleRepository.findMedScheduleById(medScheduleId);
    }
    @Override
    public MedicationSchedule createMedicationSchedule(MedicationSchedule medSchedule) {
        return medScheduleRepository.createMedSchedule(medSchedule);
    }
    @Override
    public MedicationSchedule updateMedicationSchedule(MedicationSchedule medSchedule) {
        return medScheduleRepository.updateMedSchedule(medSchedule);
    }
    @Override
    public MedicationSchedule deleteMedicationSchedule(int medScheduleId) {
        return medScheduleRepository.deleteMedSchedule(medScheduleId);
    }

    @Override
    public List<PGMedScheduleRepository.DailyCount> getPillsTakenPerDay(int nDays) {
        return medScheduleRepository.getPillsTakenPerDay(nDays);
    }
    @Override
    public Map<Integer, Map<String, Integer>> getTimeOfDayPillData() {
        return medScheduleRepository.getTimeOfDayDataWithPillName();
    }


}
