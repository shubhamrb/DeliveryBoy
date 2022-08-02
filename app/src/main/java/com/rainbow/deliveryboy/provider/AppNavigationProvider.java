package com.rainbow.deliveryboy.provider;

import android.os.Bundle;

import com.rainbow.deliveryboy.base.BaseActivity;
import com.rainbow.deliveryboy.fragments.HomeFragment;
import com.rainbow.deliveryboy.fragments.LoginFragment;
import com.rainbow.deliveryboy.fragments.NotificationFragment;
import com.rainbow.deliveryboy.fragments.OTpFragment;
import com.rainbow.deliveryboy.fragments.OrderDetailFragment;
import com.rainbow.deliveryboy.model.getOrders.OrdersData;
import com.rainbow.deliveryboy.navigator.AppNavigator;

public abstract class AppNavigationProvider extends BaseActivity implements AppNavigator {


    @Override
    public void openLoginFragment(PerformFragment performFragment, String referral_id) {
        Bundle bundle = new Bundle();
        bundle.putString("referral_id", referral_id);
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setArguments(bundle);
        openFragment(loginFragment, LoginFragment.class.getName(), performFragment, false);
    }

    @Override
    public void openOTPFragment(PerformFragment performFragment, String referral_id) {
        Bundle bundle = new Bundle();
        bundle.putString("referral_id", referral_id);
        OTpFragment oTpFragment = new OTpFragment();
        oTpFragment.setArguments(bundle);
        openFragment(oTpFragment, LoginFragment.class.getName(), performFragment, false);
    }

    @Override
    public void openHomeFragment(PerformFragment performFragment) {
        HomeFragment homeFragment = new HomeFragment();
        openFragment(homeFragment, HomeFragment.class.getName(), performFragment, false);
    }

    @Override
    public void openNotificationFragment(PerformFragment performFragment) {
        NotificationFragment notificationFragment = new NotificationFragment();
        openFragment(notificationFragment, NotificationFragment.class.getName(), performFragment, true);
    }

    @Override
    public void openOrderDetailFragment(OrdersData ordersData, PerformFragment performFragment) {
        OrderDetailFragment orderDetailFragment = new OrderDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ordersData", ordersData);
        orderDetailFragment.setArguments(bundle);
        openFragment(orderDetailFragment, OrderDetailFragment.class.getName(), performFragment, true);
    }

}
