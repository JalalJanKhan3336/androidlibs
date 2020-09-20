package com.thesoftparrot.storageapp.visapp.extras.callback;

public interface FileProgressStatusCallback {
    void onFileProgressStatusSuccess(String cloud, String msg);
    void onFileProgressStatusFailure(String error);
}
