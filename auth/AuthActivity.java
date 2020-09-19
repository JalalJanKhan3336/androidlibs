package com.thesoftparrot.delivery.tcsapp.ui.commonui.activity;

import android.view.View;

import com.thesoftparrot.delivery.tcsapp.databinding.ActivityAuthBinding;
import com.thesoftparrot.delivery.tcsapp.extra.utils.KeyUtils;
import com.thesoftparrot.delivery.tcsapp.ui.base.activity.BaseActivity;

public class AuthActivity extends BaseActivity<ActivityAuthBinding> {

    @Override
    protected ActivityAuthBinding initBinding() {
        return ActivityAuthBinding.inflate(getLayoutInflater());
    }

    @Override
    protected View initRoot() {
        return mBinding.getRoot();
    }

    @Override
    protected void initRef() {
        findUserType(mPrefManager.retrieveData(KeyUtils.KEY_USER_TYPE, (String) null));
    }

    @Override
    protected void click() {

    }

    @Override
    protected void typeCustomer() {

    }

    @Override
    protected void typeStaff() {

    }

}
