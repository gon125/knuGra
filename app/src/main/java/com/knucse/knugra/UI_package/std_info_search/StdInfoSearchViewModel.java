package com.knucse.knugra.UI_package.std_info_search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StdInfoSearchViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StdInfoSearchViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is StdInfoSearch fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}