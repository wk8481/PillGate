package be.kdg.programming3.pillgate.repo.dashboardRepo;


import be.kdg.programming3.pillgate.domain.user.Dashboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Profile("jdbctemplate")
@Primary

public class JDBCDashboardRepository implements DashboardRepository{

    private static List<Dashboard> dashboard = new ArrayList<>();
    private final Logger logger = LoggerFactory.getLogger(JDBCDashboardRepository.class);

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert dashboardInserter;


    public JDBCDashboardRepository(JdbcTemplate jdbcTemplate) {
        logger.info("Setting up the dashboard repository...");
        this.jdbcTemplate = jdbcTemplate;
        this.dashboardInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Dashboard")
                .usingGeneratedKeyColumns("dashboard_id");

    }


    private RowMapper<Dashboard> dashboardRowMapper() {
        logger.info("Setting up the dashboard row mapper...");
        return (rs, rowNum) -> new Dashboard (
                rs.getInt("dashboard_id"),
                rs.getInt("nrPillTaken"),
                rs.getInt("duration"),
                rs.getInt("customer_id")

        );
    }


    @Override
    public Dashboard createDashboard(Dashboard dashboard){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("dashboard_id", dashboard.getDashboard_id());
        parameters.put("nrPillTaken", dashboard.getNrPillTaken());
        parameters.put("duration", dashboard.getDuration());
        parameters.put("customer_id", dashboard.getCustomer().getCustomer_id());
        dashboard.setDashboard_id(dashboardInserter.executeAndReturnKey(parameters).intValue());
        logger.info("Creating a dashboard {}", dashboard);
        return dashboard;

    }

    @Override
    public List<Dashboard> findAllDashboards() {
        logger.info("Finding dashboards from the database...");
        List<Dashboard> dashboards = jdbcTemplate.query(
                "SELECT * FROM Dashboard", dashboardRowMapper());
        return dashboards;
    }

    @Override
    public Dashboard findDashboardByID(int dashboard_ID) {
        logger.info("Finding dashboard by id {}", dashboard_ID);
        Dashboard dashboard = jdbcTemplate.queryForObject("SELECT * FROM Dashboard WHERE dashboard_id = ?",
                dashboardRowMapper(), dashboard_ID);
        return dashboard;
    }

    @Override
    public Dashboard updateDashboard(Dashboard dashboard) {
        logger.info("Dashboard is updated {}", dashboard);
        jdbcTemplate.update("UPDATE Dashboard SET dashboard_ID = ? WHERE CUSTOMER_ID= ?",
                dashboard.getDashboard_id(), dashboard.getNrPillTaken(), dashboard.getCustomer().getCustomer_id());
        return dashboard;
    }

    @Override
    public Dashboard deleteDashboard(int dashboard_ID) {
        Dashboard deletedDashboard = findDashboardByID(dashboard_ID);

        jdbcTemplate.update("DELETE FROM DASHBOARD WHERE dashboard_ID = ?", dashboard_ID);
        logger.info("Dashboard with id {} is deleted ", dashboard_ID);

        return deletedDashboard;
    }
}
