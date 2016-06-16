package com.app.overboxsample.network.request;

/**
 * Created by rohan on 8/20/15.
 */
import com.app.overboxsample.network.utils.HttpMethod;
import com.app.overboxsample.network.utils.RequestExecutionMode;

import java.util.Map;

/**
 * Fixme : Do we want to keep this generic or always use string as the payload type?
 * <p/>
 * Argument :
 * A string payload with content type can so solve the purpose here.
 * 1. This is actually implemented in okhttp. volley also implements this by accepting the payload as
 * a string.
 * <p/>
 * 2. Specific decoration like encryption will always have a constant interface. So intermediate
 * conversion from on type to another with be very easier because you always have to take a string
 * and return a string
 * Note : Also this may come at a cost of modifying the content type after every decoration step.
 * Will have to figure out the cost of this operation ?
 */
public interface Request{
    String getUrl();

    HttpMethod getMethod();

    Map<String, String> getHeaders();

    Payload getPayload();

    String getPostBody();

    int timeout();

    Object getTag();

    String getContentType();

    RequestExecutionMode getExecutionMode();

    boolean isForceExpiry();

    String getCacheKey();
}


