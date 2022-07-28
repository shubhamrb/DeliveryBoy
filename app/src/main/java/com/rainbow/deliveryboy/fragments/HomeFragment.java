package com.rainbow.deliveryboy.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.rainbow.deliveryboy.R;
import com.rainbow.deliveryboy.activity.HomeActivity;
import com.rainbow.deliveryboy.adapter.SectionPagerHomeAdapter;
import com.rainbow.deliveryboy.base.BaseFragment;
import com.rainbow.deliveryboy.presenter.HomePresenter;
import com.rainbow.deliveryboy.utils.Constants;
import com.rainbow.deliveryboy.views.HomeView;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment<HomePresenter, HomeView> implements HomeView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imageViewHome)
    ImageView imageViewHome;
    @BindView(R.id.imageViewNotification)
    ImageView imageViewNotification;

    private SharedPreferences sharedPreferences;

    private String strToken = "";

    @BindView(R.id.viewpagermyride)
    ViewPager viewpagermyride;
    @BindView(R.id.text_upcoming)
    AppCompatTextView textViewCar;
    @BindView(R.id.layoutupcoming)
    LinearLayout layoutupcoming;
    @BindView(R.id.text_completed)
    AppCompatTextView textCompleted;
    @BindView(R.id.layoutcompleted)
    LinearLayout layoutcompleted;

    @Override
    protected int createLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void setPresenter() {
        presenter = new HomePresenter();
    }

    @Override
    protected HomeView createView() {
        return this;
    }


    @Override
    protected void bindData() {
        sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        strToken = sharedPreferences.getString(Constants.TOKEN, "");

        SectionPagerHomeAdapter sectionPagerAdapter =
                new SectionPagerHomeAdapter(getChildFragmentManager(), 2);
        viewpagermyride.setAdapter(sectionPagerAdapter);
        viewpagermyride.setOffscreenPageLimit(1);
        viewpagermyride.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    layoutupcoming.setBackground(getResources().getDrawable(R.drawable.rounded_bg_blue));
                    layoutcompleted.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    textViewCar.setTextColor(getResources().getColor(R.color.colorWhite));
                    textCompleted.setTextColor(getResources().getColor(R.color.colorBlack));
                } else {
                    layoutcompleted.setBackground(getResources().getDrawable(R.drawable.rounded_bg_blue));
                    layoutupcoming.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    textViewCar.setTextColor(getResources().getColor(R.color.colorBlack));
                    textCompleted.setTextColor(getResources().getColor(R.color.colorWhite));
                }
                viewpagermyride.setCurrentItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick({R.id.imageViewHome, R.id.imageViewNotification,R.id.layoutupcoming,R.id.layoutcompleted})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewHome:
                ((HomeActivity) getActivity()).openDrawer();
                break;
            case R.id.imageViewNotification:
                presenter.openNotification();
                break;
            case R.id.layoutupcoming:
                layoutupcoming.setBackground(getResources().getDrawable(R.drawable.rounded_bg_blue));
                layoutcompleted.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                textViewCar.setTextColor(getResources().getColor(R.color.colorWhite));
                textCompleted.setTextColor(getResources().getColor(R.color.colorBlack));
                viewpagermyride.setCurrentItem(0);
                break;
            case R.id.layoutcompleted:
                layoutcompleted.setBackground(getResources().getDrawable(R.drawable.rounded_bg_blue));
                layoutupcoming.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                textViewCar.setTextColor(getResources().getColor(R.color.colorBlack));
                textCompleted.setTextColor(getResources().getColor(R.color.colorWhite));
                viewpagermyride.setCurrentItem(1);
                break;
        }
    }
}
