package be.kdg.programming3.domain.user;

import org.springframework.cglib.core.Local;

import java.rmi.registry.LocateRegistry;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MedicationSchedule {
    private int medSchedule_id;
    private int customer_id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String pillName;
    private int quantity;
    private LocalDate timeTakePill;

    private transient Customer customer;
    private transient Dashboard dashboard;


    public MedicationSchedule(int customer_id, LocalDate startDate,
                              LocalDate endDate, String pillName,
                              int quantity, LocalDate timeTakePill) {
        customer_id = this.customer_id;
        startDate = this.startDate;
        endDate = this.endDate;
        pillName = this.pillName;
        quantity = this.quantity;
        timeTakePill = this.timeTakePill;
    }

    public MedicationSchedule() {
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getPillName() {
        return pillName;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getTimeTakePill() {
        return timeTakePill;
    }

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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    public void addCustomer(Customer customer) {
        this.customer = customer;
    }

    public void addDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String formattedDate = dtf.format(startDate);
        String formattedDate2 = dtf.format(endDate);
        String formattedDate3 = dtf.format(timeTakePill);

        return "MedicationSchedule{" +
                "medSchedule_id=" + medSchedule_id +
                ", customer_id=" + customer_id +
                ", startDate=" + formattedDate +
                ", endDate=" + formattedDate2 +
                ", pillName='" + pillName + '\'' +
                ", quantity=" + quantity +
                ", timeTakePill=" + formattedDate3 +
                '}';


    }
}

