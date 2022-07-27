package com.rainbow.deliveryboy.views;


import com.rainbow.deliveryboy.base.RootView;
import com.rainbow.deliveryboy.model.getNotification.NotificationData;

import java.util.List;

public interface HomeView extends RootView {
    void setOrdersData(List<NotificationData> data);
}
