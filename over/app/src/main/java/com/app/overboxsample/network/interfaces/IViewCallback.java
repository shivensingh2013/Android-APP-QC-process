package com.app.overboxsample.network.interfaces;

import android.support.annotation.Nullable;

public interface IViewCallback<T> {
    void onSuccess(T dataObject);
    void onError(String errorMessage, int errorCode, @Nullable T dataObject);
}
