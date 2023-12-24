package be.kdg.programming3.pillgate.domain.user;



import be.kdg.programming3.pillgate.domain.sensor.WeightSensorData;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents a customer in the PillGate application.
 * Each customer has a unique identifier, personal information, medication schedules, caregivers, and other related attributes.
 * This class is annotated with {@link org.springframework.stereotype.Component} to indicate that it is a Spring component.
 * @author Team PillGate
 */
@Component
public class Customer {

    private int customer_id;
    private String customer_name;
    private LocalDate birthDate;
    private String email;
    private boolean hasCareGiver;
    private String password;

    private transient List<CareGiver> careGivers = new ArrayList<>();


    private transient List<MedicationSchedule> medicationSchedules;

    private transient WeightSensorData weightSensorData;

    /**
     * Constructs a customer with the specified name and password.
     * @param customer_name The name of the customer.
     * @param password      The password associated with the customer.
     */

    public Customer(String customer_name, String password) {
        this.customer_name = customer_name;
        this.password = password;
    }
    /**
     * Constructs a customer with the specified attributes.
     * @param customer_id   The unique identifier of the customer.
     * @param customer_name The name of the customer.
     * @param birthDate     The birth date of the customer.
     * @param email         The email address of the customer.
     * @param hasCareGiver  A flag indicating whether the customer has a caregiver.
     * @param password      The password associated with the customer.
     */
    public Customer(int customer_id, String customer_name, LocalDate birthDate, String email, boolean hasCareGiver, String password) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.birthDate = birthDate;
        this.email = email;
        this.hasCareGiver = hasCareGiver;
        this.password = password;
    }
    /**
     * Constructs a customer with the specified identifier.
     * @param customer_id The unique identifier of the customer.
     */
    public Customer(int customer_id) {
        //implemented later
        this.customer_id = customer_id;
    }

    /**
     * Constructs a customer with the specified personal information.
     * @param customer_name The name of the customer.
     * @param birthDate     The birth date of the customer.
     * @param email         The email address of the customer.
     * @param password      The password associated with the customer.
     */
    public Customer(String customer_name, LocalDate birthDate, String email, String password) {
        this.customer_name = customer_name;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
    }
    /**
     * Default constructor for the Customer class.
     */
    public Customer(){

    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isHasCareGiver() {
        return hasCareGiver;
    }

    public void setHasCareGiver(boolean hasCareGiver) {
        this.hasCareGiver = hasCareGiver;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<CareGiver> getCareGivers() {
        return careGivers;
    }

    public void setCareGivers(List<CareGiver> careGivers) {
        this.careGivers = careGivers;
    }

    public List<MedicationSchedule> getMedicationSchedules() {
        return medicationSchedules;
    }

    public void setMedicationSchedules(List<MedicationSchedule> medicationSchedules) {
        this.medicationSchedules = medicationSchedules;
    }

    /**
     * Gets the weight sensor associated with the customer.
     * @return The {@link WeightSensorData} associated with the customer.
     */
    public WeightSensorData getWeightSensor() {
        return weightSensorData;
    }
    /**
     * Converts the customer object to a formatted string.
     * @return A formatted string representation of the customer object.
     */
    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String formattedDate = dtf.format(birthDate);

        return "Customer{" +
                "customer_id=" + customer_id +
                ", customer_name='" + customer_name + '\'' +
                ", birthDate=" + formattedDate +
                ", email='" + email + '\'' +
                ", hasCareGiver=" + hasCareGiver +
                '}';
    }

    /**
     * Adds a caregiver to the customer's list of caregivers.
     * Sets the hasCareGiver flag to true if the customer did not have a caregiver before.
     *
     * @param careGiver The caregiver to be added.
     */

    public void addCaregiver(CareGiver careGiver) {
        // Check if the customer already has a caregiver
        if (!hasCareGiver) {
            this.careGivers.add(careGiver);
            careGiver.addCustomer(this);
            hasCareGiver = true; // Set hasCareGiver to true since now the customer has a caregiver
        }
        // No return statement here
    }
    /**
     * Adds a medication schedule to the customer's list of medication schedules.
     *
     * @param medicationSchedule The medication schedule to be added.
     */
    public void addMedicationSchedule(MedicationSchedule medicationSchedule) {
        if (medicationSchedule == null) medicationSchedules = new ArrayList<>();
        medicationSchedules.add(medicationSchedule);
    }


    /**
     * Sets the weight sensor associated with the customer.
     *
     * @param weightSensorData The weight sensor to be associated with the customer.
     */

    public void setWeightSensor(WeightSensorData weightSensorData) {
        this.weightSensorData = weightSensorData;
        weightSensorData.setCustomer(this);
    }


}
