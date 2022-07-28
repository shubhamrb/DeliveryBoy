package com.rainbow.deliveryboy.presenter;

import com.rainbow.deliveryboy.base.BaseActivity;
import com.rainbow.deliveryboy.base.BasePresenter;
import com.rainbow.deliveryboy.views.HomeView;

public class HomePresenter extends BasePresenter<HomeView> {


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    public void openNotification() {
        navigator.openNotificationFragment(BaseActivity.PerformFragment.REPLACE);
    }
}
