//package be.kdg.programming3.pillgate.repo.sensorRepo;
//
//import be.kdg.programming3.pillgate.domain.sensor.WeightSensor;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.transaction.Transactional;
//import org.springframework.context.annotation.Primary;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Profile("jpa")
////@Primary
//@Repository
//public class JPASensorRepository implements SensorRepository {
//
//    @PersistenceContext
//    private EntityManager em;
//
//    /* WeightSensor methods */
//
//    @Override
//    @Transactional
//    public WeightSensor createSensor(WeightSensor weightSensor) {
//        em.persist(weightSensor);
//        return weightSensor;
//    }
//
//    @Override
//    public List<WeightSensor> findAllWSensors() {
//        return em.createQuery("SELECT w FROM WeightSensor w", WeightSensor.class).getResultList();
//    }
//
//    @Override
//    public WeightSensor findSensorById(int sensor_ID) {
//        return em.find(WeightSensor.class, sensor_ID);
//    }
//
//    @Override
//    @Transactional
//    public WeightSensor updateSensor(WeightSensor existingWeightSensor) {
//        WeightSensor foundSensor = em.find(WeightSensor.class, existingWeightSensor.getSensor_ID());
//
//        if (foundSensor != null) {
//            foundSensor.setCalibrationDate(existingWeightSensor.getCalibrationDate());
//            foundSensor.setWeight(existingWeightSensor.getWeight());
//            foundSensor.setCalibrationFactor(existingWeightSensor.getCalibrationFactor());
//
//            // Use the createSensor method to persist the changes
//            return createSensor(foundSensor);
//        }
//
//        return null;
//    }
////
////    @Override
////    @Transactional
////    public WeightSensor deleteWeightSensor(int sensor_ID) {
////        WeightSensor weightSensor = em.find(WeightSensor.class, sensor_ID);
////        if (weightSensor != null) {
////            em.remove(weightSensor);
////        }
////        return weightSensor;
////    }
//}
