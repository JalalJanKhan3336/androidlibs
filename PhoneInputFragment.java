package com.thesoftparrot.storageapp.visapp.ui.auth.fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pakistan.jkutils.utils.FieldUtils;
import com.thesoftparrot.storageapp.visapp.R;
import com.thesoftparrot.storageapp.visapp.databinding.FragmentPhoneInputBinding;
import com.thesoftparrot.storageapp.visapp.extras.utils.KeyUtils;
import com.thesoftparrot.storageapp.visapp.ui.base.fragment.BaseFragment;

public class PhoneInputFragment extends BaseFragment<FragmentPhoneInputBinding> {

    private static final String TAG = "PhoneInputFragment";

    public PhoneInputFragment() {
        // Required empty public constructor
    }

    @Override
    protected FragmentPhoneInputBinding initBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentPhoneInputBinding.inflate(inflater, container, false);
    }

    @Override
    protected View initRoot() {
        return mBinding.getRoot();
    }

    @Override
    protected void initRef() {
        initNavGraph(R.id.host_fragment);
        mBinding.phoneInc.ccp.registerCarrierNumberEditText(mBinding.phoneInc.phoneInputEt);
    }

    @Override
    protected void click() {
        mBinding.phoneInc.ccp.setPhoneNumberValidityChangeListener(isValidNumber -> {
            int icon = 0;

            if(!FieldUtils.isFieldEmpty(mBinding.phoneInc.phoneInputEt)){
                if(!isValidNumber)
                    icon = R.drawable.ic_close;
                else
                    icon = R.drawable.ic_done;
            }

            mBinding.phoneInc.phoneInputEt.setCompoundDrawablesWithIntrinsicBounds(0, 0, icon, 0);

            Log.d(TAG, "_onClick_Formatted_Number: "+mBinding.phoneInc.ccp.getFormattedFullNumber());
            Log.d(TAG, "_onClick_UnFormatted_Number: "+mBinding.phoneInc.ccp.getFullNumber());
            Log.d(TAG, "_onClick_UnFormatted_Number_With_Code: "+mBinding.phoneInc.ccp.getFullNumberWithPlus());
        });

        mBinding.phoneInc.continueFab.setOnClickListener(view -> {
            String phone = mBinding.phoneInc.ccp.getFullNumberWithPlus();
            mNavController.navigate(R.id.action_phoneInputFragment_to_verifyCodeFragment, initBundle(KeyUtils.KEY_PHONE, phone));
        });
    }

}