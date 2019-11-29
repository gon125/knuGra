package com.knucse.knugra.UI_package.std_info_input;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StdInfoInputViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StdInfoInputViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is StdInfoInput fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}