package com.pucmm.loginandmainpage.ui.listos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListOSViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ListOSViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the ListOS fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}