package com.thesoftparrot.storageapp.visapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentSnapshot;
import com.thesoftparrot.storageapp.visapp.extras.callback.FileProgressStatusCallback;
import com.thesoftparrot.storageapp.visapp.extras.callback.ProgressStatusCallback;
import com.thesoftparrot.storageapp.visapp.repository.callback.FetchSingleCallback;
import com.thesoftparrot.storageapp.visapp.repository.callback.UpdateCallback;
import com.thesoftparrot.storageapp.visapp.repository.remote.DatabaseOperation;

public abstract class BaseViewModel<T> extends ViewModel {
    private static final String TAG = "BaseViewModel";

    private DatabaseOperation mDatabase;
    private ProgressStatusCallback mProgressStatusCallback;
    private FileProgressStatusCallback mFileProgressStatusCallback;

    public BaseViewModel() {
        init(null, null);
    }

    public BaseViewModel(ProgressStatusCallback statusCallback) {
        init(statusCallback, null);
    }

    public BaseViewModel(FileProgressStatusCallback callback) {
        init(null,callback);
    }

    public BaseViewModel(ProgressStatusCallback callback1, FileProgressStatusCallback callback2) {
        init(callback1,callback2);
    }

    private void init(ProgressStatusCallback progressStatusCallback, FileProgressStatusCallback fileProgressStatusCallback) {
        this.mDatabase = DatabaseOperation.getInstance();
        this.mProgressStatusCallback = progressStatusCallback;
        this.mFileProgressStatusCallback = fileProgressStatusCallback;
    }

    protected void update(String collection, String document, T value, boolean notifyCallback){
        mDatabase.update(collection, document, value, new UpdateCallback() {
            @Override
            public void onUpdateSuccess(String msg) {
                Log.d(TAG, "_onUpdateSuccess_msg: "+msg);
                if(notifyCallback)
                    onProgressStatusChanged(false,msg);
            }

            @Override
            public void onUpdateFailed(String error) {
                Log.e(TAG, "_onUpdateFailed: "+error);
                onProgressStatusChanged(true,error);
            }
        });
    }

    public LiveData<T> fetchSingle(String collection, String document, Class<T> type, boolean notifyCallback){
        MutableLiveData<T> liveData = new MutableLiveData<T>();

        mDatabase.fetch(collection, document, new FetchSingleCallback() {
            @Override
            public void onFetchSingleSuccess(DocumentSnapshot snapshot, String msg) {
                Log.d(TAG, "_onFetchSingleSuccess_Snapshot: "+snapshot);
                T value = snapshot.toObject(type);
                liveData.setValue(value);
                if(notifyCallback)
                    onProgressStatusChanged(false, msg);
            }

            @Override
            public void onFetchSingleFailed(String error) {
                if(notifyCallback)
                    onProgressStatusChanged(true, error);
            }
        });

        return liveData;
    }

    protected void onProgressStatusChanged(boolean isError, String msg){
        if(mProgressStatusCallback != null){
            if(isError)
                mProgressStatusCallback.onProgressStateFailure(msg);
            else
                mProgressStatusCallback.onProgressStateSuccess(msg);
        }
    }

    protected void onFileProgressStatusChanged(boolean isError, String cloud, String msg){
        if(mFileProgressStatusCallback != null){
            if(isError)
                mFileProgressStatusCallback.onFileProgressStatusFailure(msg);
            else
                mFileProgressStatusCallback.onFileProgressStatusSuccess(cloud, msg);
        }
    }

}
