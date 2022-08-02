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

    @SerializedName("rejectOrder")
    @Expose
    private int rejectOrder;

    @SerializedName("totalOrder")
    @Expose
    private int totalOrder;

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

    public int getRejectOrder() {
        return rejectOrder;
    }

    public void setRejectOrder(int rejectOrder) {
        this.rejectOrder = rejectOrder;
    }

    public int getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(int totalOrder) {
        this.totalOrder = totalOrder;
    }
}
