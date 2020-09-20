package com.thesoftparrot.storageapp.visapp.ui.base.activity;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.pakistan.jkutils.manager.SharedPrefStorageManager;
import com.thesoftparrot.storageapp.visapp.R;
import com.thesoftparrot.storageapp.visapp.VisApp;

public abstract class BaseActivity<VB> extends AppCompatActivity {

    protected VB mBinding;
    protected NavController mNavController;
    protected SharedPrefStorageManager mPrefManager;

    protected abstract VB initBinding();
    protected abstract View initRoot();
    protected abstract void initRef();
    protected abstract void click();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = initBinding();
        setContentView(initRoot());

        initPref();
        initRef();
        click();
    }

    private void initPref() {
        mPrefManager = SharedPrefStorageManager.getInstance(VisApp.getInstance().getAppContext(),getResources().getString(R.string.app_name));
    }

    protected void initNavGraph(int host){
        mNavController = Navigation.findNavController(this, host);
    }

    protected boolean isLoggedIn(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return (auth.getCurrentUser() != null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
