package com.rainbow.deliveryboy.presenter;

import com.rainbow.deliveryboy.api.ApiService;
import com.rainbow.deliveryboy.api.RetroClient;
import com.rainbow.deliveryboy.base.BaseActivity;
import com.rainbow.deliveryboy.base.BasePresenter;
import com.rainbow.deliveryboy.model.resendOtp.ResendOtpResponse;
import com.rainbow.deliveryboy.model.sendotp.SendOtpResponse;
import com.rainbow.deliveryboy.model.verifyOtp.VerifyOtpResponse;
import com.rainbow.deliveryboy.utils.Constants;
import com.rainbow.deliveryboy.views.OtpView;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpPresenter extends BasePresenter<OtpView> {


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    public void openLogin(String referral_id) {
        navigator.openLoginFragment(BaseActivity.PerformFragment.REPLACE, referral_id);
    }

    public void verifyOtp(String otp, String mobile) {
        view.showLoader();
        ApiService api = RetroClient.getApiService();

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("otp",otp);
            jsonObject.put("mobileNumber",mobile);
            jsonObject.put("type",Constants.TYPE);
            jsonObject.put("device_type",Constants.DEVICE_TYPE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<VerifyOtpResponse> call = api.verifyOTP(jsonObject.toString());
        call.enqueue(new Callback<VerifyOtpResponse>() {
            @Override
            public void onResponse(Call<VerifyOtpResponse> call, Response<VerifyOtpResponse> response) {
                view.hideLoader();

                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        view.setLoginData(response.body());
                    } else {
                        //  view.hideLoader();
                        view.showMessage(response.body().getError());
                    }


                }
            }


            @Override
            public void onFailure(Call<VerifyOtpResponse> call, Throwable throwable) {
                view.hideLoader();
                view.showMessage(throwable.getMessage());
            }
        });
    }

    public void openCreateProfile(String referral_id) {
    }

    public void sendOtp(String mobile) {
        view.showLoader();
        ApiService api = RetroClient.getApiService();

        JSONObject jsonObject =new JSONObject();
        try {
            jsonObject.put("mobileNumber",mobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<SendOtpResponse> call = api.sendOTP(jsonObject.toString());
        call.enqueue(new Callback<SendOtpResponse>() {
            @Override
            public void onResponse(Call<SendOtpResponse> call, Response<SendOtpResponse> response) {
                view.hideLoader();
                if (response.body() != null) {
                    if (response.body().getSend()) {
                        view.setOtpSent(response.body());
                    } else {
                        //  view.hideLoader();
                        view.showMessage("Something went wrong.");
                    }
                }
            }

            @Override
            public void onFailure(Call<SendOtpResponse> call, Throwable throwable) {
                view.hideLoader();
                view.showMessage(throwable.getMessage());
            }
        });
    }
}
