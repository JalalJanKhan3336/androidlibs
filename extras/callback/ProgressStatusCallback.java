package com.thesoftparrot.storageapp.visapp.extras.callback;

public interface ProgressStatusCallback {
    void onProgressStateSuccess(String msg);
    void onProgressStateFailure(String error);
}
