package com.rainbow.deliveryboy.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.rainbow.deliveryboy.R;
import com.rainbow.deliveryboy.activity.HomeActivity;
import com.rainbow.deliveryboy.base.BaseFragment;
import com.rainbow.deliveryboy.model.sendotp.SendOtpResponse;
import com.rainbow.deliveryboy.model.verifyOtp.VerifyOtpResponse;
import com.rainbow.deliveryboy.presenter.OtpPresenter;
import com.rainbow.deliveryboy.utils.Constants;
import com.rainbow.deliveryboy.views.OtpView;

import butterknife.BindView;
import butterknife.OnClick;


public class OTpFragment extends BaseFragment<OtpPresenter, OtpView> implements OtpView {

    SharedPreferences sharedPreferences;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @BindView(R.id.editTextFirstDigits)
    AppCompatEditText editTextFirstDigits;
    @BindView(R.id.editTextSecondsDigits)
    AppCompatEditText editTextSecondsDigits;
    @BindView(R.id.editTextThirdDigits)
    AppCompatEditText editTextThirdDigits;
    @BindView(R.id.editTextFourthDigits)
    AppCompatEditText editTextFourthDigits;
    @BindView(R.id.editTextFive)
    AppCompatEditText editTextFive;
    @BindView(R.id.editTextSix)
    AppCompatEditText editTextSix;
    @BindView(R.id.textViewResendOtp)
    AppCompatTextView textViewResendOtp;
    @BindView(R.id.textViewBack)
    AppCompatTextView textViewBack;
    @BindView(R.id.imageViewNext)
    ImageView imageViewNext;
    @BindView(R.id.reltive)
    RelativeLayout reltive;

    static String otp, mobileNo = "";
    private String referral_id;

    public static void setMobileNumber(String mobileNo) {
        OTpFragment.mobileNo = mobileNo;
    }


    @Override
    protected int createLayout() {
        return R.layout.fragment_otp;
    }

    @Override
    protected void setPresenter() {
        presenter = new OtpPresenter();
    }


    @Override
    protected OtpView createView() {
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
        reverseTimer(30, textViewResendOtp);

        sendCode();

        editTextFirstDigits.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    editTextSecondsDigits.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextSecondsDigits.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 0) {
                    editTextThirdDigits.requestFocus();
                } else {
                    editTextFirstDigits.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextThirdDigits.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    editTextFourthDigits.requestFocus();
                } else {
                    editTextSecondsDigits.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextFourthDigits.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 0) {
                    editTextFive.requestFocus();
                } else {
                    editTextThirdDigits.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imageViewNext.setOnClickListener(view -> {
            try {
                otp = editTextFirstDigits.getText().toString() + editTextSecondsDigits.getText().toString()
                        + editTextThirdDigits.getText().toString() + editTextFourthDigits.getText().toString();
                doVerifyCode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        textViewBack.setOnClickListener(view -> {
            try {
                presenter.openLogin(referral_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public void reverseTimer(int Seconds, final TextView tv) {

        new CountDownTimer(Seconds * 1000 + 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                if (getContext() != null) {
                    tv.setTextColor(getContext().getResources().getColor(R.color.colorBlack));
                    int seconds = (int) (millisUntilFinished / 1000);
                    int minutes = seconds / 60;
                    seconds = seconds % 60;
                    tv.setText(getContext().getResources().getString(R.string.resend_otp_in) + String.format("%02d", minutes)
                            + ":" + String.format("%02d", seconds));
                    tv.setEnabled(false);
                }
            }

            public void onFinish() {
                if (getContext() != null) {
                    tv.setText(getContext().getResources().getString(R.string.resend_otp));
                    tv.setEnabled(true);
                    tv.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
                }
            }
        }.start();
    }

    @Override
    public void setLoginData(VerifyOtpResponse response) {
        sharedPreferences.edit().putBoolean(Constants.IS_LOGIN, true).apply();
        sharedPreferences.edit().putString(Constants.TOKEN, response.getToken()).apply();
        sharedPreferences.edit().putString(Constants.NAME, response.getUser().getFirst_name()).apply();
        sharedPreferences.edit().putString(Constants.EMAIL, response.getUser().getEmail()).apply();
        sharedPreferences.edit().putString(Constants.MOBILE, response.getUser().getPhone()).apply();
        sharedPreferences.edit().putString(Constants.USER_ID, String.valueOf(response.getUser().getId())).apply();
        sharedPreferences.edit().putString(Constants.IMAGE, String.valueOf(response.getUser().getUser_image_path())).apply();
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.putExtra("referral_id", referral_id);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void setOtpSent(SendOtpResponse response) {
        Toast.makeText(getActivity(), "OTP sent.", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.textViewResendOtp)
    public void onViewClicked() {
        reverseTimer(30, textViewResendOtp);
        sendCode();
    }

    private void sendCode() {
        if (mobileNo != null && !mobileNo.isEmpty()) {
            presenter.sendOtp(mobileNo);
        }
    }


    private void doVerifyCode() {
        if (TextUtils.isEmpty(otp) || !TextUtils.isDigitsOnly(otp) || otp.length() != 4) {
            Toast.makeText(getActivity(), "Please enter valid OTP.", Toast.LENGTH_LONG).show();
            return;
        }
        presenter.verifyOtp(otp, mobileNo);
    }

}
