package com.rainbow.deliveryboy.api;


import com.google.gson.JsonObject;
import com.rainbow.deliveryboy.model.dashboard.DashboardData;
import com.rainbow.deliveryboy.model.getNotification.ResponseNotification;
import com.rainbow.deliveryboy.model.getOrders.OrderItem;
import com.rainbow.deliveryboy.model.getOrders.OrdersData;
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
    @POST("deliveryboy/sendotp")
    Call<SendOtpResponse> sendOTP(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("deliveryboy/login")
    Call<VerifyOtpResponse> verifyOTP(@Body String body);

    @Headers("Content-Type: application/json")
    @GET
    Call<List<OrdersData>> getOrdersList(@Url String fullUrl, @Header("Authorization") String accessToken);

    @Headers("Content-Type: application/json")
    @GET
    Call<List<OrderItem>> getOrderDetail(@Url String fullUrl, @Header("Authorization") String accessToken);

    @Headers("Content-Type: application/json")
    @GET
    Call<DashboardData> getDashboardData(@Url String fullUrl, @Header("Authorization") String accessToken);

    @Headers("Content-Type: application/json")
    @POST
    Call<JsonObject> requestAmountSubmit(@Url String fullUrl, @Header("Authorization") String accessToken, @Body String body);

    @Headers("Content-Type: application/json")
    @POST("deliveryboy/updatestatus")
    Call<JsonObject> updateStatus(@Header("Authorization") String accessToken, @Body String body);

    @FormUrlEncoded
    @POST("deliveryboy/user-notification-list")
    Call<ResponseNotification> getNotificationList(@Field("api_key") String api_key,
                                                   @Field("client") String client,
                                                   @Field("token") String token);

//    api_key:070b92d28adc166b3a6c63c2d44535d2f62a3e24
//eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImFkbWluOUBnbWFpbC5jb20iLCJpZCI6MTU4LCJpYXQiOjE2NTk2MzQzNDR9.Y_85xLz605eLmP0rUqwf0jGV50jhe-V2a3-Odrl_Gmc
}
