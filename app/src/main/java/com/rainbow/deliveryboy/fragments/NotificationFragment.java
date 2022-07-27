package com.rainbow.deliveryboy.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rainbow.deliveryboy.R;
import com.rainbow.deliveryboy.adapter.NotificationListAdapter;
import com.rainbow.deliveryboy.base.BaseFragment;
import com.rainbow.deliveryboy.model.getNotification.NotificationData;
import com.rainbow.deliveryboy.presenter.NotificationPresenter;
import com.rainbow.deliveryboy.utils.Constants;
import com.rainbow.deliveryboy.views.NotificationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends BaseFragment<NotificationPresenter, NotificationView> implements NotificationView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolBarTitle)
    AppCompatTextView toolBarTitle;
    SharedPreferences sharedPreferences;
    @BindView(R.id.imageViewBack)
    ImageView imageViewBack;
    @BindView(R.id.recyclerViewNotification)
    RecyclerView recyclerViewNotification;

    @Override
    protected int createLayout() {
        return R.layout.fragment_notification;
    }

    @Override
    protected void setPresenter() {
        presenter = new NotificationPresenter();
    }


    @Override
    protected NotificationView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        toolBarTitle.setText("Notification");
//        presenter.getAllNotification(sharedPreferences.getString(Constants.TOKEN, ""));
        setNotificationData(new ArrayList<>());
    }


    @OnClick(R.id.imageViewBack)
    public void onViewClicked() {
        getActivity().onBackPressed();
    }

    @Override
    public void setNotificationData(List<NotificationData> notificationData) {
        recyclerViewNotification.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewNotification.setAdapter(new NotificationListAdapter(getContext(), notificationData));

    }
}
