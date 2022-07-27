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


public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {


    private Context context;
    List<NotificationData> list;


    public OrderListAdapter(Context context, List<NotificationData> dataList) {
        this.context = context;
        this.list = dataList;
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_order_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (position % 2 == 0) {
            holder.btn_accept.setVisibility(View.GONE);
            holder.btn_reject.setVisibility(View.GONE);
            holder.tv_status.setVisibility(View.VISIBLE);
        } else {
            holder.tv_status.setVisibility(View.GONE);
            holder.btn_accept.setVisibility(View.VISIBLE);
            holder.btn_reject.setVisibility(View.VISIBLE);
        }
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

    public void setList(List<NotificationData> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_icon)
        AppCompatImageView imageIcon;
        @BindView(R.id.tv_title)
        AppCompatTextView tv_title;
        @BindView(R.id.tv_address)
        AppCompatTextView tv_address;
        @BindView(R.id.tv_date)
        AppCompatTextView tv_date;
        @BindView(R.id.tv_status)
        AppCompatTextView tv_status;
        @BindView(R.id.btn_accept)
        AppCompatTextView btn_accept;
        @BindView(R.id.btn_reject)
        AppCompatTextView btn_reject;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}