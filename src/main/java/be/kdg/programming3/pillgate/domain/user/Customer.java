package be.kdg.programming3.pillgate.domain.user;



import be.kdg.programming3.pillgate.domain.sensor.WeightSensor;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



@Component
//@Entity
//@Table(name = "Customer")
public class Customer {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customer_id;



    private String customer_name;
    private LocalDate birthDate;
    private String email;
    private boolean hasCareGiver;
    private String password;

//    @ManyToMany(mappedBy = "customers")

//    @JoinTable(
//            name = "CustomerCareGiver",
//            joinColumns = @JoinColumn(name = "customer_id"),
//            inverseJoinColumns = @JoinColumn(name = "caregiver_id")
//    )



    private transient List<CareGiver> careGivers = new ArrayList<>();

//    @OneToMany
//    @JoinColumn(name = "customer_id")
    private transient List<MedicationSchedule> medicationSchedules;

//    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private transient Dashboard dashboard;

//    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private transient WeightSensor weightSensor;


    public Customer(String customer_name, String password) {
        this.customer_name = customer_name;
        this.password = password;
    }
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

    public WeightSensor getWeightSensor() {
        return weightSensor;
    }

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

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
        dashboard.setCustomer(this);
    }


    public void setWeightSensor(WeightSensor weightSensor) {
        this.weightSensor = weightSensor;
        weightSensor.setCustomer(this);
    }


}
