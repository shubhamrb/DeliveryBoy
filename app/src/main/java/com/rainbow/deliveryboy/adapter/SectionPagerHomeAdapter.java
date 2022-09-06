package com.rainbow.deliveryboy.adapter;

import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.rainbow.deliveryboy.fragments.DashboardFragment;
import com.rainbow.deliveryboy.fragments.OrdersFragment;


public class SectionPagerHomeAdapter extends FragmentStatePagerAdapter {
    int tabCount;
    private DashboardFragment dashboardFragment;
    private OrdersFragment ordersFragment;
    private SparseArray<Fragment> registeredFragments = new SparseArray<>();

    public SectionPagerHomeAdapter(FragmentManager fm, int tabCount) {
        super(fm,tabCount);
        this.tabCount = tabCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                dashboardFragment = new DashboardFragment();
                return dashboardFragment;
            case 1:
                ordersFragment = new OrdersFragment();
                return ordersFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}
