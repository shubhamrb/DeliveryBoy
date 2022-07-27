package com.rainbow.deliveryboy.views;

import com.rainbow.deliveryboy.base.RootView;
import com.rainbow.deliveryboy.model.sendotp.SendOtpResponse;
import com.rainbow.deliveryboy.model.verifyOtp.VerifyOtpResponse;

public interface OtpView extends RootView {

    void setLoginData(VerifyOtpResponse response);

    void setOtpSent(SendOtpResponse response);
}
