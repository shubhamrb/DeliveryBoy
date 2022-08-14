package com.rainbow.deliveryboy.model.getNotification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationData {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("is_read")
    @Expose
    private boolean is_read;

    @SerializedName("noti_type")
    @Expose
    private String noti_type;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isIs_read() {
        return is_read;
    }

    public void setIs_read(boolean is_read) {
        this.is_read = is_read;
    }

    public String getNoti_type() {
        return noti_type;
    }

    public void setNoti_type(String noti_type) {
        this.noti_type = noti_type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
