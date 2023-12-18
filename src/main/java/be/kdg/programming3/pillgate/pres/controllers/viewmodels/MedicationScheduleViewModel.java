package be.kdg.programming3.pillgate.pres.controllers.viewmodels;


import be.kdg.programming3.pillgate.domain.user.Customer;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class MedicationScheduleViewModel {

    private int medSchedule_id;

    @Positive(message = "customer_id must be a positive number")
    private int customer_id;

    @NotNull(message = "startDate cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "endDate cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotBlank(message = "pillName should not be empty")
    private String pillName;

    @Positive(message = "quantity must be a positive number")
    private int quantity;

    @NotNull(message = "timeTakePill cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")

    private LocalDateTime timeTakePill;

    @PositiveOrZero(message = "repeatIn must be a positive number or zero")
    private int repeatIn;

    @PositiveOrZero(message = "nrOfPillsPlaced must be a positive number or zero")
    private int nrOfPillsPlaced;

//    private transient Customer customer;
//    private transient Dashboard dashboard;
//    private transient List<Medicine> medicines = new ArrayList<>();


    public MedicationScheduleViewModel(int customer_id, String pillName, LocalDateTime timeTakePill, int repeatIn, int nrOfPillsPlaced) {
        this.customer_id = customer_id;
        this.pillName = pillName;
        this.timeTakePill = timeTakePill;
        this.repeatIn = repeatIn;
        this.nrOfPillsPlaced = nrOfPillsPlaced;

    }




    public MedicationScheduleViewModel() {
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

    public int getRepeatIn() { return repeatIn;}

    public int getNrOfPillsPlaced(){
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

    public void setRepeatIn(int repeatIn) { this.repeatIn = repeatIn;}

    public void setNrOfPillsPlaced(int nrOfPillsPlaced){
        this.nrOfPillsPlaced=nrOfPillsPlaced;
    }


    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedTimeTakePill = timeTakePill != null ? dateTimeFormatter.format(timeTakePill) : "n/a";

        return "MedicationSchedule{" +
                "medSchedule_id=" + medSchedule_id +
                ", customer_id=" + customer_id +
                ", pillName='" + pillName + '\'' +
                ", timeTakePill=" + formattedTimeTakePill +
                ", nrOfPillsPlaced=" + nrOfPillsPlaced +
                '}';

    }

}

