package com.rainbow.deliveryboy.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rainbow.deliveryboy.R;
import com.rainbow.deliveryboy.adapter.OrderItemListAdapter;
import com.rainbow.deliveryboy.base.BaseFragment;
import com.rainbow.deliveryboy.model.getOrders.OrderItem;
import com.rainbow.deliveryboy.model.getOrders.OrdersData;
import com.rainbow.deliveryboy.presenter.OrderDetailPresenter;
import com.rainbow.deliveryboy.utils.Constants;
import com.rainbow.deliveryboy.views.OrderDetailView;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetailFragment extends BaseFragment<OrderDetailPresenter, OrderDetailView> implements OrderDetailView {

    @BindView(R.id.recyclerViewItem)
    RecyclerView recyclerViewItem;

    @BindView(R.id.tv_order)
    AppCompatTextView tv_order;
    @BindView(R.id.tv_amount)
    AppCompatTextView tv_amount;
    @BindView(R.id.tv_title)
    AppCompatTextView tv_title;
    @BindView(R.id.tv_address)
    AppCompatTextView tv_address;
    @BindView(R.id.tv_date)
    AppCompatTextView tv_date;
    @BindView(R.id.tv_payment)
    AppCompatTextView tv_payment;
    @BindView(R.id.navigate)
    FloatingActionButton navigate;

    private SharedPreferences sharedPreferences;
    private String strToken = "";
    private OrdersData ordersData;
    private OrderItemListAdapter orderItemListAdapter;

    @Override
    protected int createLayout() {
        return R.layout.fragment_order_detail;
    }

    @Override
    protected void setPresenter() {
        presenter = new OrderDetailPresenter();
    }

    @Override
    protected OrderDetailView createView() {
        return this;
    }


    @Override
    protected void bindData() {
        sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        strToken = sharedPreferences.getString(Constants.TOKEN, "");

        Bundle bundle = getArguments();
        if (bundle != null) {
            ordersData = (OrdersData) bundle.getSerializable("ordersData");
            if (ordersData != null) {

                tv_order.setText("Order #" + ordersData.getId());
                tv_amount.setText("₹" + ordersData.getFinal_price());
                tv_address.setText(ordersData.getAddress().getAddress_1());
                tv_date.setText(ordersData.getOrder_date().split("T")[0]);
                tv_title.setText(ordersData.getAddress().getName());
                if (ordersData.getPayment_channel().equalsIgnoreCase("Offline")) {
                    tv_payment.setText("Payment - COD");
                } else {
                    tv_payment.setText("Payment - Online");
                }
                loadOrderDetail();

                navigate.setOnClickListener(view -> {
                    String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%s,%s (%s)", ordersData.getAddress().getLatitude(), ordersData.getAddress().getLongitude(), ordersData.getAddress().getAddress_1());
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    startActivity(intent);
                });
            }
        }
    }

    private void loadOrderDetail() {
        presenter.getOrderDetail(strToken, String.valueOf(ordersData.getId()));
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
    public void setOrdersData(List<OrderItem> list) {

        recyclerViewItem.setLayoutManager(new LinearLayoutManager(getContext()));
        orderItemListAdapter = new OrderItemListAdapter(getContext(), list);
        recyclerViewItem.setAdapter(orderItemListAdapter);
    }
}
