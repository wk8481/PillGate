package be.kdg.programming3.domain.user;

import org.springframework.cglib.core.Local;

import java.rmi.registry.LocateRegistry;
import java.time.LocalDate;

public class MedicationSchedule {
    private int customer_id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String pillName;
    private int quantity;
    private LocalDate timeTakePill;


    public MedicationSchedule(int customer_id, LocalDate startDate,
                              LocalDate endDate, String pillName,
                              int quantity, LocalDate timeTakePill){
        customer_id = this.customer_id;
        startDate = this.startDate;
        endDate = this.endDate;
        pillName = this.pillName;
        quantity = this.quantity;
        timeTakePill = this.timeTakePill;
    }

    public MedicationSchedule(){}

    public int getCustomer_id() {return customer_id;}

    public LocalDate getStartDate(){return startDate;}

    public LocalDate getEndDate(){return endDate;}

    public String getPillName(){return pillName;}

    public int getQuantity(){return quantity;}

    public LocalDate getTimeTakePill(){return timeTakePill;}



}
