package com.app.overboxsample.network.interfaces;

import android.support.annotation.NonNull;

import com.app.overboxsample.network.request.Request;
import com.app.overboxsample.network.response.NetworkResponseListener;
import com.app.overboxsample.network.response.Response;

public interface HttpClient<T> {
    Response<T> excecute(@NonNull Request request);

    void excecuteAsync(@NonNull Request request, NetworkResponseListener<T> listener);

    void cancel(Object tag);
}
