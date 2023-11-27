package be.kdg.programming3.domain.user;

import be.kdg.programming3.domain.pillbox.Medicine;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customer_id;
    private String customer_name;
    private int age;
    private String email;
    private boolean hasCareGiver;

    @ManyToMany
    @JoinTable(
            name = "CustomerCareGiver",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "caregiver_id")
    )
    private List<CareGiver> careGivers = new ArrayList<>();
    private transient List<MedicationSchedule> medicationSchedules;
    private transient List<Medicine> medicines;

    public Customer(int customer_id, String customer_name, int age, String email, boolean hasCareGiver) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.age = age;
        this.email = email;
        this.hasCareGiver = hasCareGiver;
    }

    public Customer() {
        //implemented later
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public boolean isHasCareGiver() {
        return hasCareGiver;
    }

    public List<CareGiver> getCareGivers() {
        return careGivers;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHasCareGiver(boolean hasCareGiver) {
        this.hasCareGiver = hasCareGiver;
    }

    public void setCareGiver(CareGiver careGiver) {
        this.careGivers = careGivers;
    }

    public List<MedicationSchedule> getMedicationSchedules() {
        return medicationSchedules;
    }

    public void setMedicationSchedules(List<MedicationSchedule> medicationSchedules) {
        this.medicationSchedules=medicationSchedules;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customer_id=" + customer_id +
                ", customer_name='" + customer_name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", hasCareGiver=" + hasCareGiver +
                '}';
    }


//    public boolean addCaregiver(CareGiver careGiver) {
//        // Check if the customer already has a caregiver
//        if (!hasCareGiver) {
//            this.careGiver = careGiver;
//            hasCareGiver = true; // Set hasCareGiver to true since now the customer has a caregiver
//            return true; // Return true indicating that the caregiver was added
//        } else {
//            return false; // Return false indicating that the customer already has a caregiver
//        }
//    }

    public void addCaregiver(CareGiver careGiver) {
        // Check if the customer already has a caregiver
        if (!hasCareGiver) {
            this.careGivers.add(careGiver);
            careGiver.addCustomer(this);
            hasCareGiver = true; // Set hasCareGiver to true since now the customer has a caregiver
        }
        // No return statement here
    }

    public void addMedicationSchedule(MedicationSchedule medicationSchedule) {
        if (medicationSchedule == null) medicationSchedules = new ArrayList<>();
        medicationSchedules.add(medicationSchedule);
    }

    public void addMedicine(Medicine medicine) {
        if (medicines == null) medicines = new ArrayList<>();
        medicines.add(medicine);
    }
}
