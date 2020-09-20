package com.thesoftparrot.storageapp.visapp.ui.auth.fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.pakistan.jkutils.utils.ContextUtils;
import com.pakistan.jkutils.utils.FieldUtils;
import com.pakistan.jkutils.utils.MoverUtils;
import com.thesoftparrot.storageapp.visapp.R;
import com.thesoftparrot.storageapp.visapp.databinding.FragmentVerifyCodeBinding;
import com.thesoftparrot.storageapp.visapp.extras.callback.ProgressStatusCallback;
import com.thesoftparrot.storageapp.visapp.extras.utils.KeyUtils;
import com.thesoftparrot.storageapp.visapp.repository.model.Profile;
import com.thesoftparrot.storageapp.visapp.ui.base.fragment.PhoneAuthFragment;
import com.thesoftparrot.storageapp.visapp.ui.home.activity.MainActivity;
import com.thesoftparrot.storageapp.visapp.viewmodel.ProfileViewModel;
import com.thesoftparrot.storageapp.visapp.viewmodel.factory.ProfileViewModelFactory;

public class VerifyCodeFragment extends PhoneAuthFragment<FragmentVerifyCodeBinding> implements ProgressStatusCallback {

    private static final String TAG = "VerifyCodeFragment";

    private ProfileViewModel mProfileViewModel;

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
        initNavGraph(R.id.host_fragment);

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

        mProfileViewModel = new ViewModelProvider(this, new ProfileViewModelFactory(this, null)).get(ProfileViewModel.class);
        mBinding.phoneNumberTv.setText(phoneNumber);
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
                loading(true,false, KeyUtils.AUTH_LOADING_MSG);
                verifyCode(code);
            }
        });

    }

    @Override
    protected void onPhoneAuthSuccess(boolean isNewUser) {
        if(isNewUser){
            String userId = findUid();
            String phoneNumber = findPhoneNumber();
            Profile profile = new Profile(userId, phoneNumber);
            mProfileViewModel.updateProfileInfo(profile, true);
        }else {
            MoverUtils.moveTo(requireContext(), MainActivity.class);
            requireActivity().finish();
        }
    }

    @Override
    protected void onPhoneAuthFailed(String error) {
        Log.e(TAG, "_onPhoneAuthFailed: "+error);
        loading(false,false, null);
        snacky(mBinding.getRoot(), error);
    }

    @Override
    public void onProgressStateSuccess(String msg) {
        loading(false,false, null);
        mNavController.navigate(R.id.action_verifyCodeFragment_to_verificationSuccessFragment);
    }

    @Override
    public void onProgressStateFailure(String error) {
        loading(false,false, null);

        snacky(mBinding.backIv, error);
        MoverUtils.moveTo(requireContext(), MainActivity.class);
        requireActivity().finish();
    }
}
