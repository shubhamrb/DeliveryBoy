package com.rainbow.deliveryboy.views;

public interface OtpReceivedInterface {
  void onOtpReceived(String otp);
  void onOtpTimeout();
}