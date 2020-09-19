package com.thesoftparrot.delivery.tcsapp.ui.splash;

import android.view.View;

import com.pakistan.jkutils.utils.MoverUtils;
import com.thesoftparrot.delivery.tcsapp.databinding.ActivitySplashBinding;
import com.thesoftparrot.delivery.tcsapp.extra.utils.KeyUtils;
import com.thesoftparrot.delivery.tcsapp.ui.base.activity.BaseActivity;
import com.thesoftparrot.delivery.tcsapp.ui.commonui.activity.AuthActivity;
import com.thesoftparrot.delivery.tcsapp.ui.customer.home.activity.CustomerHomeActivity;
import com.thesoftparrot.delivery.tcsapp.ui.staff.home.activity.StaffHomeActivity;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    @Override
    protected ActivitySplashBinding initBinding() {
        return ActivitySplashBinding.inflate(getLayoutInflater());
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
        if(isLoggedIn())
            move(CustomerHomeActivity.class);
        else
            move(AuthActivity.class);
    }

    @Override
    protected void typeStaff() {
        if(isLoggedIn())
            move(StaffHomeActivity.class);
        else
            move(AuthActivity.class);
    }

    // Move to specified Activity
    private void move(Class target) {
        MoverUtils.moveTo(SplashActivity.this, target);
        finish();
    }
}
