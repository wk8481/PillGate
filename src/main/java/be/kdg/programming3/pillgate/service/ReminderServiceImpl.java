package be.kdg.programming3.pillgate.service;

import be.kdg.programming3.pillgate.domain.sensor.WeightSensorData;
import be.kdg.programming3.pillgate.domain.user.Customer;
import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;
import be.kdg.programming3.pillgate.pres.controllers.viewmodels.MedicationScheduleViewModel;
import be.kdg.programming3.pillgate.repo.customerRepo.CustomerRepository;
import be.kdg.programming3.pillgate.repo.medSchedRepo.MedScheduleRepository;
import be.kdg.programming3.pillgate.repo.sensorRepo.SensorRepository;
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
 * The {@code ReminderServiceImpl} class implements the {@link ReminderService} interface
 * and provides methods for managing medication schedules, converting view models,
 * and handling medication reminders.
 * @author Team PillGate
 * @see ReminderService
 */

@Service
public class ReminderServiceImpl implements ReminderService, Serializable {

    private final MedScheduleRepository medScheduleRepository;
    private final CustomerRepository customerRepository;
    private final SensorRepository sensorRepository;

    @Autowired
    private HttpServletRequest request;

    private Logger logger = LoggerFactory.getLogger(ReminderServiceImpl.class);

    /**
     * Constructs a new {@code ReminderServiceImpl} with the specified repositories and servlet request.
     * @param medscheduleRepository The repository for MedicationSchedule entities.
     * @param customerRepository    The repository for Customer entities.
     * @param sensorRepository      The repository for WeightSensorData entities.
     */

    @Autowired
    public ReminderServiceImpl(MedScheduleRepository medscheduleRepository, CustomerRepository customerRepository, SensorRepository sensorRepository){
        this.medScheduleRepository = medscheduleRepository;
        this.customerRepository = customerRepository;
        this.sensorRepository = sensorRepository;
    }

    /**
     * Converts a {@link MedicationScheduleViewModel} to a {@link MedicationSchedule}.
     * @param pillForm The view model containing medication schedule data.
     * @return The converted MedicationSchedule entity.
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


    @Override
    public void getMedicationScheduleForm(MedicationScheduleViewModel model){
        medScheduleRepository.createMedSchedule(convertToMedicationSchedule(model));
        logger.info("Created medication schedule {}", model);
    }

    /**
     * Retrieves the latest MedicationSchedule from the repository.
     * @return The latest MedicationSchedule, or {@code null} if none exists.
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
     * First, it checks if the customer exists in the database.
     * If the customer exists, it will create a new weight sensor for the specific customer.
     * Before saving the medication schedule, it will set the message to the customer, based on the pill name.
     * Saves a MedicationSchedule based on the provided view model.
     * @param pillForm The view model containing medication schedule data.
     * Before saving medicationShcedule
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

        // Creates a weight sensor for the customer
        WeightSensorData weightSensorData = new WeightSensorData();
        weightSensorData.setCustomer(customerExists);
        weightSensorData.setCalibrationDate(LocalDateTime.now());
        sensorRepository.createSensor(weightSensorData);
        logger.info("Weight sensor created for the customer {}", weightSensorData);


        medicationSchedule.setMessage("It's time to take your " + pillForm.getPillName());
        medScheduleRepository.createMedSchedule(medicationSchedule);
        logger.info("Customer_id: {} saved medication schedule", customerId);
    }

    /**
     * Extracts the customer ID from the current session.
     * @return The customer ID.
     * @throws IllegalStateException if the user is not authenticated.
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
     * Retrieves the medication schedule alert message for the current time.
     * it will check if the current time is equal to the timeTakePill of the medication schedule.
     * If the time is equal, it will set the message to the customer.
     * @return The medication schedule alert message, or {@code null} if no alert is present.
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
                medicationSchedule.setMessage("It is time to take your " + medicationSchedule.getPillName());
                logger.info("The message: {}", medicationSchedule.getMessage());
                return medicationSchedule.getMessage();
            }
        }
        return null;
    }

    /**
     * This is the same method from above, but it will check if the current time is equal to the time which repeatIn (hours) + timeTakePIll of the medication schedule.
     * If the time is equal, it will set the message to the customer.
     * @return The medication schedule alert message, or {@code null} if no alert is present.
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
                medicationSchedule.setMessage("It is time to take your " + medicationSchedule.getPillName());
                logger.info("The message: {}", medicationSchedule.getMessage());
                return medicationSchedule.getMessage();
            }
        }

        return null;
    }



}
