package com.thesoftparrot.delivery.tcsapp.ui.commonui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pakistan.jkutils.manager.SharedPrefStorageManager;
import com.pakistan.jkutils.utils.MoverUtils;
import com.thesoftparrot.delivery.tcsapp.R;
import com.thesoftparrot.delivery.tcsapp.TCSApp;
import com.thesoftparrot.delivery.tcsapp.databinding.FragmentVerificationSuccessBinding;
import com.thesoftparrot.delivery.tcsapp.extra.utils.KeyUtils;
import com.thesoftparrot.delivery.tcsapp.ui.customer.home.activity.CustomerHomeActivity;

public class VerificationSuccessFragment extends Fragment {

    private FragmentVerificationSuccessBinding mBinding;
    private SharedPrefStorageManager mPrefManager;

    public VerificationSuccessFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentVerificationSuccessBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initPref();
        click();
    }

    private void initPref() {
        mPrefManager = SharedPrefStorageManager.getInstance(TCSApp.getInstance().getAppContext(), getResources().getString(R.string.app_name));
    }

    private void click() {
        mBinding.gotoHomeBtn.setOnClickListener(view -> {
            MoverUtils.moveTo(requireContext(), findUserType(mPrefManager.retrieveData(KeyUtils.KEY_USER_TYPE, (String) null)));
            requireActivity().finishAffinity();
        });
    }

    private Class findUserType(String type){
        Class target = CustomerHomeActivity.class; // default is Customer's Home Screen

        if(type != null && TextUtils.isEmpty(type)){
            if(type.equalsIgnoreCase(KeyUtils.KEY_CUSTOMER))
                target = CustomerHomeActivity.class; // Customer's Home Screen
            if(type.equalsIgnoreCase(KeyUtils.KEY_STAFF))
                target = CustomerHomeActivity.class; // Staff's Home Screen
        }

        return target;
    }

}
