package com.rainbow.deliveryboy.navigator;

import com.rainbow.deliveryboy.base.BaseActivity;

public interface AuthNavigator {

    void openLoginFragment(BaseActivity.PerformFragment performFragment, String referral_id);

    void openOTPFragment(BaseActivity.PerformFragment performFragment, String referral_id);

}
