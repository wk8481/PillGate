
package be.kdg.programming3.repository;

import be.kdg.programming3.domain.pillbox.Medicine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;



@Repository
@Profile("COLLECTIONS")

public class HardCodedPillBoxRepository implements MedicineRepository {

    private Logger logger = LoggerFactory.getLogger(HardCodedPillBoxRepository.class);
    private static List<Medicine> medicines = new ArrayList<>();
//    private static List<PillBox> pillBoxes = new ArrayList<>();
    private static AtomicInteger nextId = new AtomicInteger(1);



    @Override
    public Medicine createMedicine(Medicine medicine) {
        if (medicine == null) {
            logger.error("Medicine should never be null!");
            return null;
        }
        logger.info("Creating medicine {}", medicine);
        medicine.setMedicine_id(nextId.getAndIncrement());
        medicines.add(medicine);
        return medicine;
    }


    @Override
    public List<Medicine> findAllMedicines() {
        logger.info("reading medicine from database");
     return medicines;
    }


    @Override
    public Medicine findMedicineById(int medicine_id) {
        return medicines.stream()
                .filter(medicine -> medicine.getMedicine_id() == medicine_id)
                .findFirst()
                .orElse(null);
    }


    @Override
    public Medicine updateMedicine(Medicine existingMedicine) {
        int index = -1;
        for (int i = 0; i < medicines.size(); i++) {
            if (medicines.get(i).getMedicine_id() == existingMedicine.getMedicine_id()) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            medicines.set(index, existingMedicine);
            return existingMedicine;
        } else {
            return null; // Medicine not found, return null or handle accordingly.
        }
    }

    @Override
    public Medicine deleteMedicine(int medicine_id) {
        Medicine medicineToDelete = findMedicineById(medicine_id);
        if (medicineToDelete != null) {
            medicines.remove(medicineToDelete);
            logger.info("Deleted medicine with id {}", medicine_id);
        } else {
            logger.info("Medicine with id {} not found", medicine_id);
        }
        return medicineToDelete;
    }

}

//    @Override
//    public PillBox createPillBox(PillBox pillBox) {
//        if (pillBox == null){
//            logger.error("Pillbox should never be null!");
//            return null;
//        }
//        logger.info("Creating pillbox {}", pillBox);
//        pillBox.setPillbox_id(nextId.getAndIncrement());
//        pillBoxes.add(pillBox);
//        return pillBox;
//
//    }
//
//    @Override
//    public List<PillBox> readPillBoxes() {
//        logger.info("Reading pillboxes from database...");
//        return pillBoxes;
//
//    }
//
//
//
//    @Override
//    public PillBox getPillBoxByID(int pillbox_id) {
//        return pillBoxes.stream()
//                .filter(pillBox -> pillBox.getPillbox_id() == pillbox_id)
//                .findFirst()
//                .orElse(null);
//    }
//
//    @Override
//    public PillBox updatePillBox(PillBox existingPillBox) {
//        int index = -1;
//        for (int i = 0; i < pillBoxes.size(); i++) {
//            if (pillBoxes.get(i).getPillbox_id() == existingPillBox.getPillbox_id()) {
//                index = i;
//                break;
//            }
//        }
//        if (index != -1) {
//            pillBoxes.set(index, existingPillBox);
//            return existingPillBox;
//        } else {
//            return null; // Pillbox not found, return null or handle accordingly.
//        }
//    }
