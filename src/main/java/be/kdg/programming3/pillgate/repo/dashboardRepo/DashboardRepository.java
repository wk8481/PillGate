package be.kdg.programming3.pillgate.repo.dashboardRepo;

import be.kdg.programming3.pillgate.domain.user.Dashboard;

import java.util.List;

public interface DashboardRepository {

    List<Dashboard> findAllDashboards();
    Dashboard findDashboardByID(int dashboard_ID);
    Dashboard createDashboard(Dashboard dashboard);
    Dashboard updateDashboard(Dashboard dashboard);
    Dashboard deleteDashboard(int dashboard_ID);

}
