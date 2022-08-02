package com.rainbow.deliveryboy.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rainbow.deliveryboy.R;
import com.rainbow.deliveryboy.model.getOrders.OrderItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OrderItemListAdapter extends RecyclerView.Adapter<OrderItemListAdapter.ViewHolder> {


    private Context context;
    List<OrderItem> list;

    public OrderItemListAdapter(Context context, List<OrderItem> dataList) {
        this.context = context;
        this.list = dataList;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_order_product_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        OrderItem ordersData = list.get(position);
        holder.tv_title.setText(ordersData.getProduct_name());
        holder.tv_amount.setText("Amount - ₹" + ordersData.getFinal_price());
        holder.tv_qty.setText("(Qty : " + ordersData.getQty() + ") - " + ordersData.getWeight() + " " + ordersData.getWeight_unit());

        try {
            Glide.with(context).load(ordersData.getProduct().getFeatured_image())
                    .error(R.drawable.logo_icon)
                    .placeholder(R.drawable.logo_icon).into(holder.imageIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_icon)
        AppCompatImageView imageIcon;
        @BindView(R.id.tv_title)
        AppCompatTextView tv_title;
        @BindView(R.id.tv_amount)
        AppCompatTextView tv_amount;
        @BindView(R.id.tv_qty)
        AppCompatTextView tv_qty;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}