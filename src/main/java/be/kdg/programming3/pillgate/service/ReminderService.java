package be.kdg.programming3.pillgate.service;

import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;
import be.kdg.programming3.pillgate.pres.controllers.viewmodels.MedicationScheduleViewModel;

public interface ReminderService {
    MedicationSchedule convertToMedicationSchedule(MedicationScheduleViewModel pillForm);

    void getMedicationScheduleForm(MedicationScheduleViewModel model);

    MedicationSchedule getLatestMedicationSchedule();

    void saveMedicationSchedule(MedicationScheduleViewModel pillForm);

    // this method to check if it is time for a reminder
    boolean isTimeForReminder();

    String getMedScheduleAlert();
}
