package com.knucse.knugra.UI_package.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("메뉴를 선택해주세요.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}