package com.thesoftparrot.storageapp.visapp.ui.base.fragment;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken;
import com.thesoftparrot.storageapp.visapp.extras.utils.KeyUtils;

import java.util.concurrent.TimeUnit;

public abstract class PhoneAuthFragment<VB> extends BaseFragment<VB> {

    private static final String TAG = "PhoneAuthFragment";

    protected abstract void onPhoneAuthSuccess(boolean isNewUser);
    protected abstract void onPhoneAuthFailed(String error);

    private String verificationId;

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mVerificationStateCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String verificId, @NonNull ForceResendingToken forceResendingToken) {
            super.onCodeSent(verificId, forceResendingToken);
            verificationId = verificId;
            Log.d(TAG, "_onCodeSent_VerificationId: "+verificationId);
        }

        @Override
        public void onCodeAutoRetrievalTimeOut(@NonNull String code) {
            super.onCodeAutoRetrievalTimeOut(code);
            Log.d(TAG, "_onCodeAutoRetrievalTimeOut_SMS_Code: "+code);
            verifyCode(code);
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
            String code = credential.getSmsCode();
            Log.d(TAG, "_onVerificationCompleted_SMS_Code: "+code);
            verifyCode(code);
            loading(true,false, KeyUtils.PHONE_VERIFICATION_MSG);
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            String error = e.getLocalizedMessage();
            Log.e(TAG, "_onVerificationFailed_Error: "+error);
            onPhoneAuthFailed(error);
        }
    };

    protected void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth
                .signInWithCredential(credential)
                .addOnSuccessListener(authResult -> onPhoneAuthSuccess(authResult.getAdditionalUserInfo().isNewUser()))
                .addOnFailureListener(e -> onPhoneAuthFailed(e.getMessage()));

    }

    public PhoneAuthFragment() {}

    protected void sendVerificationCode(String phoneNumber){
        PhoneAuthProvider.getInstance()
                .verifyPhoneNumber(
                        phoneNumber,
                        60,
                        TimeUnit.SECONDS,
                        TaskExecutors.MAIN_THREAD,
                        mVerificationStateCallback);
    }

}
