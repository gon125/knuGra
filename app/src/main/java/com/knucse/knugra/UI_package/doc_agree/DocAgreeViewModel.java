package com.knucse.knugra.UI_package.doc_agree;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DocAgreeViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public DocAgreeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is DocAgree fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
