package be.kdg.programming3.pillgate.service;

import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;
import be.kdg.programming3.pillgate.pres.controllers.viewmodels.MedicationScheduleViewModel;
import be.kdg.programming3.pillgate.repo.userRepo.JDBCUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReminderService {

    private JDBCUserRepository userRepository;

    //@Autowired
    //private SimpMessagingTemplate messagingTemplate;


    private Logger logger = LoggerFactory.getLogger(ReminderService.class);

    @Autowired
    public ReminderService(JDBCUserRepository userRepository){
        this.userRepository = userRepository;
    }




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



    public void saveMedicationSchedule(MedicationScheduleViewModel pillForm){
        MedicationSchedule medicationSchedule = convertToMedicationSchedule(pillForm); //convert pillForm to medschedule
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



    public String getMedScheduleAlert() {
        logger.info("Getting medication schedule message");
        LocalDateTime currentDateTime = LocalDateTime.now();
        for (MedicationSchedule medicationSchedule : userRepository.findAllMedSchedules()) {
            if (medicationSchedule.getTimeTakePill().getHour() == currentDateTime.getHour()
            && medicationSchedule.getTimeTakePill().getMinute() == currentDateTime.getMinute()
            && !medicationSchedule.isStopped()) {
                medicationSchedule.setStopped(true);
                userRepository.createMedSchedule(medicationSchedule);
                return medicationSchedule.getMessage();
            }
        }
        return null;
    }

}
