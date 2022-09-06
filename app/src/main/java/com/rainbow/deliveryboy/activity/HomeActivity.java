package com.rainbow.deliveryboy.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.rainbow.deliveryboy.R;
import com.rainbow.deliveryboy.adapter.MenuListAdapter;
import com.rainbow.deliveryboy.fragments.HomeFragment;
import com.rainbow.deliveryboy.model.MenuItem;
import com.rainbow.deliveryboy.notification.NotificationService;
import com.rainbow.deliveryboy.provider.AppNavigationProvider;
import com.rainbow.deliveryboy.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class HomeActivity extends AppNavigationProvider implements MenuListAdapter.ItemListener {

    private static final String TAG = HomeActivity.class.getSimpleName();
    @BindView(R.id.placeHolder)
    FrameLayout placeHolder;
    @BindView(R.id.linerLayoutRoot)
    LinearLayout linerLayoutRoot;
    @BindView(R.id.left_drawer)
    RecyclerView leftDrawer;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    List<MenuItem> menuList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    @BindView(R.id.nav_view)
    NavigationView navView;
    TextView textViewName;
    TextView textViewNumber;
    CircleImageView imageViewConactImage;
    View header;
    LocationManager locationManager;

    @BindView(R.id.imfacebook)
    ImageView imfacebook;
    @BindView(R.id.iminstagram)
    ImageView iminstagram;
    @BindView(R.id.imtwitter)
    ImageView imtwitter;
    @BindView(R.id.imlinkedin)
    ImageView imlinkedin;

    FragmentManager fragmentManager;

    private final String id = "";
    private final String type = "";
    private String referral_id = null;

    @Override
    public int getPlaceHolder() {
        return R.id.placeHolder;
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        try {
            startService(new Intent(this, NotificationService.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

        referral_id = getIntent().getStringExtra("referral_id");

        if (referral_id != null) {
//            sendReferral();
        }

        fragmentManager = getSupportFragmentManager();
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        header = navView.getHeaderView(0);
        textViewName = header.findViewById(R.id.textViewName);
        textViewNumber = header.findViewById(R.id.textViewNumber);
        imageViewConactImage = header.findViewById(R.id.imageViewConactImage);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        header.setOnClickListener(v -> {
            closeDrawer();
//            openProfileFragment(PerformFragment.REPLACE);
        });
//        checkAndRequestPermissions();

        menuList.add(new MenuItem(1, getResources().getString(R.string.my_orders), R.drawable.my_ride));
//        menuList.add(new MenuItem(2, "Task Report", R.drawable.wallet));
        /*menuList.add(new MenuItem(3, getResources().getString(R.string.my_vehicle), R.drawable.my_vehicle));
        menuList.add(new MenuItem(4, getResources().getString(R.string.my_chat), R.drawable.chats));
        menuList.add(new MenuItem(5, "Refer & Earn", R.drawable.refer));
        menuList.add(new MenuItem(6, getResources().getString(R.string.help_ticket), R.drawable.help));
        menuList.add(new MenuItem(7, getResources().getString(R.string.setting), R.drawable.setting));*/
        menuList.add(new MenuItem(8, getResources().getString(R.string.logout), R.drawable.logout));

        leftDrawer.setLayoutManager(new LinearLayoutManager(this));
        MenuListAdapter menuListAdapter = new MenuListAdapter(this, menuList, this);
        leftDrawer.setAdapter(menuListAdapter);
        openHomeFragment(PerformFragment.REPLACE);
        imfacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com"));
                startActivity(intent);
            }
        });

        iminstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com"));
                startActivity(intent);
            }
        });
        imtwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com"));
                startActivity(intent);
            }
        });
        imlinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com"));
                startActivity(intent);
            }
        });

    }

    private void sendReferral() {
        /*API call*/
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, "https://charpair.com/api/refferal_accepted", response -> {
            try {
                JSONObject jObject = new JSONObject(response);
                Log.d("success", jObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("api_key", Constants.API_KEY);
                params.put("client", Constants.DEVICE_TYPE);
                params.put("token", sharedPreferences.getString(Constants.TOKEN, ""));
                return params;
            }
        };
        queue.add(sr);
    }

    private boolean checkAndRequestPermissions() {
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarsePermision = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (coarsePermision != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[0]), 1);
            return false;
        }
        return true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().hasExtra("type")) {
            sharedPreferences.edit().putInt(Constants.TAB, 1).apply();
            switchTabs(0);
        }
    }

    public void switchTabs(int status) {
        try {
            sharedPreferences.edit().putInt(Constants.TAB, 0).apply();
            HomeFragment fragment = (HomeFragment) fragmentManager.findFragmentByTag(HomeFragment.class.getName());
            if (fragment != null) {
                fragment.switchTab(status);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void openDrawer() {
        textViewName.setText(sharedPreferences.getString(Constants.NAME, "Name"));
        textViewNumber.setText(sharedPreferences.getString(Constants.EMAIL, "abc@gmail.com"));
        if (!sharedPreferences.getString(Constants.IMAGE, "").isEmpty()) {
            Glide.with(this).load(sharedPreferences.getString(Constants.IMAGE, "https://toppng.com/uploads/preview/person-png-11553989513mzkt4ocbrv.png")).into(imageViewConactImage);
        } else {
            Glide.with(this).load("https://toppng.com/uploads/preview/person-png-11553989513mzkt4ocbrv.png").into(imageViewConactImage);
        }
        drawerLayout.openDrawer(Gravity.LEFT);
    }


    public void closeDrawer() {
        drawerLayout.closeDrawer(Gravity.LEFT);

    }

    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }
    }


    @Override
    public void onclick(int s) {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
        if (s == 1) {
            HomeFragment fragment = (HomeFragment) fragmentManager.findFragmentByTag(HomeFragment.class.getName());
            if (fragment != null) {
                fragment.switchTab(0);
            }
        } else if (s == 8) {
            final AlertDialog.Builder newBuilder = new AlertDialog.Builder(this);
            newBuilder.setMessage("Are you sure you want to Logout?");
            newBuilder.setPositiveButton("Yes", (dialog, which) -> {
                stopService(new Intent(this, NotificationService.class));

                sharedPreferences.edit().putBoolean(Constants.IS_LOGIN, false).apply();
                sharedPreferences.edit().clear().apply();
                Intent intent = new Intent(HomeActivity.this, AuthActivity.class);
                startActivity(intent);
                finish();

            });
            newBuilder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
            newBuilder.show();
        }
    }

}
