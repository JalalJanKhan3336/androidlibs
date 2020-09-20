package com.thesoftparrot.storageapp.visapp.repository.remote;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.thesoftparrot.storageapp.visapp.extras.utils.KeyUtils;
import com.thesoftparrot.storageapp.visapp.repository.callback.FetchSingleCallback;
import com.thesoftparrot.storageapp.visapp.repository.callback.UpdateCallback;

public class DatabaseOperation {
    private static final String TAG = "DatabaseOperation";

    private static DatabaseOperation instance;

    public synchronized static DatabaseOperation getInstance() {
        if(instance == null)
            instance = new DatabaseOperation();
        return instance;
    }

    private FirebaseFirestore mRootRef;
    private StorageReference mRootStorageRef;

    private DatabaseOperation() {
        mRootRef = FirebaseFirestore.getInstance();
        mRootStorageRef = FirebaseStorage.getInstance().getReference();
    }

    public void update(String collection, String document, Object value, UpdateCallback listener){
        if(listener == null)
            throw new NullPointerException("UpdateCallback interface can not be NULL");

        mRootRef
                .collection(collection)
                .document(document)
                .set(value)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "_onUpdate_Success: ");
                    listener.onUpdateSuccess(KeyUtils.UPDATE_SUCCESS_MSG);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "_onUpdate_Failure: "+e.getMessage());
                    listener.onUpdateFailed(e.getMessage());
                });
    }

    public void fetch(String collection, String document, FetchSingleCallback listener){
        if(listener == null)
            throw new NullPointerException("FetchSingleCallback interface can not be NULL");

        mRootRef
                .collection(collection)
                .document(document)
                .get()
                .addOnSuccessListener(snapshot -> {
                    Log.d(TAG, "_onFetch_Success: "+snapshot);
                    listener.onFetchSingleSuccess(snapshot, KeyUtils.FETCH_SUCCESS_MSG);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "_onFetch_Failure: "+e.getMessage());
                    listener.onFetchSingleFailed(e.getMessage());
                });
    }
}
