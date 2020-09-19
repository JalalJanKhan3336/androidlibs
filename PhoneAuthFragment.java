package com.thesoftparrot.delivery.tcsapp.ui.base.fragment;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public abstract class PhoneAuthFragment<VB> extends BaseFragment<VB> {

    protected abstract void onPhoneAuthSuccess(boolean isNewUser);
    protected abstract void onPhoneAuthFailed(String error);

    private String verificationId;

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mVerificationStateCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String verificId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(verificId, forceResendingToken);
            verificationId = verificId;
        }

        @Override
        public void onCodeAutoRetrievalTimeOut(@NonNull String code) {
            super.onCodeAutoRetrievalTimeOut(code);

            if(code != null || !TextUtils.isEmpty(code))
                verifyCode(code);
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
            signInWithCredential(credential);
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            onPhoneAuthFailed(e.getMessage());
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
