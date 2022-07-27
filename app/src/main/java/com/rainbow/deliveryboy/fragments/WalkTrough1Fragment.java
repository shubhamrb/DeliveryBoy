package com.rainbow.deliveryboy.fragments;


import androidx.appcompat.widget.AppCompatImageView;

import com.rainbow.deliveryboy.R;
import com.rainbow.deliveryboy.base.BaseFragment;
import com.rainbow.deliveryboy.presenter.WalkTrough1Presenter;
import com.rainbow.deliveryboy.views.WalkTrough1View;

import butterknife.BindView;

public class WalkTrough1Fragment extends BaseFragment<WalkTrough1Presenter, WalkTrough1View>
        implements WalkTrough1View {

    @BindView(R.id.imageViewCall2)
    AppCompatImageView imageViewCall2;

    @Override
    protected int createLayout() {
        return R.layout.fragment_walktrough1;
    }

    @Override
    protected void setPresenter() {
        presenter = new WalkTrough1Presenter();
    }

    @Override
    protected WalkTrough1View createView() {
        return this;
    }

    @Override
    protected void bindData() {}
}
