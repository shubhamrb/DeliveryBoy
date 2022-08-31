package com.rainbow.deliveryboy.model.getOrders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrdersData implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("assign_status")
    @Expose
    private int assign_status;

    @SerializedName("orderId")
    @Expose
    private String orderId;

    @SerializedName("order_date")
    @Expose
    private String order_date;

    @SerializedName("final_price")
    @Expose
    private String final_price;

    @SerializedName("payment_channel")
    @Expose
    private String payment_channel;

    @SerializedName("payment_status")
    @Expose
    private String payment_status;

    @SerializedName("otp")
    @Expose
    private String otp;

    @SerializedName("address")
    @Expose
    private AddressData address;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOtp() {
        return otp;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public int getAssign_status() {
        return assign_status;
    }

    public void setAssign_status(int assign_status) {
        this.assign_status = assign_status;
    }

    public String getPayment_channel() {
        return payment_channel;
    }

    public void setPayment_channel(String payment_channel) {
        this.payment_channel = payment_channel;
    }

    public String getFinal_price() {
        return final_price;
    }

    public void setFinal_price(String final_price) {
        this.final_price = final_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public AddressData getAddress() {
        return address;
    }

    public void setAddress(AddressData address) {
        this.address = address;
    }
}
