package be.kdg.programming3.repository;

import be.kdg.programming3.domain.sensor.WeightSensor;
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


@Profile("jdbc")
@Repository
public class JDBCSensorRepository implements SensorRepository{
    private Logger logger = LoggerFactory.getLogger(JDBCSensorRepository.class);
    private static List<WeightSensor> weightSensors = new ArrayList<>();
    private static AtomicInteger nextId = new AtomicInteger(1);
    private SensorRepository sensorRepository;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert sensorInserter;

    public JDBCSensorRepository(JdbcTemplate jdbcTemplate, SensorRepository sensorRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.sensorRepository = sensorRepository;
        this.sensorInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("WeightSensor")
                .usingGeneratedKeyColumns("sensor_ID");
    }

    public static WeightSensor mapRow(ResultSet rs, int rowId) throws SQLException {
        return new WeightSensor(rs.getInt("sensor_ID"),
                rs.getDate("calibrationDate").toLocalDate(),
                rs.getDouble("weight"),
                rs.getDouble("calibrationFactor"));
    }

    @Override
    public WeightSensor createSensor(WeightSensor weightSensor) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("sensor_ID", weightSensor.getSensor_ID());
        parameters.put("WEIGHT_CAPACITY_GRAMS", weightSensor.getWEIGHT_CAPACITY_GRAMS());
        parameters.put("calibrationDate", weightSensor.getCalibrationDate());
        parameters.put("weight", weightSensor.getWeight());
        parameters.put("calibrationFactor", weightSensor.getCalibrationFactor());
        weightSensor.setSensor_ID(sensorInserter.executeAndReturnKey(parameters).intValue());
        logger.info("Creating weight sensor {}", weightSensor);
        return weightSensor;
    }

    @Override
    public List<WeightSensor> findAllWSensors() {
        logger.info("Reading weightSensors from the database...");
        List<WeightSensor> weightSensors = jdbcTemplate.query(
                "SELECT * FROM WeightSensor", JDBCSensorRepository::mapRow);
        return weightSensors;
    }

    @Override
    public WeightSensor findSenorByID(int sensor_ID) {
        WeightSensor weightSensor = jdbcTemplate.queryForObject("SELECT * FROM WeightSensor WHERE sensor_ID = ?",
                JDBCSensorRepository::mapRow, sensor_ID);
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
