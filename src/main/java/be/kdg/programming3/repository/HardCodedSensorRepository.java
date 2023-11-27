package be.kdg.programming3.repository;

import be.kdg.programming3.domain.sensor.WeightSensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@Profile("list")
public class HardCodedSensorRepository implements SensorRepository {
    private Logger logger = LoggerFactory.getLogger(HardCodedSensorRepository.class);
    private static List<WeightSensor> weightSensors = new ArrayList<>();
    private static AtomicInteger nextId = new AtomicInteger(1);


    @Override
    public WeightSensor createSensor(WeightSensor weightSensor) {
        if (weightSensor == null) {
            logger.info("Weight Sensor should never be null");
            return null;
        }
        logger.info("Creating weight sensor {}", weightSensor);
        weightSensor.setSensor_ID(nextId.getAndIncrement());
        weightSensors.add(weightSensor);
        return weightSensor;
    }

    @Override
    public List<WeightSensor> findAllWSensors() {
        logger.info("Reading weightSensors from the database...");
        return weightSensors;
    }

    @Override
    public WeightSensor findSenorByID(int sensor_ID) {
        logger.info("Reading medicines from database via database..");
        return weightSensors.stream()
                .filter(weightSensor -> weightSensor.getSensor_ID() == sensor_ID)
                .findFirst()
                .orElse(null);
    }

    @Override
    public WeightSensor updateSensor(WeightSensor existingWSensor) {
        logger.info("Weight sensor updated: {}", existingWSensor);
        int index = -1;
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

