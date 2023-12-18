package be.kdg.programming3.pillgate.domain.user;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class MedicationSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int medSchedule_id;

    private String pillName;
    private LocalDateTime timeTakePill;

// We can use this instead of localdatetime to only show the time not the date
  //  private LocalTime timeTakePill;
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



    public MedicationSchedule(Customer customer, String pillName,
                              LocalDateTime timeTakePill, int repeatIn, int nrOfPillsPlaced,
                              double weightOfSinglePill, int nrOfPillsTaken) {
        this.customer = customer;
        this.pillName = pillName;
        this.timeTakePill = timeTakePill;
        this.repeatIn = repeatIn;
        this.nrOfPillsPlaced = nrOfPillsPlaced;
        this.weightOfSinglePill = weightOfSinglePill;
        this.nrOfPillsTaken = nrOfPillsTaken;
    }

    public MedicationSchedule(int medSchedule_id, String pillName, LocalDateTime timeTakePill, int repeatIn, int nrOfPillsPlaced, boolean isStopped, String message, Customer customer, Dashboard dashboard) {
        this.medSchedule_id = medSchedule_id;
        this.pillName = pillName;
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


    public String getPillName() {
        return pillName;
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

    public void setPillName(String pillName) {
        this.pillName = pillName;
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

    public void setCustomer_id(int customer_id) {
        this.customer = new Customer(customer_id);
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

        String formattedDate3 = dtf.format(timeTakePill);

        return "MedicationSchedule{" +
                "medSchedule_id=" + medSchedule_id +
                ", customer_id=" + customer.getCustomer_id() +
                ", pillName='" + pillName + '\'' +
                ", timeTakePill=" + formattedDate3 +
                ", repeatIn=" + repeatIn +
                ", nrOfPillsPlaced=" + nrOfPillsPlaced +
                '}';


    }

}

