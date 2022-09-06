package com.rainbow.deliveryboy.presenter;

import com.google.gson.JsonObject;
import com.rainbow.deliveryboy.api.ApiService;
import com.rainbow.deliveryboy.api.RetroClient;
import com.rainbow.deliveryboy.base.BaseActivity;
import com.rainbow.deliveryboy.base.BasePresenter;
import com.rainbow.deliveryboy.model.getOrders.OrdersData;
import com.rainbow.deliveryboy.views.OrdersView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersPresenter extends BasePresenter<OrdersView> {


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    public void getOrders(String token, int current_page, int pagelimit, int status) {
        view.showLoader();
        ApiService api = RetroClient.getApiService();
        Call<List<OrdersData>> call = api.getOrdersList("deliveryboy/orderlist/" + current_page + "/" + pagelimit + "/" + status, token);
        call.enqueue(new Callback<List<OrdersData>>() {
            @Override
            public void onResponse(Call<List<OrdersData>> call, Response<List<OrdersData>> response) {
                view.hideLoader();
                if (response.body() != null) {
                    if (response.code() == 200) {
                        view.setOrdersData(response.body());
                    } else {
                        view.showMessage("Something went wrong.");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<OrdersData>> call, Throwable throwable) {
                view.hideLoader();
                view.showMessage(throwable.getMessage());
            }
        });
    }

    public void updateStatus(String token, int order_id, int status, String reason, String otp, String amount) {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("orderId", order_id);
            jsonObject.put("status", status);
            if (reason != null) {
                jsonObject.put("reject_reason", reason);
            }
            if (otp != null) {
                jsonObject.put("otp", otp);
            }
            if (amount != null) {
                jsonObject.put("amount", Double.parseDouble(amount));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.showLoader();
        ApiService api = RetroClient.getApiService();
        Call<JsonObject> call = api.updateStatus(token, jsonObject.toString());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                view.hideLoader();
                if (response.body() != null) {
                    if (response.code() == 200) {
                        view.statusUpdated(response.body());
                    } else {
                        view.showMessage("Something went wrong.");
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable throwable) {
                view.hideLoader();
                view.showMessage(throwable.getMessage());
            }
        });
    }


    public void openNotification() {
        navigator.openNotificationFragment(BaseActivity.PerformFragment.REPLACE);
    }

    public void openOrderDetail(OrdersData ordersData) {
        navigator.openOrderDetailFragment(ordersData, BaseActivity.PerformFragment.REPLACE);
    }
}
