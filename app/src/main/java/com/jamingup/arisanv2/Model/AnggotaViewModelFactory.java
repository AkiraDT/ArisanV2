package com.jamingup.arisanv2.Model;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class AnggotaViewModelFactory implements ViewModelProvider.Factory {
    private Application mAplication;
    private String mParam;

    public AnggotaViewModelFactory(Application mAplication, String mParam) {
        this.mAplication = mAplication;
        this.mParam = mParam;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AnggotaViewModel(mAplication, mParam);
    }
}
