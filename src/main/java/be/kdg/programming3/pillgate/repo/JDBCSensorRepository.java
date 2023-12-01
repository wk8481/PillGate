package be.kdg.programming3.pillgate.repo;

import be.kdg.programming3.pillgate.domain.sensor.WeightSensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


@Profile("jdbctemplate")
@Repository
public class JDBCSensorRepository implements SensorRepository{
    private Logger logger = LoggerFactory.getLogger(JDBCSensorRepository.class);
    private static List<WeightSensor> weightSensors = new ArrayList<>();
    private static AtomicInteger nextId = new AtomicInteger(1);



    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert sensorInserter;

    public JDBCSensorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.sensorInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("WeightSensor")
                .usingGeneratedKeyColumns("sensor_ID");
        logger.info("Setting up the sensor repository...");
    }

    public  WeightSensor mapRow(ResultSet rs, int rowId) throws SQLException {
        return new WeightSensor(
                rs.getInt("sensor_ID"),
                rs.getInt("customer_id"),
                rs.getInt("WEIGHT_CAPACITY_GRAMS"),
                rs.getDate("calibrationDate").toLocalDate(),
                rs.getDouble("weight"));

    }


    @Override
    public WeightSensor createSensor(WeightSensor weightSensor) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("sensor_ID", weightSensor.getSensor_ID());
        parameters.put("WEIGHT_CAPACITY_GRAMS", weightSensor.getWEIGHT_CAPACITY_GRAMS());
        parameters.put("calibrationDate", weightSensor.getCalibrationDate());
        parameters.put("weight", weightSensor.getWeight());
//        parameters.put("calibrationFactor", weightSensor.getCalibrationFactor());
        weightSensor.setSensor_ID(sensorInserter.executeAndReturnKey(parameters).intValue());
        logger.info("Creating weight sensor {}", weightSensor);
        return weightSensor;
    }

    @Override
    public List<WeightSensor> findAllWSensors() {
        logger.info("Reading weightSensors from the database...");
//        List<WeightSensor> sensor = jdbcTemplate.query("SELECT * FROM WeightSensor" , mapRow);

        return jdbcTemplate.query("SELECT * FROM WeightSensor",this::mapRow );
    }

    @Override
    public WeightSensor findSenorByID(int sensor_ID) {
        WeightSensor weightSensor = jdbcTemplate.queryForObject("SELECT * FROM WeightSensor WHERE sensor_ID = ?", this::mapRow, sensor_ID);
        logger.info("Finding weight sensor by id {} ", weightSensor);
        return weightSensor;
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
