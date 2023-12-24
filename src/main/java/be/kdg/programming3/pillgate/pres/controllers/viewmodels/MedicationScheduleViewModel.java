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
    private int customer_id;

    /**
     * Name of the medication.
     */
    @NotBlank(message = "pillName should not be empty")
    private String pillName;

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


    public int getMedSchedule_id() {
        return medSchedule_id;
    }

    public void setMedSchedule_id(int medSchedule_id) {
        this.medSchedule_id = medSchedule_id;
    }


    public int getCustomer_id() {
        return customer_id;
    }

    public String getPillName() {
        return pillName;
    }

    public LocalDateTime getTimeTakePill() {
        return timeTakePill;
    }

    public int getRepeatIn() {
        return repeatIn;
    }


    public int getNrOfPillsPlaced() {
        return nrOfPillsPlaced;
    }


    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }


    public void setPillName(String pillName) {
        this.pillName = pillName;
    }


    public void setTimeTakePill(LocalDateTime timeTakePill) {
        this.timeTakePill = timeTakePill;
    }


    public void setRepeatIn(int repeatIn) {
        this.repeatIn = repeatIn;
    }

    public void setNrOfPillsPlaced(int nrOfPillsPlaced) {
        this.nrOfPillsPlaced = nrOfPillsPlaced;
    }

    /**
     * Provide a string representation of the object.
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
