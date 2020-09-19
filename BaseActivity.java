package com.thesoftparrot.delivery.tcsapp.ui.base.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.pakistan.jkutils.manager.SharedPrefStorageManager;
import com.thesoftparrot.delivery.tcsapp.R;
import com.thesoftparrot.delivery.tcsapp.TCSApp;
import com.thesoftparrot.delivery.tcsapp.extra.utils.KeyUtils;

public abstract class BaseActivity<VB> extends AppCompatActivity {

    protected VB mBinding;
    protected NavController mNavController;
    protected SharedPrefStorageManager mPrefManager;

    protected abstract VB initBinding();
    protected abstract View initRoot();
    protected abstract void initRef();
    protected abstract void click();
    protected abstract void typeCustomer();
    protected abstract void typeStaff();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = initBinding();
        setContentView(initRoot());

        initNavGraph();
        initPref();
        initRef();
        click();
    }

    private void initPref() {
        mPrefManager = SharedPrefStorageManager.getInstance(TCSApp.getInstance().getAppContext(),getResources().getString(R.string.app_name));
    }

    private void initNavGraph(){
        mNavController = Navigation.findNavController(this, R.id.host_fragment);
    }

    protected boolean isLoggedIn(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return (auth.getCurrentUser() != null);
    }

    protected void findUserType(String type){
        if(type != null && TextUtils.isEmpty(type)){
            if(type.equalsIgnoreCase(KeyUtils.KEY_CUSTOMER))
                typeCustomer();
            if(type.equalsIgnoreCase(KeyUtils.KEY_STAFF))
                typeStaff();
        }else
            typeCustomer();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
