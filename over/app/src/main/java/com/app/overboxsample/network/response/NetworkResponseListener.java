package com.app.overboxsample.network.response;

/**
 * Created by rohan on 8/27/15.
 */
public interface NetworkResponseListener<T> {
    void onNetworkResponse(Response<T> response);
}
