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

/**
 * Service implementation for managing medication reminders.
 * This class provides methods to convert, retrieve, save, and obtain alerts related to medication schedules.
 *
 * Implements:
 * {@link be.kdg.programming3.pillgate.service.ReminderService}
 * {@link java.io.Serializable}
 *
 * @author Team PillGate
 */
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


//    PREVIOUS CONSTRUCTOR USING JDBC CUSTOMER REPOSITORY
//    @Autowired
//    public ReminderServiceImpl(MedScheduleRepository medscheduleRepository, JDBCCustomerRepository customerRepository){
//        this.medScheduleRepository = medscheduleRepository;
//        this.customerRepository = customerRepository;
//    }

    /**
     * Converts a {@link MedicationScheduleViewModel} object to a {@link MedicationSchedule} entity.
     *
     * @param pillForm The view model representing the medication schedule form.
     * @return The converted {@link MedicationSchedule} entity.
     */
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

    /**
     * Retrieves the medication schedule form based on the provided {@link MedicationScheduleViewModel}.
     *
     * @param model The view model representing the medication schedule form.
     */
    @Override
    public void getMedicationScheduleForm(MedicationScheduleViewModel model){
        medScheduleRepository.createMedSchedule(convertToMedicationSchedule(model));
        logger.info("Created medication schedule {}", model);
    }

    /**
     * Retrieves the latest medication schedule.
     *
     * @return The latest {@link MedicationSchedule} entity.
     */
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

    /**
     * Saves the medication schedule based on the provided {@link MedicationScheduleViewModel}.
     *
     * @param pillForm The view model representing the medication schedule form.
     */
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

    /**
     * Extracts the customer ID from the session.
     *
     * @return The customer ID extracted from the session.
     * @throws IllegalStateException If the user is not authenticated.
     */
    private int extractCustomerIdFromSession() {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("customer_id") != null) {
            return (int) session.getAttribute("customer_id");
        } else {
            throw new IllegalStateException("User not authenticated");
        }
    }



    /**
     * Extracts the username (email) from the session.
     *
     * @return The username (email) extracted from the session.
     * @throws IllegalStateException If the user is not authenticated.
     */
    // Method to extract username from the session
    private String extractUsernameFromSession() {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("email") != null) {
            return (String) session.getAttribute("email");
        } else {
            throw new IllegalStateException("User not authenticated");
        }
    }


    /**
     * Gets the alert message for the current medication schedule.
     *
     * @return The alert message for the current medication schedule.
     */
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

    /**
     * Gets the alert message for the next occurrence based on the repeat interval.
     *
     * @return The alert message for the next occurrence based on the repeat interval.
     */
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
