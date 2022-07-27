package com.rainbow.deliveryboy.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rainbow.deliveryboy.R;
import com.rainbow.deliveryboy.activity.HomeActivity;
import com.rainbow.deliveryboy.adapter.OrderListAdapter;
import com.rainbow.deliveryboy.base.BaseFragment;
import com.rainbow.deliveryboy.model.getNotification.NotificationData;
import com.rainbow.deliveryboy.presenter.HomePresenter;
import com.rainbow.deliveryboy.utils.Constants;
import com.rainbow.deliveryboy.utils.LoadMore;
import com.rainbow.deliveryboy.views.HomeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment<HomePresenter, HomeView> implements HomeView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imageViewHome)
    ImageView imageViewHome;
    @BindView(R.id.imageViewNotification)
    ImageView imageViewNotification;
    @BindView(R.id.recyclerViewOrder)
    RecyclerView recyclerViewOrder;
    private SharedPreferences sharedPreferences;

    private final int PAGE_LIMIT = 10;
    private int CURRENT_PAGE = 0;
    private LoadMore mLoadMore;
    private boolean isLoadMore = false;
    private OrderListAdapter orderListAdapter;
    private List<NotificationData> orderList;
    private String strToken = "";

    @Override
    protected int createLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void setPresenter() {
        presenter = new HomePresenter();
    }

    @Override
    protected HomeView createView() {
        return this;
    }


    @Override
    protected void bindData() {
        sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        strToken = sharedPreferences.getString(Constants.TOKEN, "");

        orderList = new ArrayList<>();
        mLoadMore = new LoadMore(recyclerViewOrder);
        mLoadMore.setLoadingMore(false);
        recyclerViewOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        orderListAdapter = new OrderListAdapter(getContext(), orderList);
        recyclerViewOrder.setAdapter(orderListAdapter);

        mLoadMore.setOnLoadMoreListener(() -> {
            if (isLoadMore) {
                CURRENT_PAGE++;
                loadOrders();
            }
        });

        loadOrders();
    }

    private void loadOrders() {
        presenter.getOrders(strToken, CURRENT_PAGE, PAGE_LIMIT);
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

    @OnClick({R.id.imageViewHome, R.id.imageViewNotification})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewHome:
                ((HomeActivity) getActivity()).openDrawer();
                break;
            case R.id.imageViewNotification:
                presenter.openNotification();
                break;
        }
    }

    @Override
    public void setOrdersData(List<NotificationData> list) {
        if (CURRENT_PAGE == 0) {
            orderList.clear();
        }
        if (list.size() > 0) {
            orderList.addAll(list);
            mLoadMore.setLoadingMore(true);
            orderListAdapter.setList(list);
        } else {
            mLoadMore.setLoadingMore(false);
        }
    }

}
