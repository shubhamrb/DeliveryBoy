package com.rainbow.deliveryboy.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
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
    public void onClickButton(OrdersData ordersData) {
        if (ordersData.getStatus() == 7) {
            /*cancel*/
            showCancelReason(ordersData.getId(), ordersData.getStatus());
        } else if (ordersData.getStatus() == 8) {
            /*complete*/
            showCompleteOtp(ordersData.getId(), ordersData.getStatus(), ordersData.getFinal_price());
        } else {
            presenter.updateStatus(strToken, ordersData.getId(), ordersData.getStatus(), null, null, null);
        }
    }


    public void showCancelReason(int order_id, int status) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.order_cancel_reason_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        AppCompatButton buttonSubmit = dialog.findViewById(R.id.buttonSubmit);
        RadioGroup reason_radio = dialog.findViewById(R.id.reason_radio);

        buttonSubmit.setOnClickListener(v -> {
            int selectedId = reason_radio.getCheckedRadioButtonId();

            // find the radiobutton by returned id
            RadioButton radioButton = (RadioButton) dialog.findViewById(selectedId);
            presenter.updateStatus(strToken, order_id, status, radioButton.getText().toString(), null, null);
            dialog.dismiss();
        });
        dialog.show();
    }


    public void showCompleteOtp(int order_id, int status, String amount) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.order_complete_otp_dialog);
        AppCompatButton buttonSubmit = dialog.findViewById(R.id.buttonSubmit);
        AppCompatEditText editTextOtp = dialog.findViewById(R.id.editTextOtp);
        TextView amountTxt = dialog.findViewById(R.id.amountTxt);

        amountTxt.setText("Collected amount : ₹" + amount);
        buttonSubmit.setOnClickListener(v -> {
            String otp = editTextOtp.getText().toString();
            if (otp.isEmpty()) {
                Toast.makeText(getActivity(), "Please enter OTP.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (otp.replace(" ", "").length() != 6) {
                Toast.makeText(getActivity(), "Please enter the valid OTP.", Toast.LENGTH_SHORT).show();
                return;
            }

            // find the radiobutton by returned id
            presenter.updateStatus(strToken, order_id, status, null, otp, amount);
            dialog.dismiss();
        });
        dialog.show();
    }


    @Override
    public void onClickItem(OrdersData ordersData) {
        presenter.openOrderDetail(ordersData);
    }
}
