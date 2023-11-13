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


}
