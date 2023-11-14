package be.kdg.programming3.domain.user;

public class Dashboard {
    private int dashboard_id;
    private int nrPillTaken;
    private int duration;
    private MedicationSchedule medicationSchedule;

    public Dashboard(int dashboard_id, int nrPillTaken, int duration, MedicationSchedule medicationSchedule){
        this.dashboard_id = dashboard_id;
        nrPillTaken = this.nrPillTaken;
        duration = this.duration;
        medicationSchedule = this.medicationSchedule;
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

    public MedicationSchedule getMedicationSchedule() {
        return medicationSchedule;
    }

    public void setMedicationSchedule(MedicationSchedule medicationSchedule) {
        this.medicationSchedule = medicationSchedule;
    }


    @Override
    public String toString() {
        return "Dashboard{" +
                "nrPillTaken=" + nrPillTaken +
                ", duration=" + duration +
                ", medicationSchedule=" + medicationSchedule +
                '}';
    }

}
