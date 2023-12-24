package be.kdg.programming3.pillgate.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Represents a CareGiver entity in the PillGate system.
 * CareGivers are users who can manage the medication schedules of multiple customers.
 *
 * @author Team PillGate
 */
//@Entity
//@Table(name = "CareGiver")
@Component
public class  CareGiver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int caregiver_id;

    private String caregiver_name;
    private String email;

    // transient keyword to exclude this field from serialization
    private transient List<Customer> customers;

    /**
     * Constructs a CareGiver with specified properties.
     *
     * @param caregiver_id   The unique identifier of the CareGiver.
     * @param caregiver_name The name of the CareGiver.
     * @param email          The email address of the CareGiver.
     * @author Team PillGate
     */
    public CareGiver(int caregiver_id, String caregiver_name, String email) {
        this.caregiver_id = caregiver_id;
        this.caregiver_name = caregiver_name;
        this.email = email;
    }

    /**
     * Default constructor for CareGiver.
     * @author Team PillGate
     */
    public CareGiver() {
        // implemented later
    }

    /**
     * Gets the unique identifier of the CareGiver.
     *
     * @return The CareGiver's unique identifier.
     */
    public int getCaregiver_id() {
        return caregiver_id;
    }

    /**
     * Sets the unique identifier of the CareGiver.
     *
     * @param caregiver_id The new unique identifier for the CareGiver.
     */
    public void setCaregiver_id(int caregiver_id) {
        this.caregiver_id = caregiver_id;
    }

    /**
     * Gets the name of the CareGiver.
     *
     * @return The name of the CareGiver.
     */
    public String getCaregiver_name() {
        return caregiver_name;
    }

    /**
     * Sets the name of the CareGiver.
     *
     * @param caregiver_name The new name for the CareGiver.
     */
    public void setCaregiver_name(String caregiver_name) {
        this.caregiver_name = caregiver_name;
    }

    /**
     * Gets the email address of the CareGiver.
     *
     * @return The email address of the CareGiver.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the CareGiver.
     *
     * @param email The new email address for the CareGiver.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the list of customers associated with the CareGiver.
     *
     * @return The list of customers associated with the CareGiver.
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Sets the list of customers associated with the CareGiver.
     *
     * @param customers The new list of customers for the CareGiver.
     */
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    /**
     * Adds a customer to the list of customers associated with the CareGiver.
     *
     * @param customer The customer to be added.
     */
    public void addCustomer(Customer customer) {
        this.customers.add(customer);
        customer.getCareGivers().add(this);
    }

    /**
     * Returns a string representation of the CareGiver.
     *
     * @return A string representation of the CareGiver.
     */
    @Override
    public String toString() {
        return "CareGiver{" +
                "caregiver_id=" + caregiver_id +
                ", caregiver_name='" + caregiver_name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
