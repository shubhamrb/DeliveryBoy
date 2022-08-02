package com.rainbow.deliveryboy.views;


import com.rainbow.deliveryboy.base.RootView;
import com.rainbow.deliveryboy.model.dashboard.DashboardData;

public interface DashboardView extends RootView {
    void setDashboardData(DashboardData dashboardData);
}
