package com.knucse.knugra.UI_package.career_success;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CareerSuccessViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CareerSuccessViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nThis is CareerSuccess fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
