/*
package be.kdg.programming3.repository;

import be.kdg.programming3.domain.pillbox.Medicine;
import be.kdg.programming3.domain.sensor.WeightSensor;
import be.kdg.programming3.repository.MedicineRepository;
import org.hibernate.query.SelectionQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@Profile("jdbc")
public class JDBCPillBoxRepository implements MedicineRepository {

    private static final Logger logger = LoggerFactory.getLogger(JDBCPillBoxRepository.class);
    private static AtomicInteger nextId = new AtomicInteger(1);
    private MedicineRepository medicineRepository;
    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert pillBoxInserter;


    public JDBCPillBoxRepository(MedicineRepository medicineRepository, JdbcTemplate jdbcTemplate) {
        this.medicineRepository = medicineRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.pillBoxInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Medicine")
                .usingGeneratedKeyColumns("medicine_id");
    }

    private static Medicine medicineRowMapper(ResultSet rs, int rowid) throws SQLException {
        String name = rs.getString("name");
        int medicine_id = rs.getInt("medicine_id");
        double weight = rs.getDouble("weight");

        WeightSensor weightSensor = new WeightSensor(weight);
        return new Medicine(name, weightSensor, medicine_id);
    }

    @Override
    public Medicine createMedicine(Medicine medicine) {
        final String sql = "INSERT INTO Medicine (name, weight) VALUES (?, ?)";
        jdbcTemplate.update(sql, medicine.getName(), medicine.getWeight());
        return medicine;
    }

    @Override
    public Medicine findMedicineById(int medicine_id) {
        final String sql = "SELECT * FROM Medicine WHERE medicine_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{medicine_id}, medicineRowMapper);
    }

    @Override
    public List<Medicine> findAllMedicines() {
        final String sql = "SELECT * FROM Medicine";
        return jdbcTemplate.query(sql, medicineRowMapper);
    }

    @Override
    public Medicine updateMedicine(Medicine existingMedicine) {
        final String sql = "UPDATE Medicine SET name = ?, weight = ? WHERE medicine_id = ?";
        int affectedRows = jdbcTemplate.update(sql, existingMedicine.getName(), existingMedicine.getWeight(), existingMedicine.getMedicine_id());
        return affectedRows > 0 ? existingMedicine : null;
    }

    @Override
    public void deleteMedicine(int medicine_id) {
        final String sql = "DELETE FROM Medicine WHERE medicine_id = ?";
        jdbcTemplate.update(sql, medicine_id);
    }
}
*/
