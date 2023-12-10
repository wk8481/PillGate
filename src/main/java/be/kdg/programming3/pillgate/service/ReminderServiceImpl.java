package be.kdg.programming3.pillgate.service;

import be.kdg.programming3.pillgate.domain.user.Customer;
import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;
import be.kdg.programming3.pillgate.pres.controllers.viewmodels.MedicationScheduleViewModel;
//import be.kdg.programming3.pillgate.repo.userRepo.JPAUserRepository;
import be.kdg.programming3.pillgate.repo.customerRepo.CustomerRepository;
import be.kdg.programming3.pillgate.repo.customerRepo.JDBCCustomerRepository;
import be.kdg.programming3.pillgate.repo.medSchedRepo.JDBCMedscheduleRepository;
import be.kdg.programming3.pillgate.repo.medSchedRepo.MedScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReminderServiceImpl implements ReminderService {

    private final MedScheduleRepository medScheduleRepository;
    private final CustomerRepository customerRepository;

    //@Autowired
    //private SimpMessagingTemplate messagingTemplate;


    private Logger logger = LoggerFactory.getLogger(ReminderServiceImpl.class);

    @Autowired
    public ReminderServiceImpl(MedScheduleRepository medscheduleRepository, JDBCCustomerRepository customerRepository){
        this.medScheduleRepository = medscheduleRepository;
        this.customerRepository = customerRepository;
    }


    @Override
    public MedicationSchedule convertToMedicationSchedule(MedicationScheduleViewModel pillForm){
        MedicationSchedule medicationSchedule = new MedicationSchedule();

        // set the properties of the medicationSchedule object
        medicationSchedule.setPillName(pillForm.getPillName());
        medicationSchedule.setTimeTakePill(pillForm.getTimeTakePill());
        medicationSchedule.setRepeatIn(pillForm.getRepeatIn());

        return medicationSchedule;
    }

    @Override
    public void getMedicationScheduleForm(MedicationScheduleViewModel model){
        medScheduleRepository.createMedSchedule(convertToMedicationSchedule(model));
    }



    @Override
    public void saveMedicationSchedule(MedicationScheduleViewModel pillForm) {
        Customer customerExists = customerRepository.findCustomerById(pillForm.getCustomer_id());

        if (customerExists == null) {
            logger.error("Customer ID {} does not exist.", pillForm.getCustomer_id());
            throw new IllegalArgumentException("Invalid customer_id: " + pillForm.getCustomer_id());
        }

        MedicationSchedule medicationSchedule = convertToMedicationSchedule(pillForm);
        medScheduleRepository.createMedSchedule(medicationSchedule);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Customer customer = customerRepository.findCustomerByUsername(username);

        if (customer == null) {
            logger.error("Username {} does not exist.", username);
        } else {
            logger.info("This is customer {} from customerByUsername", customer);
        }

    }

    @Override
    public boolean isTimeForReminder(){
        List<MedicationSchedule> upcomingSchedules = medScheduleRepository.findAllMedSchedules();

        for(MedicationSchedule medicationSchedule: upcomingSchedules){
            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime scheduledTime = medicationSchedule.getTimeTakePill();

            if(currentTime.isEqual(scheduledTime)){
                return true;
            }
        }
        return false;
    }



    @Override
    public String getMedScheduleAlert() {
        logger.info("Getting medication schedule message");
        LocalDateTime currentDateTime = LocalDateTime.now();
        for (MedicationSchedule medicationSchedule : medScheduleRepository.findAllMedSchedules()) {
            if (medicationSchedule.getTimeTakePill().getHour() == currentDateTime.getHour()
            && medicationSchedule.getTimeTakePill().getMinute() == currentDateTime.getMinute()
            && !medicationSchedule.isStopped()) {
                medicationSchedule.setStopped(true);
                return medicationSchedule.getMessage();
            }
        }
        return null;
    }


}
