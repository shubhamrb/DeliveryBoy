package com.rainbow.deliveryboy.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.rainbow.deliveryboy.R;
import com.rainbow.deliveryboy.base.BaseFragment;
import com.rainbow.deliveryboy.presenter.LoginPresenter;
import com.rainbow.deliveryboy.utils.Constants;
import com.rainbow.deliveryboy.views.LoginView;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginFragment extends BaseFragment<LoginPresenter, LoginView> implements LoginView {

    @BindView(R.id.editTextMobileNumber)
    AppCompatEditText editTextMobileNumber;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    SharedPreferences sharedPreferences;
    @BindView(R.id.textViewForgotPassword)
    AppCompatTextView textViewForgotPassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @BindView(R.id.imageViewNext)
    ImageView imageViewNext;
    @BindView(R.id.image)
    AppCompatImageView image;
    @BindView(R.id.frame)
    FrameLayout frame;
    @BindView(R.id.textViewMain)
    AppCompatTextView textViewMain;
    @BindView(R.id.layoutLine1)
    ImageView layoutLine1;
    @BindView(R.id.layoutEditText)
    LinearLayout layoutEditText;
    private String referral_id = null;

    @Override
    protected int createLayout() {
        return R.layout.fragment_login;
    }

    @Override
    protected void setPresenter() {
        presenter = new LoginPresenter();
    }


    @Override
    protected LoginView createView() {
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void bindData() {
        sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        Bundle bundle = getArguments();
        if (bundle != null) {
            referral_id = bundle.getString("referral_id");
        }
    }


    @OnClick({R.id.imageViewNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewNext:
                if (editTextMobileNumber.getText().toString().trim().isEmpty()) {
                    showMessage("Please enter Mobile Number");
                } else if (editTextMobileNumber.getText().length() < 10) {
                    showMessage("Please enter Valid Mobile Number");
                } else {
                    otpScreen(editTextMobileNumber.getText().toString());
                }
                break;
        }
    }

    public void otpScreen(String mobileNo) {
        OTpFragment.setMobileNumber(mobileNo);
        presenter.openOTP(referral_id);
    }


}