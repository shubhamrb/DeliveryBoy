package com.rainbow.deliveryboy.presenter;

import android.content.Context;

import com.rainbow.deliveryboy.api.ApiService;
import com.rainbow.deliveryboy.api.RetroClient;
import com.rainbow.deliveryboy.base.BaseActivity;
import com.rainbow.deliveryboy.base.BasePresenter;
import com.rainbow.deliveryboy.model.getNotification.NotificationData;
import com.rainbow.deliveryboy.model.getOrders.OrdersData;
import com.rainbow.deliveryboy.views.NotificationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationPresenter extends BasePresenter<NotificationView> {


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
    }

    public void getAllNotification(Context context,String token, int current_page, int pagelimit) {
        view.showLoader();
        ApiService api = RetroClient.getApiService(context);
        Call<List<NotificationData>> call = api.getNotificationList("deliveryboy/notificationlist/" + current_page + "/" + pagelimit, token);
        call.enqueue(new Callback<List<NotificationData>>() {
            @Override
            public void onResponse(Call<List<NotificationData>> call, Response<List<NotificationData>> response) {
                view.hideLoader();
                if (response.body() != null) {
                    if (response.code() == 200) {
                        view.setNotificationData(response.body());
                    } else {
                        view.showMessage("Something went wrong.");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<NotificationData>> call, Throwable throwable) {
                view.hideLoader();
                view.showMessage(throwable.getMessage());
            }
        });
    }
}
