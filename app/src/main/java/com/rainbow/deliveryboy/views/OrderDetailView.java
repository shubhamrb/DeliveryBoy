package com.rainbow.deliveryboy.views;


import com.google.gson.JsonObject;
import com.rainbow.deliveryboy.base.RootView;
import com.rainbow.deliveryboy.model.getOrders.OrderItem;

import java.util.List;

public interface OrderDetailView extends RootView {
    void setOrdersData(List<OrderItem> data);

    void statusUpdated(JsonObject jsonObject, String event);
}
