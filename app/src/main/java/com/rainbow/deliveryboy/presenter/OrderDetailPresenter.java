package com.rainbow.deliveryboy.presenter;

import com.rainbow.deliveryboy.api.ApiService;
import com.rainbow.deliveryboy.api.RetroClient;
import com.rainbow.deliveryboy.base.BasePresenter;
import com.rainbow.deliveryboy.model.getOrders.OrderItem;
import com.rainbow.deliveryboy.views.OrderDetailView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailPresenter extends BasePresenter<OrderDetailView> {


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    public void getOrderDetail(String token, String order_id) {
        view.showLoader();
        ApiService api = RetroClient.getApiService();
        Call<List<OrderItem>> call = api.getOrderDetail("order/orderdetail/" + order_id, token);
        call.enqueue(new Callback<List<OrderItem>>() {
            @Override
            public void onResponse(Call<List<OrderItem>> call, Response<List<OrderItem>> response) {
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
            public void onFailure(Call<List<OrderItem>> call, Throwable throwable) {
                view.hideLoader();
                view.showMessage(throwable.getMessage());
            }
        });
    }
}