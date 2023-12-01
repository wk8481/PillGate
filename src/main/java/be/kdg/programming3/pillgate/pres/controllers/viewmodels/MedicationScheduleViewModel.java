package be.kdg.programming3.pillgate.pres.controllers.viewmodels;


import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class MedicationScheduleViewModel {

    private int medSchedule_id;
    private int customer_id;

    private String pillName;
    private int quantity;
    private LocalDateTime timeTakePill;
    private int repeatIn;

//    private transient Customer customer;
//    private transient Dashboard dashboard;
//    private transient List<Medicine> medicines = new ArrayList<>();


    public MedicationScheduleViewModel(String pillName, LocalDateTime timeTakePill, int repeatIn) {

        this.pillName = pillName;
 //       this.quantity = quantity;
        this.timeTakePill = timeTakePill;
        this.repeatIn = repeatIn;

    }

    public MedicationScheduleViewModel() {
    }

    public int getMedSchedule_id() {
        return medSchedule_id;
    }

    public void setMedSchedule_id(int medSchedule_id) {
        this.medSchedule_id = medSchedule_id;
    }


    public int getCustomer_id() {
        return customer_id;
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

    public int getRepeatIn() { return repeatIn;}

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
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

    public void setRepeatIn(int repeatIn) { this.repeatIn = repeatIn;}

/*    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }*/

//    public void addMedicine(Medicine medicine) {
//        if (this.medicines == null){
//            this.medicines = new ArrayList<>();
//        }
//        this.medicines.add(medicine);
//
//    }

/*    public void addCustomer(Customer customer) {
        this.customer = customer;
    }

    public void addDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }*/

    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

//        String formattedDate = dtf.format(startDate);
//        String formattedDate2 = dtf.format(endDate);
        String formattedDate3 = dtf.format(timeTakePill);

        return "MedicationSchedule{" +
                "medSchedule_id=" + medSchedule_id +
                ", customer_id=" + customer_id +
//                ", startDate=" + formattedDate +
//                ", endDate=" + formattedDate2 +
                ", pillName='" + pillName + '\'' +
                ", quantity=" + quantity +
                ", timeTakePill=" + formattedDate3 +
                '}';


    }
}
