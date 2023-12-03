package be.kdg.programming3.pillgate.domain.user;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.stereotype.Component;


import java.util.List;
//
//@Entity
//@Table(name = "CareGiver")
//@Component
public class CareGiver {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int caregiver_id;


    private String caregiver_name;
    private String email;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    @ManyToMany(mappedBy = "careGivers")
    private transient List<Customer> customers;

    public CareGiver(int caregiver_id, String caregiver_name, String email ) {
        this.caregiver_id = caregiver_id;
        this.caregiver_name = caregiver_name;
        this.email = email;
    }

    public CareGiver() {
        //implemented later
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

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCaregiver_name(String caregiver_name) {
        this.caregiver_name = caregiver_name;
    }


    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        return "CareGiver{" +
                "caregiver_id=" + caregiver_id +
                ", caregiver_name='" + caregiver_name + '\'' +
                ", email=" + email +
                '}';
    }

    public void addCustomer(Customer customer) {

        this.customers.add(customer);
        customer.getCareGivers().add(this);
    }



}
