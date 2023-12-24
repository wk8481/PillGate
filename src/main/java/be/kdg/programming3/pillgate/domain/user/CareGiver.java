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
 * @author Team PillGate
 */

@Component
public class  CareGiver {


    private int caregiver_id;

    private String caregiver_name;
    private String email;

    private transient List<Customer> customers;

    /**
     * Constructs a CareGiver with specified properties.
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


    public int getCaregiver_id() {
        return caregiver_id;
    }


    public void setCaregiver_id(int caregiver_id) {
        this.caregiver_id = caregiver_id;
    }


    public String getCaregiver_name() {
        return caregiver_name;
    }


    public void setCaregiver_name(String caregiver_name) {
        this.caregiver_name = caregiver_name;
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public List<Customer> getCustomers() {
        return customers;
    }


    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    /**
     * Adds a customer to the list of customers associated with the CareGiver.
     * @param customer The customer to be added.
     */
    public void addCustomer(Customer customer) {
        this.customers.add(customer);
        customer.getCareGivers().add(this);
    }

    /**
     * Returns a string representation of the CareGiver.
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
