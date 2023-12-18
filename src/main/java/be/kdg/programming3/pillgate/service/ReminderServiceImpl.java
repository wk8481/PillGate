package be.kdg.programming3.pillgate.service;

import be.kdg.programming3.pillgate.domain.user.Customer;
import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;
import be.kdg.programming3.pillgate.pres.controllers.viewmodels.MedicationScheduleViewModel;
import be.kdg.programming3.pillgate.repo.customerRepo.CustomerRepository;
import be.kdg.programming3.pillgate.repo.medSchedRepo.MedScheduleRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class ReminderServiceImpl implements ReminderService, Serializable {

    private final MedScheduleRepository medScheduleRepository;
    private final CustomerRepository customerRepository;
    @Autowired
    private HttpServletRequest request;

    private Logger logger = LoggerFactory.getLogger(ReminderServiceImpl.class);

    @Autowired
    public ReminderServiceImpl(MedScheduleRepository medscheduleRepository, CustomerRepository customerRepository){
        this.medScheduleRepository = medscheduleRepository;
        this.customerRepository = customerRepository;
    }


    @Override
    public MedicationSchedule convertToMedicationSchedule(MedicationScheduleViewModel pillForm){
        MedicationSchedule medicationSchedule = new MedicationSchedule();

        int customerId = extractCustomerIdFromSession();
        logger.info("Customer_id: {} retrieved from session", customerId);
        Customer customer = customerRepository.findCustomerById(customerId);
        medicationSchedule.setCustomer(customer);
        logger.info("setting customer to medicationSchedule"+customer);
        medicationSchedule.setPillName(pillForm.getPillName());
        medicationSchedule.setTimeTakePill(pillForm.getTimeTakePill());
        medicationSchedule.setRepeatIn(pillForm.getRepeatIn());
        medicationSchedule.setNrOfPillsPlaced(pillForm.getNrOfPillsPlaced());
        logger.info("Converted medschedule {}", medicationSchedule);
        return medicationSchedule;
    }

    @Override
    public void getMedicationScheduleForm(MedicationScheduleViewModel model){
        medScheduleRepository.createMedSchedule(convertToMedicationSchedule(model));
        logger.info("Created medication schedule {}", model);
    }

    @Override
    public MedicationSchedule getLatestMedicationSchedule() {
        // Logic to retrieve the latest medication schedule from the repository
        // Example: Order by medSchedule_id in descending order and return the first one
        List<MedicationSchedule> allSchedules = medScheduleRepository.findAllMedSchedules();

        return allSchedules.stream()
                .sorted(Comparator.comparingInt(MedicationSchedule::getMedSchedule_id).reversed())
                .findFirst()
                .orElse(null);
    }

    @Override
    public void saveMedicationSchedule(MedicationScheduleViewModel pillForm) {
        int customerId  = extractCustomerIdFromSession();

        Customer customerExists = customerRepository.findCustomerById(customerId);

        if (customerExists == null) {
            logger.error("Customer ID {} does not exist.", customerId);
            throw new IllegalArgumentException("Invalid customer_id: " + customerId);
        }

        MedicationSchedule medicationSchedule = convertToMedicationSchedule(pillForm);
        medicationSchedule.setMessage("It's time to take your " + pillForm.getPillName());
        medScheduleRepository.createMedSchedule(medicationSchedule);
        logger.info("Customer_id: {} saved medication schedule", customerId);
    }

    private int extractCustomerIdFromSession() {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("customer_id") != null) {
            return (int) session.getAttribute("customer_id");
        } else {
            throw new IllegalStateException("User not authenticated");
        }
    }




    // Method to extract username from the session
    private String extractUsernameFromSession() {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("email") != null) {
            return (String) session.getAttribute("email");
        } else {
            throw new IllegalStateException("User not authenticated");
        }
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
                logger.info("It is time to take your {}", medicationSchedule.getPillName());
                medScheduleRepository.createMedSchedule(medicationSchedule);
                return medicationSchedule.getMessage();
            }
        }
        return null;
    }


    public String getAlertForRepeatIn(){
        logger.info("Getting medication schedule message to repeat");
        LocalDateTime currentDateTime = LocalDateTime.now();

        for (MedicationSchedule medicationSchedule : medScheduleRepository.findAllMedSchedules()) {
            if (medicationSchedule.getTimeTakePill().plusHours(medicationSchedule.getRepeatIn()).getHour() == currentDateTime.getHour()
                    && medicationSchedule.getTimeTakePill().getMinute() == currentDateTime.getMinute()
                    && !medicationSchedule.isStopped()) {
                medicationSchedule.setStopped(true);
                logger.info("It is time to take your {}", medicationSchedule.getPillName());
                medScheduleRepository.createMedSchedule(medicationSchedule);
                return medicationSchedule.getMessage();
            }
        }

        return null;
    }



}
