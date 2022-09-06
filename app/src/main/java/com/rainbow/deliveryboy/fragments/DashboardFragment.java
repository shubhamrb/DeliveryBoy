package com.rainbow.deliveryboy.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rainbow.deliveryboy.R;
import com.rainbow.deliveryboy.activity.HomeActivity;
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

    @BindView(R.id.wallet_amount)
    AppCompatTextView wallet_amount;

    @BindView(R.id.btn_request)
    AppCompatTextView btn_request;

    @BindView(R.id.btn_complete)
    CardView btn_complete;
    @BindView(R.id.btn_pending)
    CardView btn_pending;
    @BindView(R.id.btn_rejected)
    CardView btn_rejected;
    @BindView(R.id.btn_total)
    CardView btn_total;

    private DashboardData data;

    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout pullToRefresh;

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
        pullToRefresh.setOnRefreshListener(() -> {
            getDashboardData();
            pullToRefresh.setRefreshing(false);
        });
        getDashboardData();

        btn_complete.setOnClickListener(view -> {
            onBoxClick(8);
        });
        btn_pending.setOnClickListener(view -> {
            onBoxClick(1);
        });
        btn_rejected.setOnClickListener(view -> {
            onBoxClick(10);
        });
        btn_total.setOnClickListener(view -> {
            onBoxClick(0);
        });

    }

    private void onBoxClick(int status) {
        sharedPreferences.edit().putInt(Constants.TAB, 1).apply();
        switchTab(status);
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

    @OnClick({R.id.btn_request})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_request:
                if (Double.parseDouble(data.getWallet_amount()) > 0)
                    presenter.requestAmountSubmit(strToken, data.getWallet_amount(), data.getStoreId());
                break;
        }
    }

    @Override
    public void setDashboardData(DashboardData dashboardData) {
        try {
            data = dashboardData;
            text_completed.setText("Completed\n" + dashboardData.getCompleteOrder());
            text_pending.setText("Pending\n" + dashboardData.getPaddingOrder());
            text_rejected.setText("Rejected\n" + dashboardData.getCancelOrder());
            text_total.setText("Total\n" + dashboardData.getTotalOrder());
            wallet_amount.setText("₹" + dashboardData.getWallet_amount());

            switchTab(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void switchTab(int status) {
        try {
            int TAB = sharedPreferences.getInt(Constants.TAB, 0);
            if (TAB == 1) {
                Activity activity = getActivity();
                HomeActivity homeActivity = (HomeActivity) activity;
                homeActivity.switchTabs(status);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
