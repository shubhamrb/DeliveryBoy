package com.rainbow.deliveryboy.model.sendotp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendOtpResponse {

    @SerializedName("send")
    @Expose
    private Boolean send;

    @SerializedName("randomOtp")
    @Expose
    private int randomOtp;

    public Boolean getSend() {
        return send;
    }

    public void setSend(Boolean send) {
        this.send = send;
    }

    public int getRandomOtp() {
        return randomOtp;
    }

    public void setRandomOtp(int randomOtp) {
        this.randomOtp = randomOtp;
    }
}
