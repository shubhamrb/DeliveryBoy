package com.rainbow.deliveryboy.views;


import com.google.gson.JsonObject;
import com.rainbow.deliveryboy.base.RootView;
import com.rainbow.deliveryboy.model.getOrders.OrdersData;

import java.util.List;

public interface OrdersView extends RootView {
    void setOrdersData(List<OrdersData> data);

    void statusUpdated(JsonObject jsonObject);
}
