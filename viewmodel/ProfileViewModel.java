package com.thesoftparrot.storageapp.visapp.viewmodel;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.thesoftparrot.storageapp.visapp.extras.callback.FileProgressStatusCallback;
import com.thesoftparrot.storageapp.visapp.extras.callback.ProgressStatusCallback;
import com.thesoftparrot.storageapp.visapp.extras.utils.KeyUtils;
import com.thesoftparrot.storageapp.visapp.repository.model.Profile;

public class ProfileViewModel extends BaseViewModel<Profile> {
    private static final String TAG = "ProfileViewModel";

    private MutableLiveData<Profile> mCurrentUserProfileLiveData;
    private MutableLiveData<Profile> mSingleUserProfileLiveData;

    public ProfileViewModel() {
        super();
    }

    public ProfileViewModel(ProgressStatusCallback callback1, FileProgressStatusCallback callback2) {
        super(callback1, callback2);
    }

    public LiveData<Profile> getCurrentUserProfileLiveData(String userId) {
        if(mCurrentUserProfileLiveData == null) {
            mCurrentUserProfileLiveData = new MutableLiveData<>();
            fetchProfileInfo(userId, true);
        }

        return mCurrentUserProfileLiveData;
    }

    public LiveData<Profile> getSingleUserProfileLiveData(String userId) {
        if(mSingleUserProfileLiveData == null) {
            mSingleUserProfileLiveData = new MutableLiveData<>();
            fetchProfileInfo(userId, false);
        }

        return mSingleUserProfileLiveData;
    }

    public void updateProfileInfo(Profile profile, boolean notify){
        update(KeyUtils.COLLECTION_PROFILE, profile.getUserId(), profile, notify);
    }

    public void fetchProfileInfo(String userId, boolean isCurrentUser) {
        LiveData<Profile> liveData = fetchSingle(KeyUtils.COLLECTION_PROFILE, userId, Profile.class, isCurrentUser);
        liveData.observeForever(profile -> {
            if(isCurrentUser)
                mCurrentUserProfileLiveData.setValue(profile);
            else
                mSingleUserProfileLiveData.setValue(profile);
        });
    }

}
