package com.knucse.knugra.UI_package.qa;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QAViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public QAViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is QA fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}