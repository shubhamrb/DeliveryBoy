package com.rainbow.deliveryboy.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.rainbow.deliveryboy.R;
import com.rainbow.deliveryboy.adapter.OrderListAdapter;
import com.rainbow.deliveryboy.base.BaseFragment;
import com.rainbow.deliveryboy.model.getOrders.OrdersData;
import com.rainbow.deliveryboy.presenter.OrdersPresenter;
import com.rainbow.deliveryboy.utils.Constants;
import com.rainbow.deliveryboy.utils.LoadMore;
import com.rainbow.deliveryboy.views.OrdersView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends BaseFragment<OrdersPresenter, OrdersView> implements OrdersView, OrderListAdapter.onClickListener {

    @BindView(R.id.recyclerViewOrder)
    RecyclerView recyclerViewOrder;
    private SharedPreferences sharedPreferences;

    private final int PAGE_LIMIT = 10;
    private int CURRENT_PAGE = 0;
    private LoadMore mLoadMore;
    private boolean isLoadMore = false;
    private OrderListAdapter orderListAdapter;
    private List<OrdersData> orderList;
    private String strToken = "";

    @Override
    protected int createLayout() {
        return R.layout.fragment_orders;
    }

    @Override
    protected void setPresenter() {
        presenter = new OrdersPresenter();
    }

    @Override
    protected OrdersView createView() {
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
        orderListAdapter = new OrderListAdapter(getContext(), orderList, this);
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

    @OnClick({})
    public void onViewClicked(View view) {
        switch (view.getId()) {
        }
    }

    @Override
    public void setOrdersData(List<OrdersData> list) {
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

    @Override
    public void statusUpdated(JsonObject jsonObject) {
        showMessage(jsonObject.get("message").getAsString());
        loadOrders();
    }

    @Override
    public void onClickButton(int order_id, int status) {
        presenter.updateStatus(strToken, order_id, status);
    }

    @Override
    public void onClickButton(OrdersData ordersData) {
        presenter.openOrderDetail(ordersData);
    }


}
