package com.thesoftparrot.delivery.tcsapp.ui.commonui.fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.pakistan.jkutils.utils.ContextUtils;
import com.pakistan.jkutils.utils.FieldUtils;
import com.thesoftparrot.delivery.tcsapp.R;
import com.thesoftparrot.delivery.tcsapp.databinding.FragmentVerifyCodeBinding;
import com.thesoftparrot.delivery.tcsapp.extra.utils.KeyUtils;
import com.thesoftparrot.delivery.tcsapp.repository.model.Profile;
import com.thesoftparrot.delivery.tcsapp.ui.base.fragment.PhoneAuthFragment;
import com.thesoftparrot.delivery.tcsapp.viewmodel.customer.ProfileViewModel;

public class VerifyCodeFragment extends PhoneAuthFragment<FragmentVerifyCodeBinding> {

    private static final String TAG = "VerifyCodeFragment";

    public VerifyCodeFragment() {
        // Required empty public constructor
    }

    @Override
    protected FragmentVerifyCodeBinding initBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentVerifyCodeBinding.inflate(inflater, container, false);
    }

    @Override
    protected View initRoot() {
        return mBinding.getRoot();
    }

    @Override
    protected void initRef() {

        if(getArguments() == null){
            ContextUtils.toast(requireContext(),"No Phone Number received... Try again");
            mNavController.navigateUp();
            return;
        }

        String phoneNumber = getArguments().getString(KeyUtils.KEY_PHONE);

        if(phoneNumber == null || TextUtils.isEmpty(phoneNumber)){
            ContextUtils.toast(requireContext(),"No Phone Number entered... Try again");
            mNavController.navigateUp();
            return;
        }

        mBinding.phoneNumberTv.setText(phoneNumber);
        loading(true,false, KeyUtils.PHONE_VERIFICATION_MSG);
        sendVerificationCode(phoneNumber);
    }

    @Override
    protected void click() {
        mBinding.backIv.setOnClickListener(view -> {
            mNavController.navigateUp();
        });

        mBinding.verifyBtn.setOnClickListener(view -> {
            if(!FieldUtils.isFieldEmpty(mBinding.codeEt)){
                String code = mBinding.codeEt.getText().toString().trim();
                loading(true,false, KeyUtils.PHONE_VERIFICATION_MSG);
                verifyCode(code);
            }
        });

    }

    @Override
    protected void typeCustomer() {

    }

    @Override
    protected void typeStaff() {

    }

    @Override
    protected void onPhoneAuthSuccess(boolean isNewUser) {
        loading(false,false, null);
        if(isNewUser){
            String userId = findUid();
            String phoneNumber = findPhoneNumber();
            Profile profile = new Profile(userId, phoneNumber);
            ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
            profileViewModel.updateInfo(profile);
            mNavController.navigate(R.id.action_verifyCodeFragment_to_verificationSuccessFragment);
        }
    }

    @Override
    protected void onPhoneAuthFailed(String error) {
        loading(false,false, null);
        snacky(mBinding.getRoot(), error);
    }

}
