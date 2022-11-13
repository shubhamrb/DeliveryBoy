package com.rainbow.deliveryboy.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.JsonObject;
import com.rainbow.deliveryboy.R;
import com.rainbow.deliveryboy.adapter.OrderListAdapter;
import com.rainbow.deliveryboy.base.BaseFragment;
import com.rainbow.deliveryboy.model.getOrders.OrdersData;
import com.rainbow.deliveryboy.presenter.OrdersPresenter;
import com.rainbow.deliveryboy.utils.Constants;
import com.rainbow.deliveryboy.utils.LoadMore;
import com.rainbow.deliveryboy.views.OrdersView;

import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

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
public class OrdersFragment extends BaseFragment<OrdersPresenter, OrdersView>
        implements OrdersView, OrderListAdapter.onClickListener {

    @BindView(R.id.recyclerViewOrder)
    RecyclerView recyclerViewOrder;
    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout pullToRefresh;
    @BindView(R.id.btn_new_order)
    CardView btn_new_order;
    private SharedPreferences sharedPreferences;

    private final int PAGE_LIMIT = 10;
    private int CURRENT_PAGE = 0;
    private LoadMore mLoadMore;
    private boolean isLoadMore = false;
    private OrderListAdapter orderListAdapter;
    private List<OrdersData> orderList;
    private String strToken = "";
    private int STATUS = 0;
    private Socket socket;
    private IO.Options opts;
    private final String EVENT_WELCOME = "welcome";
    private final String EVENT_NEW_ORDER = "neworder";
    private final String EVENT_ACEPET = "accept";
    private final String EVENT_REJECT = "reject";
    private final String EVENT_COMPLETE = "complete";

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
    public void onPause() {
        super.onPause();
        if (socket != null) {
            socket.disconnect();
            socket.off(EVENT_NEW_ORDER);
            socket.off(Socket.EVENT_CONNECT_ERROR);
            socket = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (socket != null) {
            socket.disconnect();
            socket.off(EVENT_NEW_ORDER);
            socket.off(Socket.EVENT_CONNECT_ERROR);
            socket = null;
        }
        createSocketIOClient();
    }

    @Override
    protected void bindData() {
        sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        strToken = sharedPreferences.getString(Constants.TOKEN, "");

        setUpRecycler();

        pullToRefresh.setOnRefreshListener(() -> {
            CURRENT_PAGE = 0;
            orderList.clear();
            mLoadMore.setLoadingMore(false);
            loadOrders(0);
            pullToRefresh.setRefreshing(false);
        });
        btn_new_order.setOnClickListener(view -> {
            btn_new_order.setVisibility(View.GONE);
            CURRENT_PAGE = 0;
            orderList.clear();
            mLoadMore.setLoadingMore(false);
            loadOrders(0);
        });
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

        socket.on(EVENT_WELCOME, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String data = (String) args[0];
                socket.off(EVENT_WELCOME);
                Log.e("Socket : ", data);
            }
        }).on(EVENT_NEW_ORDER, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
//                String data = (String) args[0];
                Log.e("Socket : ", "new order received");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btn_new_order.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Exception err = (Exception) args[0];
                Log.e("Socket : ", err.toString());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btn_new_order.setVisibility(View.GONE);
                    }
                });
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

    private void setUpRecycler() {
        orderList = new ArrayList<>();
        mLoadMore = new LoadMore(recyclerViewOrder);
        mLoadMore.setLoadingMore(false);
        recyclerViewOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        orderListAdapter = new OrderListAdapter(getContext(), orderList, this);
        recyclerViewOrder.setAdapter(orderListAdapter);

        mLoadMore.setOnLoadMoreListener(() -> {
            if (isLoadMore) {
                CURRENT_PAGE++;
                loadOrders(STATUS);
            }
        });
        loadOrders(STATUS);
    }


    private void loadOrders(int status) {
        presenter.getOrders(getContext(), strToken, CURRENT_PAGE, PAGE_LIMIT, status);
    }

    public void filterStatus(int status) {
        try {
            STATUS = status;
            setUpRecycler();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

            try {
                if (CURRENT_PAGE==0){
                    recyclerViewOrder.smoothScrollToPosition(0);
                }
            }catch (Exception e){
             e.printStackTrace();
            }

        } else {
            mLoadMore.setLoadingMore(false);
        }
    }

    @Override
    public void statusUpdated(JsonObject jsonObject, String event) {
        showMessage(jsonObject.get("message").getAsString());
        if (socket != null && socket.connected()) {
            socket.emit(event, (Emitter.Listener) args -> {
//                String data = (String) args[0];
                socket.off(event);
                Log.e("Socket : ", event);
            });
        }
        loadOrders(STATUS);
    }

    @Override
    public void onClickButton(OrdersData ordersData, int status) {
        if (status == 10) {
            /*cancel*/
            showCancelReason(ordersData.getId(), status);
        } else if (status == 8) {
            /*complete*/
            showCompleteOtp(ordersData.getId(), status, ordersData.getFinal_price(), ordersData.getOtp());
        } else {
            presenter.updateStatus(getContext(), strToken, ordersData.getId(), status, null, null, null, EVENT_ACEPET);
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

        amountTxt.setText("Collected amount : ₹" + amount);

        if (otpRequired) {
            titleTxt.setVisibility(View.VISIBLE);
            otpLayout.setVisibility(View.VISIBLE);
        } else {
            titleTxt.setVisibility(View.GONE);
            otpLayout.setVisibility(View.GONE);
        }
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


    @Override
    public void onClickItem(OrdersData ordersData) {
        presenter.openOrderDetail(ordersData);
    }

    @Override
    public void onClickCall(OrdersData ordersData) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + ordersData.getAddress().getMobile()));
            startActivity(callIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
