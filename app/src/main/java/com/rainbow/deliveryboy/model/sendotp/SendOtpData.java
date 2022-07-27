package com.rainbow.deliveryboy.model.sendotp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SendOtpData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("email_verified_at")
    @Expose
    private String emailVerifiedAt;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("role_id")
    @Expose
    private Integer roleId;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("about_me")
    @Expose
    private String aboutMe;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("google_token")
    @Expose
    private String googleToken;
    @SerializedName("fb_token")
    @Expose
    private String fbToken;
    @SerializedName("total_lift")
    @Expose
    private Integer totalLift;
    @SerializedName("lift_giver")
    @Expose
    private Integer liftGiver;
    @SerializedName("lift_taker")
    @Expose
    private Integer liftTaker;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("total_review")
    @Expose
    private Integer totalReview;
    @SerializedName("driver_details")
    @Expose
    private String driverDetails;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(String emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getGoogleToken() {
        return googleToken;
    }

    public void setGoogleToken(String googleToken) {
        this.googleToken = googleToken;
    }

    public String getFbToken() {
        return fbToken;
    }

    public void setFbToken(String fbToken) {
        this.fbToken = fbToken;
    }

    public Integer getTotalLift() {
        return totalLift;
    }

    public void setTotalLift(Integer totalLift) {
        this.totalLift = totalLift;
    }

    public Integer getLiftGiver() {
        return liftGiver;
    }

    public void setLiftGiver(Integer liftGiver) {
        this.liftGiver = liftGiver;
    }

    public Integer getLiftTaker() {
        return liftTaker;
    }

    public void setLiftTaker(Integer liftTaker) {
        this.liftTaker = liftTaker;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Integer getTotalReview() {
        return totalReview;
    }

    public void setTotalReview(Integer totalReview) {
        this.totalReview = totalReview;
    }

    public String getDriverDetails() {
        return driverDetails;
    }

    public void setDriverDetails(String driverDetails) {
        this.driverDetails = driverDetails;
    }

}
