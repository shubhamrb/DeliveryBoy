package com.rainbow.deliveryboy.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
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

import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import butterknife.BindView;
import butterknife.OnClick;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.transports.WebSocket;
import okhttp3.OkHttpClient;


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

    @BindView(R.id.btn_call)
    ImageView btn_call;

    private SharedPreferences sharedPreferences;
    private String strToken = "";
    private OrdersData ordersData;
    private OrderItemListAdapter orderItemListAdapter;
    private IO.Options opts;
    private Socket socket;

    private final String EVENT_REJECT = "reject";
    private final String EVENT_COMPLETE = "complete";

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
    public void onPause() {
        super.onPause();
        if (socket != null) {
            socket.disconnect();
            socket.off(Socket.EVENT_CONNECT_ERROR);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        createSocketIOClient();
    }

    @Override
    protected void bindData() {
        sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        strToken = sharedPreferences.getString(Constants.TOKEN, "");

        Bundle bundle = getArguments();
        if (bundle != null) {
            ordersData = (OrdersData) bundle.getSerializable("ordersData");
            if (ordersData != null) {
                tv_order.setText("Order #" + ordersData.getOrderId());
                tv_amount.setText("₹" + ordersData.getFinal_price());
                if (ordersData.getAddress() != null) {
                    tv_address.setText(ordersData.getAddress().getAddress_1());
                    tv_title.setText(ordersData.getAddress().getName());
                }
                tv_date.setText(ordersData.getOrder_date().split("T")[0]);
                if (ordersData.getPayment_channel().equalsIgnoreCase("cod")) {
                    tv_payment.setText("Payment method- COD");
                } else if (ordersData.getPayment_channel().equalsIgnoreCase("wallet")) {
                    tv_payment.setText("Payment method- Wallet");
                } else if (ordersData.getPayment_channel().equalsIgnoreCase("Online+Wallet")) {
                    tv_payment.setText("Payment method- Online+Wallet");
                } else {
                    if (ordersData.getPayment_status().equalsIgnoreCase("pending")) {
                        tv_payment.setText("Payment method - Online (Payment not done)");
                    } else {
                        tv_payment.setText("Payment method - Online");
                    }
                }
                loadOrderDetail();

                navigate.setOnClickListener(view -> {
                    String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%s,%s (%s)", ordersData.getAddress().getLatitude(), ordersData.getAddress().getLongitude(), ordersData.getAddress().getAddress_1());
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    startActivity(intent);
                });

                buttonComplete.setOnClickListener(view -> {
                    if (!ordersData.getPayment_channel().equalsIgnoreCase("cod")
                            && !ordersData.getPayment_channel().equalsIgnoreCase("wallet")
                            && !ordersData.getPayment_channel().equalsIgnoreCase("wallet+online")
                            && ordersData.getPayment_status().equalsIgnoreCase("pending")) {
                        Toast.makeText(getActivity(), "Payment is not done yet.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    showCompleteOtp(ordersData.getId(), 8, ordersData.getFinal_price(), ordersData.getOtp());
                });

                buttonCancel.setOnClickListener(view -> {
                    showCancelReason(ordersData.getId(), 10);
                });

                btn_call.setOnClickListener(view -> {
                    try {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:" + ordersData.getAddress().getMobile()));
                        startActivity(callIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

        }
    }

    private void createSocketIOClient() {
        OkHttpClient okHttpClient = getUnsafeOkHttpClient().retryOnConnectionFailure(true).build();
        opts = new IO.Options();
//        opts.callFactory = okHttpClient;
        opts.transports = new String[]{WebSocket.NAME};
        opts.upgrade = false;
        opts.reconnection = true;
        opts.reconnectionAttempts = 10;
        opts.webSocketFactory = okHttpClient;
        try {
            socket = IO.socket("https://rainbowfresh.in:3001", opts);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        socket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Exception err = (Exception) args[0];
                Log.e("Socket : ", err.toString());
            }
        });
        socket.connect();
    }

    private static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            okhttp3.OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
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
            presenter.updateStatus(getContext(), strToken, order_id, status, radioButton.getText().toString(), null, null, EVENT_REJECT);
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
            presenter.updateStatus(getContext(), strToken, order_id, status, null, otp, amount, EVENT_COMPLETE);
            dialog.dismiss();
        });
        dialog.show();
    }

    private void loadOrderDetail() {
        presenter.getOrderDetail(getContext(), strToken, String.valueOf(ordersData.getId()));
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
    public void statusUpdated(JsonObject jsonObject, String event) {
        try {
            showMessage(jsonObject.get("message").getAsString());
            if (socket != null && socket.connected()) {
                socket.emit(event, (Emitter.Listener) args -> {
//                    String data = (String) args[0];
                    socket.off(event);
                    Log.e("Socket : ", event);
                });
            }
            getActivity().onBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
