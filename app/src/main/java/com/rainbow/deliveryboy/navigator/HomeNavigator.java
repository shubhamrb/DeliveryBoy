package com.rainbow.deliveryboy.navigator;


import com.rainbow.deliveryboy.base.BaseActivity;
import com.rainbow.deliveryboy.model.getOrders.OrdersData;

public interface HomeNavigator {
    void openHomeFragment(BaseActivity.PerformFragment performFragment);

    void openNotificationFragment(BaseActivity.PerformFragment performFragment);

    void openOrderDetailFragment(OrdersData order_id, BaseActivity.PerformFragment performFragment);

}
