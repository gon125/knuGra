package com.knucse.knugra.UI_package.g_info_search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GInfoSearchViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GInfoSearchViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is GInfoSearch fragment");
        // add
    }

    public LiveData<String> getText() {
        return mText;
    }
}