package com.rainbow.deliveryboy.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.rainbow.deliveryboy.R;
import com.rainbow.deliveryboy.utils.Constants;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferencesIntro;
    private String referral_id = null;
    private String notification_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferencesIntro = getSharedPreferences(Constants.SHARED_PREF_INTRO, Context.MODE_PRIVATE);

        if (getIntent().hasExtra("type")) {
            notification_type = getIntent().getStringExtra("type");
        }
        goToNext();
    }

    private void goToNext() {
        new Handler().postDelayed(() -> {
            if (sharedPreferencesIntro.getBoolean(Constants.IS_INTRO, false)) {
                if (sharedPreferences.getBoolean(Constants.IS_LOGIN, false)) {
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.putExtra("referral_id", referral_id);
                    if (notification_type != null) {
                        intent.putExtra("type", notification_type);
                    }
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, AuthActivity.class);
                    intent.putExtra("referral_id", referral_id);

                    if (notification_type != null) {
                        intent.putExtra("type", notification_type);
                    }
                    startActivity(intent);
                }
            } else {
                startActivity(new Intent(this, MainActivity.class)
                        .putExtra("referral_id", referral_id));
            }
            finish();
        }, 2000);
    }

    public static String printKeyHash(Context context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }
}
