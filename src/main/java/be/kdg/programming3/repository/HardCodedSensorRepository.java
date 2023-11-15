package be.kdg.programming3.repository;

import be.kdg.programming3.domain.sensor.WeightSensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class HardCodedSensorRepository implements SensorRepository {
    private Logger logger = LoggerFactory.getLogger(HardCodedSensorRepository.class);
    private static List<WeightSensor> weightSensors = new ArrayList<>();
    private static AtomicInteger nextId = new AtomicInteger(1);


    @Override
    public WeightSensor createSensor(WeightSensor w_sensor) {
        if (w_sensor == null){
            logger.error("Weight Sensor should never be null");
            return null;
        }
        logger.info("Creating weight sensor {}", w_sensor);
        w_sensor.setSensor_ID(nextId.getAndIncrement());
        weightSensors.add(w_sensor);
        return w_sensor;
    }

    @Override
    public List<WeightSensor> readWeightSensor() {
        logger.info("Reading weightSensors from the database...");
        return weightSensors;
    }

    @Override
    public WeightSensor getWeightSensorById(int sensor_ID) {
        return weightSensors.stream()
                .filter(weightSensor -> weightSensor.getSensor_ID() == sensor_ID)
                .findFirst()
                .orElse(null);
    }

    @Override
    public WeightSensor updateSensor(WeightSensor existingWSensor) {
        logger.info("Weight sensor updated: {}", existingWSensor);
        int index = -1;
/*        for (WeightSensor weightSensor : weightSensors) {
            if (weightSensor.getSensor_ID() == existingWSensor.getSensor_ID()) {
                index = 1;
                break;
            }
        }*/
        for (int i = 0; i < weightSensors.size(); i++) {
            WeightSensor weightSensor = weightSensors.get(i);
            if (weightSensor.getSensor_ID() == existingWSensor.getSensor_ID()) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            weightSensors.set(index, existingWSensor);
            return existingWSensor;
        } else {
            return null;
        }
    }
}
