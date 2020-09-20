package com.thesoftparrot.storageapp.visapp.ui.auth.activity;

import android.view.View;

import com.thesoftparrot.storageapp.visapp.R;
import com.thesoftparrot.storageapp.visapp.databinding.ActivityAuthBinding;
import com.thesoftparrot.storageapp.visapp.ui.base.activity.BaseActivity;


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
        initNavGraph(R.id.host_fragment);
    }

    @Override
    protected void click() {

    }

}
