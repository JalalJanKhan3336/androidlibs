package com.thesoftparrot.delivery.tcsapp.ui.base.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.thesoftparrot.delivery.tcsapp.R;
import com.thesoftparrot.delivery.tcsapp.TCSApp;
import com.thesoftparrot.delivery.tcsapp.extra.utils.KeyUtils;

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
    protected abstract void typeCustomer();
    protected abstract void typeStaff();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = initBinding(inflater, container);
        return initRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initNavGraph();
        initPref();
        findUid();
        initRef();
        click();
    }

    private void initPref() {
        mPrefManager = SharedPrefStorageManager.getInstance(TCSApp.getInstance().getAppContext(), getResources().getString(R.string.app_name));
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

    private void initNavGraph(){
        mNavController = Navigation.findNavController(requireActivity(), R.id.host_fragment);
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
