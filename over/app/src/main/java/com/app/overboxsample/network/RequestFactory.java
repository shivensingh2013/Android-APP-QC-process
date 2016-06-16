package com.app.overboxsample.network;

import com.app.overboxsample.network.request.AbstractRequest;
import com.app.overboxsample.network.request.ContentType;
import com.app.overboxsample.network.request.Payload;
import com.app.overboxsample.network.request.Request;
import com.app.overboxsample.network.utils.HttpMethod;
import com.app.overboxsample.network.utils.NetworkUtils;
import com.app.overboxsample.network.utils.RequestExecutionMode;
import com.app.overboxsample.utils.ApiUtils;

import java.util.Map;

/**
 * Created by rohan on 8/27/15.
 */
public class RequestFactory {

    private RequestFactory() {}

    public static Request createRequest(HttpMethod method, String url, Map<String, String> params,
                                        Payload payload, Map<String, String> headers, int timeout,
                                        Object tag, String cacheKey) {

        Map<String, String> defaultHeaders = ApiUtils.getDefaultHeaders();
        if(headers != null) defaultHeaders.putAll(headers);

        return createStringRequest(method, url, params, payload, defaultHeaders, timeout, tag, cacheKey);
    }

    static Request createStringRequest(HttpMethod method, String url,
                                              Map<String, String> params, Payload payload,
                                              Map<String, String> headers, int timeout, Object tag, String cacheKey) {

        return new AbstractRequest.Builder(method)
                .url(url + NetworkUtils.encodeUTF8(params))
                .body(Payload.getJsonString(payload))
                .headers(headers)
                .timeout(timeout)
                .tag(tag)
                .payload(payload)
                .contentType(ContentType.JSON)
                .isForceExpiry(false)
                .executionMode(RequestExecutionMode.MULTI_THREAD)
                .cacheKey(cacheKey)
                .build();
    }

    /*public static Request createSignedRequest(HttpMethod method, String url,
                                              Map<String, String> params, Payload payload,
                                              Map<String, String> headers, int timeout, Object tag) {
        return createSignedRequest(method, url, params, payload, headers, timeout, tag, url + NetworkUtils.encodeUTF8(params));
    }*/

    /*public static Request createSignedRequest(HttpMethod method, String url,
                                              Map<String, String> params, Payload payload,
                                              Map<String, String> headers, int timeout, Object tag, String cacheKey) {

        String body = Payload.getMapString(payload);

        return createSignedStringRequest(method, url, params, body, headers, timeout, tag, cacheKey);
    }*/

    /*public static Request createSignedStringRequest(HttpMethod method, String url,
                                              Map<String, String> params, String body,
                                              Map<String, String> headers, int timeout, Object tag, String cacheKey) {

        String fullUrl = url + NetworkUtils.encodeUTF8(params);
        Map<String, String> signed = ApiUtils.getDefaultHeaders();

        return createStringRequest(method, fullUrl, null, body, signed, timeout, tag, cacheKey);
    }*/
}
