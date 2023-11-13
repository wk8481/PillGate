package be.kdg.programming3.domain.user;

public class Dashboard {
    private int nrPillTaken;
    private int duration;
    private MedicationSchedule medicationSchedule;

    public Dashboard(int nrPillTaken, int duration, MedicationSchedule medicationSchedule){
        nrPillTaken = this.nrPillTaken;
        duration = this.duration;
        medicationSchedule = this.medicationSchedule;
    }

    public  Dashboard(){}

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
