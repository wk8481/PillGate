

package be.kdg.programming3.pillgate.repo.careGiverRepo;

import be.kdg.programming3.pillgate.domain.user.CareGiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
@Profile("postgres")
@Primary
public class PGCareGiverRepository implements CareGiverRepository {

    private final Logger logger = LoggerFactory.getLogger(PGCareGiverRepository.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PGCareGiverRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<CareGiver> CAREGIVER_ROW_MAPPER = (rs, rowNum) -> {
        CareGiver careGiver = new CareGiver();
        careGiver.setCaregiver_id(rs.getInt("caregiver_id"));
        careGiver.setCaregiver_name(rs.getString("caregiver_name"));
        careGiver.setEmail(rs.getString("email"));
        return careGiver;
    };

    @Override
    public CareGiver createCareGiver(CareGiver careGiver) {
        logger.info("Creating caregiver {}", careGiver);
        jdbcTemplate.update("INSERT INTO CareGiver (caregiver_name, email) VALUES (?, ?)",
                careGiver.getCaregiver_name(),
                careGiver.getEmail());
        return careGiver;
    }

    @Override
    public List<CareGiver> findAllCareGivers() {
        logger.info("Finding caregivers...");
        return jdbcTemplate.query("SELECT * FROM CareGiver", CAREGIVER_ROW_MAPPER);
    }

    @Override
    public CareGiver findCaregiverByID(int caregiver_id) {
        logger.info("Finding caregiver by id: {}", caregiver_id);
        return jdbcTemplate.queryForObject("SELECT * FROM CareGiver WHERE caregiver_id = ?",
                new Object[]{caregiver_id}, CAREGIVER_ROW_MAPPER);
    }

    @Override
    public CareGiver updateCareGiver(CareGiver existingCareGiver) {
        logger.info("Updating caregiver {}", existingCareGiver);
        int updatedRows = jdbcTemplate.update(
                "UPDATE CareGiver SET caregiver_name = ?, email = ? WHERE caregiver_id = ?",
                existingCareGiver.getCaregiver_name(), existingCareGiver.getEmail(), existingCareGiver.getCaregiver_id()
        );

        if (updatedRows > 0) {
            logger.info("CareGiver with ID {} updated successfully", existingCareGiver.getCaregiver_id());
            return existingCareGiver;
        } else {
            logger.warn("Failed to update CareGiver with ID: {}", existingCareGiver.getCaregiver_id());
            return null;
        }
    }

    @Override
    public CareGiver deleteCaregiver(int caregiverId) {
        String selectQuery = "SELECT * FROM CareGiver WHERE caregiver_id = ?";
        CareGiver deletedCaregiver = jdbcTemplate.queryForObject(
                selectQuery,
                CAREGIVER_ROW_MAPPER,
                caregiverId
        );

        int deletedRows = jdbcTemplate.update(
                "DELETE FROM CareGiver WHERE caregiver_id = ?",
                caregiverId
        );

        if (deletedRows > 0) {
            logger.info("CareGiver with ID {} deleted successfully", caregiverId);
            return deletedCaregiver;
        } else {
            logger.warn("Failed to delete CareGiver with ID: {}", caregiverId);
            return null;
        }
    }
}

