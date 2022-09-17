package com.rainbow.deliveryboy.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rainbow.deliveryboy.R;
import com.rainbow.deliveryboy.model.getOrders.OrdersData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {


    private Context context;
    List<OrdersData> list;
    private onClickListener listener;

    public OrderListAdapter(Context context, List<OrdersData> dataList, onClickListener listener) {
        this.context = context;
        this.list = dataList;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_order_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        OrdersData ordersData = list.get(position);
        holder.tv_id.setText("#" + ordersData.getOrderId());

        if (ordersData.getAddress() != null) {
            holder.tv_title.setText("" + ordersData.getAddress().getName());
            holder.tv_address.setText("" + ordersData.getAddress().getAddress_1());
        }

        if (ordersData.getOrder_date() != null) {
            holder.tv_date.setText(ordersData.getOrder_date().split("T")[0]);
        }

        /*try {
            Glide.with(context).load(notificationData.getImage())
                    .error(R.drawable.logo_icon)
                    .placeholder(R.drawable.logo_icon).into(holder.imageIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        int assign_status = ordersData.getAssign_status();

        holder.tv_status.setVisibility(View.GONE);
        holder.btn_accept.setVisibility(View.GONE);
        holder.btn_reject.setVisibility(View.GONE);
        holder.btn_call.setVisibility(View.VISIBLE);
        holder.ll_address.setVisibility(View.VISIBLE);

        switch (assign_status) {
            case 0:
/*                if (ordersData.getStatus() == 8) {
                    holder.btn_complete.setVisibility(View.GONE);
                    holder.tv_status.setVisibility(View.VISIBLE);
                    holder.tv_status.setTextColor(context.getResources().getColor(R.color.colorPurple));
                    holder.tv_status.setText("Delivered");
                } else {*/
                holder.btn_call.setVisibility(View.GONE);
                holder.btn_accept.setVisibility(View.VISIBLE);
                holder.btn_reject.setVisibility(View.VISIBLE);
//                }
                break;
            case 1:
                holder.tv_status.setVisibility(View.VISIBLE);
                if (ordersData.getStatus() == 8) {
                    holder.btn_call.setVisibility(View.GONE);
                    holder.tv_status.setVisibility(View.VISIBLE);
                    holder.tv_status.setTextColor(context.getResources().getColor(R.color.colorPurple));
                    holder.tv_status.setText("Delivered");
                } else {
                    holder.tv_status.setText("Accepted");
                    holder.tv_status.setTextColor(context.getResources().getColor(R.color.colorGreen));
                }

                break;
            case 2:
                holder.ll_address.setVisibility(View.GONE);
                holder.btn_call.setVisibility(View.GONE);
                holder.tv_status.setVisibility(View.VISIBLE);
                holder.tv_status.setText("Canceled");
                holder.tv_status.setTextColor(context.getResources().getColor(R.color.colorRed));
                break;
        }

        holder.btn_accept.setOnClickListener(view -> {
            listener.onClickButton(ordersData, 9);
        });
        holder.btn_reject.setOnClickListener(view -> {
            listener.onClickButton(ordersData, 10);
        });
        holder.btn_call.setOnClickListener(view -> {
            //call
            listener.onClickCall(ordersData);
        });

        holder.itemView.setOnClickListener(view -> {
            if (assign_status == 1 && ordersData.getStatus() != 8)
                listener.onClickItem(ordersData);
        });

    }

    public interface onClickListener {
        void onClickButton(OrdersData ordersData, int status);

        void onClickItem(OrdersData ordersData);

        void onClickCall(OrdersData ordersData);
    }

    public void setList(List<OrdersData> list) {
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
        @BindView(R.id.tv_id)
        AppCompatTextView tv_id;
        @BindView(R.id.btn_call)
        ImageView btn_call;
        @BindView(R.id.ll_address)
        LinearLayout ll_address;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}