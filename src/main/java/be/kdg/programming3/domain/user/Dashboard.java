package be.kdg.programming3.domain.user;

import be.kdg.programming3.domain.pillbox.Medicine;
import jdk.dynalink.linker.LinkerServices;

import java.util.ArrayList;
import java.util.List;

public class Dashboard {
    private int dashboard_id;
    private int nrPillTaken;
    private int duration;
    private transient List<MedicationSchedule> medicationSchedules;

    public Dashboard(int dashboard_id, int nrPillTaken, int duration) {
        this.dashboard_id = dashboard_id;
        this.nrPillTaken = nrPillTaken;
        this.duration = duration;

    }

    public  Dashboard(){}

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

    @Override
    public String toString() {
        return "Dashboard{" +
                "dashboard_id=" + dashboard_id +
                ", nrPillTaken=" + nrPillTaken +
                ", duration=" + duration +
                '}';
    }



    public void addMedicationSchedule(MedicationSchedule medicationSchedule) {
      if(medicationSchedules == null) medicationSchedules = new ArrayList<>();
      medicationSchedules.add(medicationSchedule);
      }
    }






