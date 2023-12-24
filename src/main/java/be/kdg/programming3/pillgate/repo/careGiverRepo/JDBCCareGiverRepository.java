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

/**
 * The {@code JDBCCareGiverRepository} class is an implementation of the {@link CareGiverRepository} interface
 * that interacts with a PostgreSQL database. It provides methods to perform CRUD operations on the CareGiver entities.
 *
 * @author Team PillGate
 * @see CareGiverRepository
 * @see CareGiver
 */
@Repository
@Profile("postgres")
@Primary
public class JDBCCareGiverRepository implements CareGiverRepository {

    private final Logger logger = LoggerFactory.getLogger(JDBCCareGiverRepository.class);

    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructs a new {@code JDBCCareGiverRepository} with the specified {@link JdbcTemplate}.
     * @param jdbcTemplate The {@link JdbcTemplate} used to interact with the PostgreSQL database.
     */
    @Autowired
    public JDBCCareGiverRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Maps a row of the result set to a CareGiver object.
     *
     * @param rs     The ResultSet to map to a CareGiver object.
     * @param rowNum The current row number.
     * @return A CareGiver object mapped from the current row of the ResultSet.
     * @throws java.sql.SQLException If a SQL exception occurs while processing the result set.
     */
    private static final RowMapper<CareGiver> CAREGIVER_ROW_MAPPER = (rs, rowNum) -> {
        CareGiver careGiver = new CareGiver();
        careGiver.setCaregiver_id(rs.getInt("caregiver_id"));
        careGiver.setCaregiver_name(rs.getString("caregiver_name"));
        careGiver.setEmail(rs.getString("email"));
        return careGiver;
    };

    /**
     * Creates a new CareGiver in the PostgreSQL database.
     * @param careGiver The CareGiver entity to be created.
     * @return The created CareGiver entity.
     */
    @Override
    public CareGiver createCareGiver(CareGiver careGiver) {
        logger.info("Creating caregiver {}", careGiver);
        jdbcTemplate.update("INSERT INTO CareGiver (caregiver_name, email) VALUES (?, ?)",
                careGiver.getCaregiver_name(),
                careGiver.getEmail());
        return careGiver;
    }

    /**
     * Retrieves all CareGivers from the PostgreSQL database.
     * @return A list of CareGiver entities.
     */
    @Override
    public List<CareGiver> findAllCareGivers() {
        logger.info("Finding caregivers...");
        return jdbcTemplate.query("SELECT * FROM CareGiver", CAREGIVER_ROW_MAPPER);
    }

    /**
     * Retrieves a CareGiver by its ID from the PostgreSQL database.
     * @param caregiver_id The ID of the CareGiver to be retrieved.
     * @return The CareGiver entity with the specified ID.
     */
    @Override
    public CareGiver findCaregiverByID(int caregiver_id) {
        logger.info("Finding caregiver by id: {}", caregiver_id);
        return jdbcTemplate.queryForObject("SELECT * FROM CareGiver WHERE caregiver_id = ?",
                new Object[]{caregiver_id}, CAREGIVER_ROW_MAPPER);
    }

    /**
     * Updates an existing CareGiver in the PostgreSQL database.
     * @param existingCareGiver The CareGiver entity to be updated.
     * @return The updated CareGiver entity.
     */
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

    /**
     * Deletes a CareGiver by its ID from the PostgreSQL database.
     * @param caregiverId The ID of the CareGiver to be deleted.
     * @return The deleted CareGiver entity.
     */
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
