package com.rainbow.deliveryboy.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rainbow.deliveryboy.R;
import com.rainbow.deliveryboy.model.getNotification.NotificationData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.ViewHolder> {


    private Context context;
    List<NotificationData> notificationList;

    public NotificationListAdapter(Context context, List<NotificationData> dataList) {
        this.context = context;
        this.notificationList = dataList;
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_notification_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        /*NotificationData notificationData = notificationList.get(position);
        holder.tvTitle.setText(notificationData.getTitle());
        holder.tvNotificationText.setText(notificationData.getDescription());
        holder.tvNotificationTime.setText(notificationData.getCreatedAt());

        try {
            Glide.with(context).load(notificationData.getImage())
                    .error(R.drawable.logo_icon)
                    .placeholder(R.drawable.logo_icon).into(holder.imageIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_icon)
        AppCompatImageView imageIcon;
        @BindView(R.id.tv_notification_text)
        AppCompatTextView tvNotificationText;
        @BindView(R.id.tv_notification_title)
        AppCompatTextView tvTitle;
        @BindView(R.id.tv_notification_time)
        AppCompatTextView tvNotificationTime;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}