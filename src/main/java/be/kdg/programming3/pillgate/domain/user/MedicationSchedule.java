package be.kdg.programming3.pillgate.domain.user;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a medication schedule for a specific customer.
 * This class encapsulates information about the customer's prescribed medication,
 * including pill details, dosage schedule, and tracking of medication taken.
 * @author Team PillGate
 */
@Component
public class MedicationSchedule {

    private int medSchedule_id;
    private String pillName;
    private LocalDateTime timeTakePill;

    private int repeatIn;
    private int nrOfPillsPlaced;
    private double weightOfSinglePill;
    private int nrOfPillsTaken;
    private boolean isStopped;
    private String message;
    private transient Customer customer;


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

    public MedicationSchedule(int medSchedule_id, String pillName, LocalDateTime timeTakePill, int repeatIn, int nrOfPillsPlaced, boolean isStopped, String message, Customer customer) {
        this.medSchedule_id = medSchedule_id;
        this.pillName = pillName;
        this.timeTakePill = timeTakePill;
        this.repeatIn = repeatIn;
        this.isStopped = isStopped;
        this.message = message;
        this.nrOfPillsPlaced = nrOfPillsPlaced;
        this.customer = customer;

    }




    public MedicationSchedule() {

    }

    public int getMedSchedule_id() {
        return medSchedule_id;
    }

    public void setMedSchedule_id(int medSchedule_id) {
        this.medSchedule_id = medSchedule_id;
    }

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



    /**
     * Checks whether at least one pill has been taken.
     * Currently not used, however it might be useful in the future.
     * @return True if at least one pill has been taken, false otherwise.
     */
    public boolean isPillTaken() {
        // Check if at least one pill has been taken and nrOfPillsPlaced has decreased
        return nrOfPillsTaken > 0 && nrOfPillsPlaced < nrOfPillsTaken || weightOfSinglePill == 0.0;
    }


    /**
     * Returns a formatted string representation of the MedicationSchedule.
     * @return A string containing the medication schedule details.
     */
    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");

        String formattedDate3 = dtf.format(timeTakePill);

        return "MedicationSchedule{" +
                "medSchedule_id=" + medSchedule_id +
                ", customer_id=" + customer.getCustomer_id() +
                ", pillName='" + pillName + '\'' +
                ", timeTakePill=" + formattedDate3 +
                ", repeatIn=" + repeatIn +
                ", nrOfPillsPlaced=" + nrOfPillsPlaced +
                ", nrOfPillsTaken=" + nrOfPillsTaken +
                ", weightOfSinglePill=" + weightOfSinglePill +
                '}';


    }

}

