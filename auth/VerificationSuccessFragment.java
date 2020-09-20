package com.thesoftparrot.storageapp.visapp.ui.auth.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pakistan.jkutils.manager.SharedPrefStorageManager;
import com.pakistan.jkutils.utils.MoverUtils;
import com.thesoftparrot.storageapp.visapp.R;
import com.thesoftparrot.storageapp.visapp.VisApp;
import com.thesoftparrot.storageapp.visapp.databinding.FragmentVerificationSuccessBinding;
import com.thesoftparrot.storageapp.visapp.ui.home.activity.MainActivity;


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
        mPrefManager = SharedPrefStorageManager.getInstance(VisApp.getInstance().getAppContext(), getResources().getString(R.string.app_name));
    }

    private void click() {
        mBinding.gotoHomeBtn.setOnClickListener(view -> {
            MoverUtils.moveTo(requireContext(), MainActivity.class);
            requireActivity().finishAffinity();
        });
    }

}
