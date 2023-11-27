package be.kdg.programming3.domain.pillbox;

import be.kdg.programming3.domain.sensor.WeightSensor;
import be.kdg.programming3.domain.user.MedicationSchedule;
import jakarta.persistence.*;

@Entity
@Table(name = "Medicine")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "name")
    private String name;

    @Column(name = "medicine_id")
    private int medicine_id;



/*
    private transient PillBox pillBox;

*/
    @ManyToOne
    @JoinColumn(name = "weight")
    private transient WeightSensor weight;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private transient MedicationSchedule medicationSchedule;

    public Medicine(int name, String weight, double medicine_id) {
        this.name = name;
        this.weight = weight;
        this.medicine_id = medicine_id;
    }

    // to be implemented later
    public Medicine() {

    }

    public String getName() {
        return name;
    }

    public WeightSensor getWeight() {
        return weight;
    }

    public int getMedicine_id() {
        return medicine_id;
    }

    public void setMedicine_id(int medicine_id) {
        this.medicine_id = medicine_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(WeightSensor weight) {
        this.weight = weight;
    }

    public MedicationSchedule getMedicationSchedule(){ return medicationSchedule; }

    public void setMedicationSchedule() {
        this.medicationSchedule=medicationSchedule;
    }
    public void addMedicationSchedule(MedicationSchedule medicationSchedule) {

        this.medicationSchedule = medicationSchedule;

    }

/*
    public void setPillBox(PillBox pillBox) {
        this.pillBox = pillBox;
    }

    public PillBox getPillBox() {
        return pillBox;
    }
*/


    @Override
    public String toString() {
        return "Medicine{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", medicine_id=" + medicine_id +
                '}';
    }

//    public void addPillbox(PillBox pillBox) {
//        this.pillBox = pillBox;
//    }





}
