package be.kdg.programming3.pillgate.pres.controllers.viewmodels;

import be.kdg.programming3.pillgate.domain.user.Customer;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ViewModel for representing medication schedule information in the presentation layer.
 * This class contains fields representing medication schedule details and is used in the presentation layer.
 *
 * @author Team PillGate
 */

@Component
public class MedicationScheduleViewModel {

    /**
     * Unique identifier for the medication schedule.
     */
    private int medSchedule_id;

    /**
     * Identifier of the associated customer.
     */
    @Positive(message = "customer_id must be a positive number")
    private int customer_id;

    /**
     * Start date of the medication schedule.
     */
    @NotNull(message = "startDate cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    /**
     * End date of the medication schedule.
     */
    @NotNull(message = "endDate cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    /**
     * Name of the medication.
     */
    @NotBlank(message = "pillName should not be empty")
    private String pillName;

    /**
     * Quantity of pills.
     */
    @Positive(message = "quantity must be a positive number")
    private int quantity;

    /**
     * Time at which the pill should be taken.
     */
    @NotNull(message = "timeTakePill cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeTakePill;

    /**
     * Interval at which the pill should be repeated (in minutes).
     */
    @PositiveOrZero(message = "repeatIn must be a positive number or zero")
    private int repeatIn;

    /**
     * Number of pills placed.
     */
    @PositiveOrZero(message = "nrOfPillsPlaced must be a positive number or zero")
    private int nrOfPillsPlaced;

    /**
     * Default constructor.
     */
    public MedicationScheduleViewModel() {
    }

    /**
     * Parameterized constructor to initialize medication schedule information.
     *
     * @param customer_id       Identifier of the associated customer.
     * @param pillName          Name of the medication.
     * @param timeTakePill      Time at which the pill should be taken.
     * @param repeatIn          Interval at which the pill should be repeated (in minutes).
     * @param nrOfPillsPlaced   Number of pills placed.
     */
    public MedicationScheduleViewModel(int customer_id, String pillName, LocalDateTime timeTakePill, int repeatIn, int nrOfPillsPlaced) {
        this.customer_id = customer_id;
        this.pillName = pillName;
        this.timeTakePill = timeTakePill;
        this.repeatIn = repeatIn;
        this.nrOfPillsPlaced = nrOfPillsPlaced;
    }

    /**
     * Get the unique identifier for the medication schedule.
     *
     * @return The unique identifier for the medication schedule.
     */
    public int getMedSchedule_id() {
        return medSchedule_id;
    }

    /**
     * Set the unique identifier for the medication schedule.
     *
     * @param medSchedule_id The unique identifier for the medication schedule.
     */
    public void setMedSchedule_id(int medSchedule_id) {
        this.medSchedule_id = medSchedule_id;
    }

    /**
     * Get the identifier of the associated customer.
     *
     * @return The identifier of the associated customer.
     */
    public int getCustomer_id() {
        return customer_id;
    }

    /**
     * Get the start date of the medication schedule.
     *
     * @return The start date of the medication schedule.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Get the end date of the medication schedule.
     *
     * @return The end date of the medication schedule.
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Get the name of the medication.
     *
     * @return The name of the medication.
     */
    public String getPillName() {
        return pillName;
    }

    /**
     * Get the quantity of pills.
     *
     * @return The quantity of pills.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Get the time at which the pill should be taken.
     *
     * @return The time at which the pill should be taken.
     */
    public LocalDateTime getTimeTakePill() {
        return timeTakePill;
    }

    /**
     * Get the interval at which the pill should be repeated (in minutes).
     *
     * @return The interval at which the pill should be repeated (in minutes).
     */
    public int getRepeatIn() {
        return repeatIn;
    }

    /**
     * Get the number of pills placed.
     *
     * @return The number of pills placed.
     */
    public int getNrOfPillsPlaced() {
        return nrOfPillsPlaced;
    }

    /**
     * Set the identifier of the associated customer.
     *
     * @param customer_id The identifier of the associated customer.
     */
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    /**
     * Set the name of the medication.
     *
     * @param pillName The name of the medication.
     */
    public void setPillName(String pillName) {
        this.pillName = pillName;
    }

    /**
     * Set the time at which the pill should be taken.
     *
     * @param timeTakePill The time at which the pill should be taken.
     */
    public void setTimeTakePill(LocalDateTime timeTakePill) {
        this.timeTakePill = timeTakePill;
    }

    /**
     * Set the interval at which the pill should be repeated (in minutes).
     *
     * @param repeatIn The interval at which the pill should be repeated (in minutes).
     */
    public void setRepeatIn(int repeatIn) {
        this.repeatIn = repeatIn;
    }

    /**
     * Set the number of pills placed.
     *
     * @param nrOfPillsPlaced The number of pills placed.
     */
    public void setNrOfPillsPlaced(int nrOfPillsPlaced) {
        this.nrOfPillsPlaced = nrOfPillsPlaced;
    }

    /**
     * Provide a string representation of the object.
     *
     * @return A string representation of the MedicationScheduleViewModel object.
     */
    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedTimeTakePill = timeTakePill != null ? dateTimeFormatter.format(timeTakePill) : "n/a";

        return "MedicationScheduleViewModel{" +
                "medSchedule_id=" + medSchedule_id +
                ", customer_id=" + customer_id +
                ", pillName='" + pillName + '\'' +
                ", timeTakePill=" + formattedTimeTakePill +
                ", nrOfPillsPlaced=" + nrOfPillsPlaced +
                '}';
    }
}
