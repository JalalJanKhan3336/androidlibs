package com.thesoftparrot.storageapp.visapp.repository.callback;

import com.google.firebase.firestore.DocumentSnapshot;

public interface FetchSingleCallback {
    void onFetchSingleSuccess(DocumentSnapshot snapshot, String msg);
    void onFetchSingleFailed(String error);
}
