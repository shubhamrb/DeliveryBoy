package com.rainbow.deliveryboy.model.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardData {
    @SerializedName("completeOrder")
    @Expose
    private int completeOrder;

    @SerializedName("paddingOrder")
    @Expose
    private int paddingOrder;

    @SerializedName("cancelOrder")
    @Expose
    private int cancelOrder;

    @SerializedName("totalOrder")
    @Expose
    private int totalOrder;

    @SerializedName("storeId")
    @Expose
    private int storeId;

    @SerializedName("wallet_amount")
    @Expose
    private String wallet_amount;

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getCompleteOrder() {
        return completeOrder;
    }

    public void setCompleteOrder(int completeOrder) {
        this.completeOrder = completeOrder;
    }

    public int getPaddingOrder() {
        return paddingOrder;
    }

    public void setPaddingOrder(int paddingOrder) {
        this.paddingOrder = paddingOrder;
    }

    public int getCancelOrder() {
        return cancelOrder;
    }

    public void setCancelOrder(int cancelOrder) {
        this.cancelOrder = cancelOrder;
    }

    public int getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(int totalOrder) {
        this.totalOrder = totalOrder;
    }

    public String getWallet_amount() {
        return wallet_amount;
    }

    public void setWallet_amount(String wallet_amount) {
        this.wallet_amount = wallet_amount;
    }
}
