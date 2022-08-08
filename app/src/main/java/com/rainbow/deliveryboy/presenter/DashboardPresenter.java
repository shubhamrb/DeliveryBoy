package com.rainbow.deliveryboy.presenter;

import com.google.gson.JsonObject;
import com.rainbow.deliveryboy.api.ApiService;
import com.rainbow.deliveryboy.api.RetroClient;
import com.rainbow.deliveryboy.base.BasePresenter;
import com.rainbow.deliveryboy.model.dashboard.DashboardData;
import com.rainbow.deliveryboy.views.DashboardView;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardPresenter extends BasePresenter<DashboardView> {


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    public void getDashboardData(String token) {
        view.showLoader();
        ApiService api = RetroClient.getApiService();
        Call<DashboardData> call = api.getDashboardData("deliveryboy/dashboarddata", token);
        call.enqueue(new Callback<DashboardData>() {
            @Override
            public void onResponse(Call<DashboardData> call, Response<DashboardData> response) {
                view.hideLoader();
                if (response.body() != null) {
                    if (response.code() == 200) {
                        view.setDashboardData(response.body());
                    } else {
                        view.showMessage("Something went wrong.");
                    }
                }
            }

            @Override
            public void onFailure(Call<DashboardData> call, Throwable throwable) {
                view.hideLoader();
                view.showMessage(throwable.getMessage());
            }
        });
    }

    public void requestAmountSubmit(String token, String amount, int store_id) {
        view.showLoader();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("store_id", store_id);
            jsonObject.put("message", "Amount submitted.");
            jsonObject.put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiService api = RetroClient.getApiService();
        Call<JsonObject> call = api.requestAmountSubmit("deliveryboy/submitcollectamount", token, jsonObject.toString());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                view.hideLoader();
                if (response.body() != null) {
                    if (response.code() == 200) {
                        view.showMessage(response.body().get("message").getAsString());
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

}
