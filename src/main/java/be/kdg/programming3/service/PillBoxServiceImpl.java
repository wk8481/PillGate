package be.kdg.programming3.service;

import be.kdg.programming3.domain.pillbox.Medicine;
import be.kdg.programming3.domain.pillbox.PillBox;
import be.kdg.programming3.repository.PillBoxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PillBoxServiceImpl implements PillBoxService {

    private Logger logger = LoggerFactory.getLogger(PillBoxService.class);

    private PillBoxRepository pillBoxRepository;

    public PillBoxServiceImpl(PillBoxRepository pillBoxRepository) {
        this.pillBoxRepository = pillBoxRepository;
    }

    @Override
    public List<PillBox> getPillBoxes() {
        logger.info("Getting pillboxes");
        return pillBoxRepository.readPillBoxes();
    }

    @Override
    public List<PillBox> getPillBoxByPrice(int price) {
        logger.info("Getting pillboxes by price");
        List<PillBox> pillBoxes = pillBoxRepository.readPillBoxes();

        return pillBoxes.stream()
                .filter(pillBox -> pillBox.getPrice() == price)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * @param pillbox_id
     * @param price
     * @param pillTaken
     * @return
     */
    @Override
    public PillBox addPillBox(int pillbox_id, int price, boolean pillTaken) {
        logger.info("Adding pillbox with id {} of price {} of pillTaken {}", pillbox_id, price, pillTaken);

        // Check if a pillbox with the given ID already exists
        PillBox existingPillBox = pillBoxRepository.getPillBoxByID(pillbox_id);

        if (existingPillBox != null) {
            // A pillbox with the same ID already exists, so update their information
            existingPillBox.setPrice(price);
            existingPillBox.setPillTaken(pillTaken);
            return pillBoxRepository.updatePillBox(existingPillBox);
        } else {
            // Create a new pillbox since no pillbox with the same ID exists
            return pillBoxRepository.createPillBox(new PillBox(pillbox_id, price, pillTaken));
        }
    }

    @Override
    public List<Medicine> getMedicines() {
        logger.info("Getting medicines");
        return pillBoxRepository.readMedicines();
    }

    @Override
    public List<Medicine> getMedicineByName(String name) {
logger.info("Getting medicines by name");
        List<Medicine> medicines = pillBoxRepository.readMedicines();

        return medicines.stream()
                .filter(medicine -> medicine.getName().equals(name))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * @param name
     * @param color
     * @param shape
     * @param type
     * @param weight
     * @param medicine_id
     * @return
     */
    @Override
    public Medicine addMedicine(String name, String color, String shape, String type, double weight, int medicine_id) {
        logger.info("Adding medicine with name {} of color {} of shape {} of type {} of weight {} of medicine_id {}", name, color, shape, type, weight, medicine_id);

        // Check if a medicine with the given ID already exists
        Medicine existingMedicine = pillBoxRepository.getMedicineById(medicine_id);

        if (existingMedicine != null) {
            // A medicine with the same ID already exists, so update their information
            existingMedicine.setName(name);
            existingMedicine.setColor(color);
            existingMedicine.setShape(shape);
            existingMedicine.setType(type);
            existingMedicine.setWeight(weight);
            return pillBoxRepository.updateMedicine(existingMedicine);
        } else {
            // Create a new medicine since no medicine with the same ID exists
            return pillBoxRepository.createMedicine(new Medicine(name, color, shape, type, weight, medicine_id));
        }
    }


}
