package be.kdg.programming3.pillgate.repo.dashboardRepo;

import be.kdg.programming3.pillgate.domain.user.Dashboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Profile("postgres")
@Primary
public class PGDashboardRepository implements DashboardRepository {

    private final Logger logger = LoggerFactory.getLogger(PGDashboardRepository.class);

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert dashboardInserter;

    @Autowired
    public PGDashboardRepository(JdbcTemplate jdbcTemplate) {
        logger.info("Setting up the dashboard repository...");
        this.jdbcTemplate = jdbcTemplate;
        this.dashboardInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Dashboard")
                .usingGeneratedKeyColumns("dashboard_id");
    }

    private static final RowMapper<Dashboard> DASHBOARD_ROW_MAPPER = (rs, rowNum) -> new Dashboard(
            rs.getInt("dashboard_id"),
            rs.getInt("nrPillTaken"),
            rs.getInt("duration"),
            rs.getInt("customer_id")
    );

    @Override
    public Dashboard createDashboard(Dashboard dashboard) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("nrPillTaken", dashboard.getNrPillTaken());
        parameters.put("duration", dashboard.getDuration());
        parameters.put("customer_id", dashboard.getCustomer().getCustomer_id());

        Number newId = dashboardInserter.executeAndReturnKey(parameters);
        dashboard.setDashboard_id(newId.intValue());

        logger.info("Creating a dashboard {}", dashboard);
        return dashboard;
    }

    @Override
    public List<Dashboard> findAllDashboards() {
        logger.info("Finding dashboards from the database...");
        return jdbcTemplate.query("SELECT * FROM Dashboard", DASHBOARD_ROW_MAPPER);
    }

    @Override
    public Dashboard findDashboardByID(int dashboard_ID) {
        logger.info("Finding dashboard by id {}", dashboard_ID);
        return jdbcTemplate.queryForObject("SELECT * FROM Dashboard WHERE dashboard_id = ?",
                new Object[]{dashboard_ID}, DASHBOARD_ROW_MAPPER);
    }

    @Override
    public Dashboard updateDashboard(Dashboard dashboard) {
        logger.info("Dashboard is updated {}", dashboard);
        jdbcTemplate.update("UPDATE Dashboard SET nrPillTaken = ?, duration = ?, customer_id = ? WHERE dashboard_id = ?",
                dashboard.getNrPillTaken(), dashboard.getDuration(), dashboard.getCustomer().getCustomer_id(), dashboard.getDashboard_id());
        return dashboard;
    }

    @Override
    public Dashboard deleteDashboard(int dashboard_ID) {
        Dashboard deletedDashboard = findDashboardByID(dashboard_ID);

        jdbcTemplate.update("DELETE FROM Dashboard WHERE dashboard_id = ?", dashboard_ID);
        logger.info("Dashboard with id {} is deleted", dashboard_ID);

        return deletedDashboard;
    }
}
