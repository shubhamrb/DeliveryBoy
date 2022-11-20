package com.rainbow.deliveryboy.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
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

import java.net.URISyntaxException;
import java.security.cert.CertificateException;

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
    private IO.Options opts;
    private Socket socket;
    private final String EVENT_NEW_ORDER = "neworder";

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
            getDashboardData(false);
            pullToRefresh.setRefreshing(false);
        });
        getDashboardData(true);

        btn_complete.setOnClickListener(view -> {
            onBoxClick(8);
        });
        btn_pending.setOnClickListener(view -> {
            onBoxClick(11);
        });
        /*btn_rejected.setOnClickListener(view -> {
            onBoxClick(7);
        });*/
        btn_total.setOnClickListener(view -> {
            onBoxClick(0);
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        if (socket != null) {
            socket.disconnect();
            socket.off(EVENT_NEW_ORDER);
            socket = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (socket != null) {
            socket.disconnect();
            socket.off(EVENT_NEW_ORDER);
            socket = null;
        }
        createSocketIOClient();
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

        socket.on(EVENT_NEW_ORDER, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
//                String data = (String) args[0];
                Log.e("Socket : ", "new order received");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getDashboardData(false);
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

    private void onBoxClick(int status) {
        sharedPreferences.edit().putInt(Constants.TAB, 1).apply();
        switchTab(status);
    }

    private void getDashboardData(boolean showLoader) {
        presenter.getDashboardData(strToken, getContext(), showLoader);
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @OnClick({R.id.btn_request})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_request:
                if (data != null && data.getWallet_amount() != null && Double.parseDouble(data.getWallet_amount()) > 0)
                    presenter.requestAmountSubmit(getContext(), strToken, data.getWallet_amount(), data.getStoreId());
                break;
        }
    }

    @Override
    public void setDashboardData(DashboardData dashboardData) {
        try {
            data = dashboardData;
            text_completed.setText("Completed\n" + dashboardData.getCompleteOrder());
            text_pending.setText("Request\nPending\n" + dashboardData.getPaddingOrder());
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
