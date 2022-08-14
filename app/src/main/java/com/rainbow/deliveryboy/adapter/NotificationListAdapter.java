package com.rainbow.deliveryboy.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rainbow.deliveryboy.R;
import com.rainbow.deliveryboy.model.getNotification.NotificationData;

import java.text.MessageFormat;
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
        return notificationList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_notification_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        NotificationData notificationData = notificationList.get(position);
        holder.tvTitle.setText(notificationData.getNoti_type());
        holder.tvNotificationText.setText(notificationData.getMessage());
        String[] arry = notificationData.getCreatedAt().split("T");
        String date = arry[0];
        String time = arry[1];
//        2022-08-14T12:21:49.000Z
        try {
            holder.tvNotificationTime.setText(MessageFormat.format("{0} {1}", date, time.substring(0, 8)));
        } catch (Exception e) {
            e.printStackTrace();
            holder.tvNotificationTime.setText("");
        }
    }

    public void setList(List<NotificationData> list) {
        this.notificationList = list;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
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