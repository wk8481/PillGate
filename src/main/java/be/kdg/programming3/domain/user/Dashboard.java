package be.kdg.programming3.domain.user;

import be.kdg.programming3.domain.pillbox.Medicine;
import jakarta.persistence.*;
import jdk.dynalink.linker.LinkerServices;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Dashboard")
public class Dashboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int dashboard_id;
    private int nrPillTaken;
    private int duration;
    private transient List<MedicationSchedule> medicationSchedules;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private transient Customer customer;

    public Dashboard(int dashboard_id, int nrPillTaken, int duration, Customer customer) {
        this.dashboard_id = dashboard_id;
        this.nrPillTaken = nrPillTaken;
        this.duration = duration;
        this.customer = customer;
    }

    public  Dashboard(){
    }

    public int getDashboard_id() {
        return dashboard_id;
    }

    public void setDashboard_id(int dashboard_id) {
        this.dashboard_id = dashboard_id;
    }

    public int getNrPillTaken() {
        return nrPillTaken;
    }

    public void setNrPillTaken(int nrPillTaken) {
        this.nrPillTaken = nrPillTaken;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<MedicationSchedule> getMedicationSchedules() {
        return medicationSchedules;
    }

    public void setMedicationSchedules(List<MedicationSchedule> medicationSchedules) {
        this.medicationSchedules = medicationSchedules;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer=customer;
    }

    @Override
    public String toString() {
        return "Dashboard{" +
                "dashboard_id=" + dashboard_id +
                ", nrPillTaken=" + nrPillTaken +
                ", duration=" + duration +
                ", customer=" + customer +
                '}';
    }



    public void addMedicationSchedule(MedicationSchedule medicationSchedule) {
//   t
        this.medicationSchedules.add(medicationSchedule);
        medicationSchedule.addDashboard(this);


      }
    }






