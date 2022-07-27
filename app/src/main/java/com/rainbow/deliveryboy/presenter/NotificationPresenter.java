package com.rainbow.deliveryboy.presenter;

import com.rainbow.deliveryboy.api.ApiService;
import com.rainbow.deliveryboy.api.RetroClient;
import com.rainbow.deliveryboy.base.BasePresenter;
import com.rainbow.deliveryboy.model.getNotification.ResponseNotification;
import com.rainbow.deliveryboy.utils.Constants;
import com.rainbow.deliveryboy.views.NotificationView;

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

    public void getAllNotification(String token) {
        view.showLoader();
        ApiService api = RetroClient.getApiService();
        Call<ResponseNotification> call = api.getNotificationList(Constants.API_KEY, Constants.DEVICE_TYPE, token);
        call.enqueue(new Callback<ResponseNotification>() {
            @Override
            public void onResponse(Call<ResponseNotification> call, Response<ResponseNotification> response) {
                view.hideLoader();
                if (response.body() != null) {
                    if (response.code() == 200) {
                        if (response.body().getData() != null) {
                            view.setNotificationData(response.body().getData());
                        } else {
                            view.showMessage(response.body().getMessage());
                        }

                    } else {
                        view.showMessage(response.body().getMessage());
                    }
                }
            }


            @Override
            public void onFailure(Call<ResponseNotification> call, Throwable throwable) {
                view.hideLoader();
                view.showMessage(throwable.getMessage());
            }
        });
    }

}
