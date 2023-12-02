package be.kdg.programming3.oldproj.service;

import be.kdg.programming3.oldproj.domain.user.MedicationSchedule;
import be.kdg.programming3.oldproj.controllers.viewmodels.MedicationScheduleViewModel;
import be.kdg.programming3.oldproj.repository.JDBCUserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReminderService {

    @Autowired
    private JDBCUserRepository userRepository;

    //@Autowired
    //private SimpMessagingTemplate messagingTemplate;


    private Logger logger = LoggerFactory.getLogger(ReminderService.class);


    private MedicationSchedule convertToMedicationSchedule(MedicationScheduleViewModel pillForm){
        MedicationSchedule medicationSchedule = new MedicationSchedule();

        // set the properties of the medicationSchedule object
        medicationSchedule.setPillName(pillForm.getPillName());
        medicationSchedule.setTimeTakePill(pillForm.getTimeTakePill());
        medicationSchedule.setRepeatIn(pillForm.getRepeatIn());

        return medicationSchedule;
    }

    private void getMedicationScheduleForm (MedicationScheduleViewModel model){
        userRepository.createMedSchedule(convertToMedicationSchedule(model));
    }


    @Transactional
    public void saveMedicationSchedule(MedicationScheduleViewModel pillForm){
        MedicationSchedule medicationSchedule = convertToMedicationSchedule(pillForm); //convert pillform to medschedul
        userRepository.createMedSchedule(medicationSchedule); // create it

    }

    // this method to check if it is time for a reminder
    public boolean isTimeForReminder(){
        List<MedicationSchedule> upcomingSchedules = userRepository.findAllMedSchedules();

        for(MedicationSchedule medicationSchedule: upcomingSchedules){
            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime scheduledTime = medicationSchedule.getTimeTakePill();

            if(currentTime.isEqual(scheduledTime)){
                return true;
            }
        }
        return false;
    }




}
