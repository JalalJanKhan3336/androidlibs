package com.thesoftparrot.storageapp.visapp.ui.base.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pakistan.jkutils.manager.SharedPrefStorageManager;
import com.thesoftparrot.storageapp.visapp.R;
import com.thesoftparrot.storageapp.visapp.VisApp;

public abstract class BaseFragment<VB> extends Fragment {

    protected VB mBinding;
    protected NavController mNavController;
    protected ProgressDialog mProgressDialog;
    protected String mCurrentUserId;
    protected SharedPrefStorageManager mPrefManager;

    protected abstract VB initBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);
    protected abstract View initRoot();
    protected abstract void initRef();
    protected abstract void click();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = initBinding(inflater, container);
        return initRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initPref();
        findUid();
        initRef();
        click();
    }

    private void initPref() {
        mPrefManager = SharedPrefStorageManager.getInstance(VisApp.getInstance().getAppContext(), getResources().getString(R.string.app_name));
    }

    // Check either user is logged in
    protected boolean isLoggedIn(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return (auth.getCurrentUser() != null);
    }

    // Finds and returns current user's Firebase uid
    protected String findUid() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if(user != null){
            mCurrentUserId = user.getUid();
            return mCurrentUserId;
        }

        return null;
    }

    // Finds and returns current user's Firebase uid
    protected String findPhoneNumber() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if(user != null)
            return user.getPhoneNumber();

        return null;
    }

    protected void loading(boolean showIt, boolean isCancelable, String msg){
        if(mProgressDialog == null)
            mProgressDialog = new ProgressDialog(requireContext());

        mProgressDialog.setMessage(msg);
        mProgressDialog.setCancelable(isCancelable);

        if(showIt)
            mProgressDialog.show();
        else
            mProgressDialog.dismiss();
    }

    protected void initNavGraph(int host){
        mNavController = Navigation.findNavController(requireActivity(), host);
    }

    protected void snacky(View view, String msg) {
        Snackbar.make(view, msg,Snackbar.LENGTH_LONG).show();
    }

    protected Bundle initBundle(String key, String item) {
        Bundle bundle = new Bundle();
        bundle.putString(key,item);
        return bundle;
    }
}
