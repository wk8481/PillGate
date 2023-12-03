package be.kdg.programming3.oldproj.domain.user;



import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customer_id;
    private String customer_name;
    private LocalDate birthDate;
    private String email;
    private boolean hasCareGiver;
    private String password;

    @ManyToMany
    @JoinTable(
            name = "CustomerCareGiver",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "caregiver_id")
    )
    private List<CareGiver> careGivers = new ArrayList<>();
    private transient List<MedicationSchedule> medicationSchedules;
//    private transient List<Medicine> medicines;

    public Customer(int customer_id, String customer_name, LocalDate birthDate, String email, boolean hasCareGiver, String password) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.birthDate = birthDate;
        this.email = email;
        this.hasCareGiver = hasCareGiver;
        this.password = password;
    }

    public Customer(int customer_id) {
        //implemented later
        this.customer_id = customer_id;
    }

    //security


    public Customer(String customer_name, LocalDate birthDate, String email, String password) {
        this.customer_name = customer_name;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
    }

    public Customer(){

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

    public LocalDate getBirthDate() {
        return birthDate;
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

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCareGivers(List<CareGiver> careGivers) {
        this.careGivers = careGivers;
    }

    public List<MedicationSchedule> getMedicationSchedules() {
        return medicationSchedules;
    }

    public void setMedicationSchedules(List<MedicationSchedule> medicationSchedules) {
        this.medicationSchedules=medicationSchedules;
    }

//    public List<Medicine> getMedicines() {
//        return medicines;
//    }
//
//    public void setMedicines(List<Medicine> medicines) {
//        this.medicines = medicines;
//    }

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

//    public void addMedicine(Medicine medicine) {
//        if (medicines == null) medicines = new ArrayList<>();
//        medicines.add(medicine);
//    }
}
