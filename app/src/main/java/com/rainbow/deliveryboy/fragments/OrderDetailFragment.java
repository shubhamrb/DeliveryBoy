package com.rainbow.deliveryboy.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;
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
    @BindView(R.id.buttonCancel)
    AppCompatButton buttonCancel;
    @BindView(R.id.buttonComplete)
    AppCompatButton buttonComplete;

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

                buttonComplete.setOnClickListener(view -> {
                    showCompleteOtp(ordersData.getId(), ordersData.getStatus(), ordersData.getFinal_price(), ordersData.getOtp());
                });

                buttonCancel.setOnClickListener(view -> {
                    showCancelReason(ordersData.getId(), ordersData.getStatus());
                });
            }
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


    public void showCompleteOtp(int order_id, int status, String amount, String strotp) {
        boolean otpRequired = strotp != null && !strotp.isEmpty();

        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.order_complete_otp_dialog);
        AppCompatButton buttonSubmit = dialog.findViewById(R.id.buttonSubmit);
        AppCompatEditText editTextOtp = dialog.findViewById(R.id.editTextOtp);
        TextView titleTxt = dialog.findViewById(R.id.titleTxt);
        LinearLayout otpLayout = dialog.findViewById(R.id.otpLayout);
        TextView amountTxt = dialog.findViewById(R.id.amountTxt);
        RadioButton radio = dialog.findViewById(R.id.radio);

        if (otpRequired) {
            titleTxt.setVisibility(View.VISIBLE);
            otpLayout.setVisibility(View.VISIBLE);
        } else {
            titleTxt.setVisibility(View.GONE);
            otpLayout.setVisibility(View.GONE);
        }

        amountTxt.setText("₹" + amount);
        buttonSubmit.setOnClickListener(v -> {
            String otp = editTextOtp.getText().toString();
            if (otpRequired && otp.isEmpty()) {
                Toast.makeText(getActivity(), "Please enter OTP.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (otpRequired && otp.replace(" ", "").length() != 6) {
                Toast.makeText(getActivity(), "Please enter the valid OTP.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!radio.isChecked()) {
                Toast.makeText(getActivity(), "Please confirm that you have collected the amount.", Toast.LENGTH_SHORT).show();
                return;
            }

            // find the radiobutton by returned id
            presenter.updateStatus(strToken, order_id, status, null, otp, amount);
            dialog.dismiss();
        });
        dialog.show();
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

    @Override
    public void statusUpdated(JsonObject jsonObject) {
        try {
            showMessage(jsonObject.get("message").getAsString());
            getActivity().onBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
