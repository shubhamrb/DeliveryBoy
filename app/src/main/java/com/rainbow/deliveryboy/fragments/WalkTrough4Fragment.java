package com.rainbow.deliveryboy.fragments;


import com.rainbow.deliveryboy.R;
import com.rainbow.deliveryboy.base.BaseFragment;
import com.rainbow.deliveryboy.presenter.WalkTrough1Presenter;
import com.rainbow.deliveryboy.views.WalkTrough1View;

public class WalkTrough4Fragment extends BaseFragment<WalkTrough1Presenter, WalkTrough1View>
        implements WalkTrough1View {

    @Override
    protected int createLayout() {
        return R.layout.fragment_walktrough4;
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
