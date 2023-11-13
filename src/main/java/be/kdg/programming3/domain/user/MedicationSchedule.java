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

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setPillName(String pillName) {
        this.pillName = pillName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTimeTakePill(LocalDate timeTakePill) {
        this.timeTakePill = timeTakePill;
    }

    @Override
    public String toString() {
        return "MedicationSchedule{" +
                "customer_id=" + customer_id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", pillName='" + pillName + '\'' +
                ", quantity=" + quantity +
                ", timeTakePill=" + timeTakePill +
                '}';
    }
}
