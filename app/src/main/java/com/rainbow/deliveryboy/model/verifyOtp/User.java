package com.rainbow.deliveryboy.model.verifyOtp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("first_name")
    @Expose
    private String first_name;

    @SerializedName("last_name")
    @Expose
    private String last_name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("address_1")
    @Expose
    private String address_1;

    @SerializedName("address_2")
    @Expose
    private String address_2;

    @SerializedName("address_3")
    @Expose
    private String address_3;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("state")
    @Expose
    private String state;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("zipcode")
    @Expose
    private String zipcode;

    @SerializedName("role")
    @Expose
    private String role;

    @SerializedName("is_active")
    @Expose
    private boolean is_active;

    @SerializedName("is_approve")
    @Expose
    private boolean is_approve;

    @SerializedName("is_display_frontend")
    @Expose
    private boolean is_display_frontend;

    @SerializedName("recieve_newsletter")
    @Expose
    private boolean recieve_newsletter;

    @SerializedName("user_image_path")
    @Expose
    private String user_image_path;

    @SerializedName("logo")
    @Expose
    private String logo;

    @SerializedName("banner_image")
    @Expose
    private String banner_image;

    @SerializedName("verified")
    @Expose
    private boolean verified;

    @SerializedName("bank_name")
    @Expose
    private String bank_name;

    @SerializedName("account_number")
    @Expose
    private String account_number;

    @SerializedName("account_holder_name")
    @Expose
    private String account_holder_name;

    @SerializedName("business_name")
    @Expose
    private String business_name;

    @SerializedName("store_name")
    @Expose
    private String store_name;

    @SerializedName("reset_password_token")
    @Expose
    private String reset_password_token;

    @SerializedName("account_verified_token")
    @Expose
    private String account_verified_token;

    @SerializedName("latitude")
    @Expose
    private String latitude;

    @SerializedName("longitude")
    @Expose
    private String longitude;

    @SerializedName("reset_password_expires")
    @Expose
    private String reset_password_expires;

    @SerializedName("device_type")
    @Expose
    private String device_type;

    @SerializedName("device_token")
    @Expose
    private String device_token;

    @SerializedName("is_terms")
    @Expose
    private String is_terms;

    @SerializedName("approval_date")
    @Expose
    private String approval_date;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress_1() {
        return address_1;
    }

    public void setAddress_1(String address_1) {
        this.address_1 = address_1;
    }

    public String getAddress_2() {
        return address_2;
    }

    public void setAddress_2(String address_2) {
        this.address_2 = address_2;
    }

    public String getAddress_3() {
        return address_3;
    }

    public void setAddress_3(String address_3) {
        this.address_3 = address_3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public boolean isIs_approve() {
        return is_approve;
    }

    public void setIs_approve(boolean is_approve) {
        this.is_approve = is_approve;
    }

    public boolean isIs_display_frontend() {
        return is_display_frontend;
    }

    public void setIs_display_frontend(boolean is_display_frontend) {
        this.is_display_frontend = is_display_frontend;
    }

    public boolean isRecieve_newsletter() {
        return recieve_newsletter;
    }

    public void setRecieve_newsletter(boolean recieve_newsletter) {
        this.recieve_newsletter = recieve_newsletter;
    }

    public String getUser_image_path() {
        return user_image_path;
    }

    public void setUser_image_path(String user_image_path) {
        this.user_image_path = user_image_path;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBanner_image() {
        return banner_image;
    }

    public void setBanner_image(String banner_image) {
        this.banner_image = banner_image;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getAccount_holder_name() {
        return account_holder_name;
    }

    public void setAccount_holder_name(String account_holder_name) {
        this.account_holder_name = account_holder_name;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getReset_password_token() {
        return reset_password_token;
    }

    public void setReset_password_token(String reset_password_token) {
        this.reset_password_token = reset_password_token;
    }

    public String getAccount_verified_token() {
        return account_verified_token;
    }

    public void setAccount_verified_token(String account_verified_token) {
        this.account_verified_token = account_verified_token;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getReset_password_expires() {
        return reset_password_expires;
    }

    public void setReset_password_expires(String reset_password_expires) {
        this.reset_password_expires = reset_password_expires;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getIs_terms() {
        return is_terms;
    }

    public void setIs_terms(String is_terms) {
        this.is_terms = is_terms;
    }

    public String getApproval_date() {
        return approval_date;
    }

    public void setApproval_date(String approval_date) {
        this.approval_date = approval_date;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}