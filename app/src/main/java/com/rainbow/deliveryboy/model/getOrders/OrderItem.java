package com.rainbow.deliveryboy.model.getOrders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderItem implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("product_name")
    @Expose
    private String product_name;

    @SerializedName("final_price")
    @Expose
    private String final_price;

    @SerializedName("qty")
    @Expose
    private int qty;

    @SerializedName("weight")
    @Expose
    private String weight;

    @SerializedName("weight_unit")
    @Expose
    private String weight_unit;

    @SerializedName("product")
    @Expose
    private ProductData product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getFinal_price() {
        return final_price;
    }

    public void setFinal_price(String final_price) {
        this.final_price = final_price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWeight_unit() {
        return weight_unit;
    }

    public void setWeight_unit(String weight_unit) {
        this.weight_unit = weight_unit;
    }

    public ProductData getProduct() {
        return product;
    }

    public void setProduct(ProductData product) {
        this.product = product;
    }
}
