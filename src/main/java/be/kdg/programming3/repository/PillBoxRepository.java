package be.kdg.programming3.repository;

import be.kdg.programming3.domain.pillbox.Medicine;
import be.kdg.programming3.domain.pillbox.PillBox;

import java.lang.reflect.Method;
import java.util.List;

public interface PillBoxRepository {


    /*These methods show:
     *  - PillBox Reposistory methods    */



    PillBox createPillBox(PillBox pillBox);



    List<PillBox> readPillBoxes();

    PillBox getPillBoxByID(int pillbox_id);

    PillBox updatePillBox(PillBox existingPillBox);


    /*These methdods show:
     *  - Medicine Reposistory methods    */
    Medicine createMedicine(Medicine medicine);

    List<Medicine> readMedicines();

    Medicine getMedicineById(int medicine_id);
    Medicine updateMedicine(Medicine existingMedicine);


}
