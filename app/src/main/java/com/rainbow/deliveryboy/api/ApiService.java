package com.rainbow.deliveryboy.api;


import com.rainbow.deliveryboy.model.getNotification.NotificationData;
import com.rainbow.deliveryboy.model.getNotification.ResponseNotification;
import com.rainbow.deliveryboy.model.resendOtp.ResendOtpResponse;
import com.rainbow.deliveryboy.model.sendotp.SendOtpResponse;
import com.rainbow.deliveryboy.model.verifyOtp.VerifyOtpResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;


public interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("sendotp")
    Call<SendOtpResponse> sendOTP(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("login")
    Call<VerifyOtpResponse> verifyOTP(@Body String body);

    @Headers("Content-Type: application/json")
    @GET
    Call<List<NotificationData>> getOrdersList(@Url String fullUrl, @Header("Authorization") String accessToken);

    @FormUrlEncoded
    @POST("user-notification-list")
    Call<ResponseNotification> getNotificationList(@Field("api_key") String api_key,
                                                   @Field("client") String client,
                                                   @Field("token") String token);

//    api_key:070b92d28adc166b3a6c63c2d44535d2f62a3e24
//    client:android
//    token:NRy4MvEaDj5O04r8S6GGSZAJ7T5tv1QvS969rtgyYe7qdyKv8q6wjWBozH5I
//    request_id:57
//    code:0100
//    lat:91.5656252
//    long:75.0002356

    //X14ybrF0H7tjrwG5w0DMZIs2Two5EiYx73LGmLMF0nt39zLVfEne1NuOhZF9

}
