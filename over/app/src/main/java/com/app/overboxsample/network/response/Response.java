package com.app.overboxsample.network.response;

import java.util.Map;

/**
 * Created by rohan on 8/20/15.
 */
public interface Response<T> {

    int getStatusCode();

    Map<String , String> getHeaders();

    T getResponse();
}
