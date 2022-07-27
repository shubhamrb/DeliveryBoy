package com.rainbow.deliveryboy.presenter;

import com.rainbow.deliveryboy.base.BaseActivity;
import com.rainbow.deliveryboy.base.BasePresenter;
import com.rainbow.deliveryboy.views.LoginView;

public class LoginPresenter extends BasePresenter<LoginView> {


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    public void openOTP(String referral_id) {
        navigator.openOTPFragment(BaseActivity.PerformFragment.REPLACE, referral_id);
    }

}
