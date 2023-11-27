package be.kdg.programming3.repository;

import be.kdg.programming3.domain.pillbox.Medicine;
import be.kdg.programming3.repository.MedicineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
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


    public JDBCPillBoxRepository(MedicineRepository medicineRepository, JdbcTemplate jdbcTemplate, SimpleJdbcInsert pillBoxInserter) {
        this.medicineRepository = medicineRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.pillBoxInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Medicine")
                .usingGeneratedKeyColumns("medicine_id");
    }

    private static final RowMapper<Medicine> medicineRowMapper = (rs, rowNum) -> new Medicine(
            rs.getInt("medicine_id"),
            rs.getString("name"),
            rs.getDouble("weight")
    );

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
    public Medicine deleteMedicine(int medicine_id) {
        final String sql = "DELETE FROM Medicine WHERE medicine_id = ?";
        jdbcTemplate.update(sql, medicine_id);
        return deleteMedicine(deleteMedicine(medicine_id).getMedicine_id());
    }
}
