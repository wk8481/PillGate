package be.kdg.programming3.pillgate.domain.user;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class MedicationSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int medSchedule_id;

    private LocalDate startDate;
    private LocalDate endDate;
    private String pillName;
    private int quantity;
    private LocalDateTime timeTakePill;
    private int repeatIn;

    private int nrOfPillsPlaced;
    private double weightOfSinglePill;
    private int nrOfPillsTaken;

    private boolean isStopped;
    private String message;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private transient Customer customer;

    @ManyToOne
    @JoinColumn(name = "dashboard_id")
    private transient Dashboard dashboard;



    public MedicationSchedule(Customer customer, LocalDate startDate,
                              LocalDate endDate, String pillName,
                              int quantity, LocalDateTime timeTakePill, int repeatIn, int nrOfPillsPlaced, double weightOfSinglePill, int nrOfPillsTaken) {
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pillName = pillName;
        this.quantity = quantity;
        this.timeTakePill = timeTakePill;
        this.repeatIn = repeatIn;
        this.nrOfPillsPlaced = nrOfPillsPlaced;
        this.weightOfSinglePill = weightOfSinglePill;
        this.nrOfPillsTaken = nrOfPillsTaken;
    }

    public MedicationSchedule(int medSchedule_id, LocalDate startDate, LocalDate endDate, String pillName, int quantity, LocalDateTime timeTakePill, int repeatIn, int nrOfPillsPlaced, boolean isStopped, String message, Customer customer, Dashboard dashboard) {
        this.medSchedule_id = medSchedule_id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pillName = pillName;
        this.quantity = quantity;
        this.timeTakePill = timeTakePill;
        this.repeatIn = repeatIn;
        this.isStopped = isStopped;
        this.message = message;
        this.nrOfPillsPlaced = nrOfPillsPlaced;
        this.customer = customer;
        this.dashboard = dashboard;
    }


    public MedicationSchedule() {

    }

    public int getMedSchedule_id() {
        return medSchedule_id;
    }

    public void setMedSchedule_id(int medSchedule_id) {
        this.medSchedule_id = medSchedule_id;
    }



/*    public int getCustomer_id() {
        return customer_id;
    }*/

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

    public LocalDateTime getTimeTakePill() {
        return timeTakePill;
    }

    public int getRepeatIn(){
        return repeatIn;
    }

    public int getNrOfPillsPlaced(){
        return nrOfPillsPlaced;
    }
    public double getWeightOfSinglePill() {
        return weightOfSinglePill;
    }
    public int getNrOfPillsTaken(){
        return nrOfPillsTaken;
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

    public void setTimeTakePill(LocalDateTime timeTakePill) {
        this.timeTakePill = timeTakePill;
    }

    public void setRepeatIn(int repeatIn){
        this.repeatIn = repeatIn;
    }

    public void setNrOfPillsPlaced(int nrOfPillsPlaced){
        this.nrOfPillsPlaced = nrOfPillsPlaced;
    }
    public void setWeightOfSinglePill(double weightOfSinglePill) {
        this.weightOfSinglePill = weightOfSinglePill;
    }
    public void setNrOfPillsTaken(int nrOfPillsTaken){
        this.nrOfPillsTaken = nrOfPillsTaken;
    }
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getCustomer_id() {
        return customer != null ? customer.getCustomer_id() : null;
    }




    public boolean isStopped() {return isStopped;}

    public void setStopped(boolean stopped) {isStopped = stopped;}

    public String getMessage() {return message;}

    public void setMessage(String message) {this.message = message;}



    public void addCustomer(Customer customer) {
        this.customer = customer;
    }

    public void addDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public void setDashboard(Dashboard dashboard) {this.dashboard = dashboard;}


    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String formattedDate = dtf.format(startDate);
        String formattedDate2 = dtf.format(endDate);
        String formattedDate3 = dtf.format(timeTakePill);

        return "MedicationSchedule{" +
                "medSchedule_id=" + medSchedule_id +
                ", customer_id=" + customer.getCustomer_id() +
                ", startDate=" + formattedDate +
                ", endDate=" + formattedDate2 +
                ", pillName='" + pillName + '\'' +
                ", quantity=" + quantity +
                ", timeTakePill=" + formattedDate3 +
                ", repeatIn=" + repeatIn +
                ", nrOfPillsPlaced=" + nrOfPillsPlaced +
                '}';


    }

}

