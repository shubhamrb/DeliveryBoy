package com.rainbow.deliveryboy.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rainbow.deliveryboy.R;
import com.rainbow.deliveryboy.activity.HomeActivity;
import com.rainbow.deliveryboy.adapter.NotificationListAdapter;
import com.rainbow.deliveryboy.base.BaseFragment;
import com.rainbow.deliveryboy.model.getNotification.NotificationData;
import com.rainbow.deliveryboy.presenter.NotificationPresenter;
import com.rainbow.deliveryboy.utils.Constants;
import com.rainbow.deliveryboy.utils.LoadMore;
import com.rainbow.deliveryboy.views.NotificationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends BaseFragment<NotificationPresenter, NotificationView>
        implements NotificationView, NotificationListAdapter.onClickListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolBarTitle)
    AppCompatTextView toolBarTitle;
    SharedPreferences sharedPreferences;
    @BindView(R.id.imageViewBack)
    ImageView imageViewBack;
    @BindView(R.id.recyclerViewNotification)
    RecyclerView recyclerViewNotification;
    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout pullToRefresh;

    private final int PAGE_LIMIT = 10;
    private int CURRENT_PAGE = 0;
    private LoadMore mLoadMore;
    private boolean isLoadMore = false;
    private NotificationListAdapter adapter;
    List<NotificationData> list;
    private String strToken = "";

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
        strToken = sharedPreferences.getString(Constants.TOKEN, "");


        list = new ArrayList<>();
        mLoadMore = new LoadMore(recyclerViewNotification);
        mLoadMore.setLoadingMore(false);
        recyclerViewNotification.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NotificationListAdapter(getContext(), list, this);
        recyclerViewNotification.setAdapter(adapter);

        mLoadMore.setOnLoadMoreListener(() -> {
            if (isLoadMore) {
                CURRENT_PAGE++;
                loadNotifications();
            }
        });
        pullToRefresh.setOnRefreshListener(() -> {
            CURRENT_PAGE = 0;
            list.clear();
            mLoadMore.setLoadingMore(false);
            loadNotifications();
            pullToRefresh.setRefreshing(false);
        });

        loadNotifications();
    }

    private void loadNotifications() {
        presenter.getAllNotification(strToken, CURRENT_PAGE, PAGE_LIMIT);
    }


    @OnClick(R.id.imageViewBack)
    public void onViewClicked() {
        getActivity().onBackPressed();
    }

    @Override
    public void setNotificationData(List<NotificationData> notificationData) {
        if (CURRENT_PAGE == 0) {
            list.clear();
        }
        if (notificationData.size() > 0) {
            list.addAll(notificationData);
            mLoadMore.setLoadingMore(true);
            adapter.setList(list);
        } else {
            mLoadMore.setLoadingMore(false);
        }
    }

    @Override
    public void onClick() {
        try {
            sharedPreferences.edit().putInt(Constants.TAB, 1).apply();
            getActivity().onBackPressed();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
