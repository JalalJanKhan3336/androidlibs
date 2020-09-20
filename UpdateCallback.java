package com.thesoftparrot.storageapp.visapp.repository.callback;

public interface UpdateCallback {
    void onUpdateSuccess(String msg);
    void onUpdateFailed(String error);
}
