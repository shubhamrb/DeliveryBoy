package com.rainbow.deliveryboy.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.rainbow.deliveryboy.R;
import com.rainbow.deliveryboy.base.BaseFragment;
import com.rainbow.deliveryboy.model.dashboard.DashboardData;
import com.rainbow.deliveryboy.presenter.DashboardPresenter;
import com.rainbow.deliveryboy.utils.Constants;
import com.rainbow.deliveryboy.views.DashboardView;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends BaseFragment<DashboardPresenter, DashboardView> implements DashboardView {

    private SharedPreferences sharedPreferences;
    private String strToken = "";
    @BindView(R.id.text_completed)
    AppCompatTextView text_completed;

    @BindView(R.id.text_pending)
    AppCompatTextView text_pending;

    @BindView(R.id.text_rejected)
    AppCompatTextView text_rejected;

    @BindView(R.id.text_total)
    AppCompatTextView text_total;

    @Override
    protected int createLayout() {
        return R.layout.fragment_dashboard;
    }

    @Override
    protected void setPresenter() {
        presenter = new DashboardPresenter();
    }

    @Override
    protected DashboardView createView() {
        return this;
    }


    @Override
    protected void bindData() {
        sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        strToken = sharedPreferences.getString(Constants.TOKEN, "");

        getDashboardData();
    }

    private void getDashboardData() {
        presenter.getDashboardData(strToken);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick({})
    public void onViewClicked(View view) {
        switch (view.getId()) {
        }
    }

    @Override
    public void setDashboardData(DashboardData dashboardData) {
        try {
            text_completed.setText("Completed\n" + dashboardData.getCompleteOrder());
            text_pending.setText("Pending\n" + dashboardData.getPaddingOrder());
            text_rejected.setText("Rejected\n" + dashboardData.getRejectOrder());
            text_total.setText("Total\n" + dashboardData.getTotalOrder());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
