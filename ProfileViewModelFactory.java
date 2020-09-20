package com.thesoftparrot.storageapp.visapp.viewmodel.factory;

import com.thesoftparrot.storageapp.visapp.extras.callback.FileProgressStatusCallback;
import com.thesoftparrot.storageapp.visapp.extras.callback.ProgressStatusCallback;
import com.thesoftparrot.storageapp.visapp.viewmodel.ProfileViewModel;

public class ProfileViewModelFactory extends BaseViewModelFactory<ProfileViewModel> {

    public ProfileViewModelFactory() {
        super();
    }

    public ProfileViewModelFactory(ProgressStatusCallback mProgressStatusCallback) {
        super(mProgressStatusCallback);
    }

    public ProfileViewModelFactory(FileProgressStatusCallback mFileProgressStatusCallback) {
        super(mFileProgressStatusCallback);
    }

    public ProfileViewModelFactory(ProgressStatusCallback mProgressStatusCallback, FileProgressStatusCallback mFileProgressStatusCallback) {
        super(mProgressStatusCallback, mFileProgressStatusCallback);
    }

    @Override
    protected ProfileViewModel referViewModel(ProgressStatusCallback progressStatusCallback, FileProgressStatusCallback fileProgressStatusCallback) {
        return new ProfileViewModel(progressStatusCallback, fileProgressStatusCallback);
    }
}
