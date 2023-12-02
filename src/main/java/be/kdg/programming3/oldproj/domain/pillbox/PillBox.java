//package be.kdg.programming3.domain.pillbox;
//
//import be.kdg.programming3.domain.sensor.WeightSensor;
//import be.kdg.programming3.domain.user.Customer;
//
//import java.util.ArrayList;
//import java.util.List;
//
////No store in database
//public class PillBox {
////    private int pillbox_id;
////    private int price;
//    private boolean pillTaken;
////    private Compartment compartment;
//
//    private transient List<Medicine> medicines;
//    private transient WeightSensor weightSensor;
//
//
//    public PillBox(int pillbox_id, int price, boolean pillTaken) {
//        this.pillbox_id = pillbox_id;
//        this.price = price;
//        this.pillTaken = pillTaken;
//    }
//
//    public PillBox(){}
//
//    public int getPillbox_id() {
//        return pillbox_id;
//    }
//
//    public void setPillbox_id(int pillbox_id) {
//        this.pillbox_id = pillbox_id;
//    }
//
//    public int getPrice() {
//        return price;
//    }
//
//    public void setPrice(int price) {
//        this.price = price;
//    }
//
//    public boolean isPillTaken() {
//        return pillTaken;
//    }
//
//    public void setPillTaken(boolean pillTaken) {
//        this.pillTaken = pillTaken;
//    }
//
//    public List<Medicine> getMedicines() {
//        return medicines;
//    }
//
//    public void setMedicines(List<Medicine> medicines) {
//        this.medicines = medicines;
//    }
//
//    public WeightSensor getWeightSensor() {
//        return weightSensor;
//    }
//
//    public void setWeightSensor(WeightSensor weightSensor) {
//        this.weightSensor = weightSensor;
//    }
//    //    public Compartment getCompartment() {
////        return compartment;
////    }
////
////    public void setCompartment(Compartment compartment) {
////        this.compartment = compartment;
////    }
//
//    @Override
//    public String toString() {
//        return "PillBox{" +
//                "pillbox_id=" + pillbox_id +
//                ", price=" + price +
//                ", pillTaken=" + pillTaken +
//                '}';
//    }
//
//    public void addMedicine(Medicine medicine) {
//        if (medicines == null) medicines = new ArrayList<>();
//        medicines.add(medicine);
//    }
//
//    public void addWeightSensor(WeightSensor weightSensor){
//        this.weightSensor = weightSensor;
//    }
//
//
//}
