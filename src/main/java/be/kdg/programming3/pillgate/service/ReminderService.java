package be.kdg.programming3.pillgate.service;

import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;
import be.kdg.programming3.pillgate.pres.controllers.viewmodels.MedicationScheduleViewModel;

/**
 * Service interface for managing medication reminders.
 * This interface defines methods to convert, retrieve, save, and obtain alerts related to medication schedules.
 * @author Team PillGate
 * @see MedicationScheduleViewModel
 * @see MedicationSchedule
 */
public interface ReminderService {

    /**
     * Converts a {@link MedicationScheduleViewModel} object to a {@link MedicationSchedule} entity.
     * @param pillForm The view model representing the medication schedule form.
     * @return The converted {@link MedicationSchedule} entity.
     */
    MedicationSchedule convertToMedicationSchedule(MedicationScheduleViewModel pillForm);

    /**
     * Retrieves the medication schedule form based on the provided {@link MedicationScheduleViewModel}.
     * @param model The view model representing the medication schedule form.
     */
    void getMedicationScheduleForm(MedicationScheduleViewModel model);

    /**
     * Retrieves the latest medication schedule.
     * @return The latest {@link MedicationSchedule} entity.
     */
    MedicationSchedule getLatestMedicationSchedule();

    /**
     * Saves the medication schedule based on the provided {@link MedicationScheduleViewModel}.
     * @param pillForm The view model representing the medication schedule form.
     */
    void saveMedicationSchedule(MedicationScheduleViewModel pillForm);

    /**
     * Retrieves the alert message for the medication schedule.
     * @return The alert message for the medication schedule.
     */
    String getMedScheduleAlert();

    /**
     * Retrieves the alert message for the repeat interval.
     * @return The alert message for the repeat interval.
     */
    String getAlertForRepeatIn();
}

