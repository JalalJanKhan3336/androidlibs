package com.thesoftparrot.storageapp.visapp.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.thesoftparrot.storageapp.visapp.extras.callback.FileProgressStatusCallback;
import com.thesoftparrot.storageapp.visapp.extras.callback.ProgressStatusCallback;

public abstract class BaseViewModelFactory<VM> extends ViewModelProvider.NewInstanceFactory {

    private ProgressStatusCallback mProgressStatusCallback;
    private FileProgressStatusCallback mFileProgressStatusCallback;

    protected abstract VM referViewModel(ProgressStatusCallback progressStatusCallback, FileProgressStatusCallback fileProgressStatusCallback);

    public BaseViewModelFactory() {
        init(null, null);
    }

    public BaseViewModelFactory(ProgressStatusCallback mProgressStatusCallback) {
        init(mProgressStatusCallback, null);
    }

    public BaseViewModelFactory(FileProgressStatusCallback mFileProgressStatusCallback) {
        init(null, mFileProgressStatusCallback);
    }

    public BaseViewModelFactory(ProgressStatusCallback mProgressStatusCallback, FileProgressStatusCallback mFileProgressStatusCallback) {
        init(mProgressStatusCallback, mFileProgressStatusCallback);
    }

    private void init(ProgressStatusCallback mProgressStatusCallback, FileProgressStatusCallback mFileProgressStatusCallback) {
        this.mProgressStatusCallback = mProgressStatusCallback;
        this.mFileProgressStatusCallback = mFileProgressStatusCallback;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) referViewModel(mProgressStatusCallback, mFileProgressStatusCallback);
    }

}