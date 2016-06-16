package com.app.overboxsample.network.volley;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.app.overboxsample.network.config.LogConfig;
import com.app.overboxsample.network.request.Payload;
import com.app.overboxsample.network.request.Request;
import com.app.overboxsample.network.response.AbstractResponse;
import com.app.overboxsample.network.response.JsonResponse;
import com.app.overboxsample.network.response.ResponseConfig;
import com.app.overboxsample.utils.LogUtils;
import com.app.overboxsample.utils.NumberUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class VolleyStringRequest extends StringRequest {

    private interface LogFormat {
        String REQUEST_LOG_FORMAT = "[url=%s] [method=%s]\n[payload=%s]";
        String HEADER_LOG_FORMAT = "[headers=%s]";
        String RESPONSE_LOG_FORMAT = "[response=%s]";
    }

    private final Request request;

    public VolleyStringRequest(@NonNull Request request, Response.Listener<String> successListener,
                               Response.ErrorListener errorListener) {

        super(HttpMethodMapper.getVolleyHttpMethod(request.getMethod()), request.getUrl(), successListener, errorListener);

        this.request = request;

        LogUtils.verboseLog(LogConfig.API, String.format(LogFormat.REQUEST_LOG_FORMAT, request.getUrl(),
                HttpMethodMapper.getVolleyHttpMethod(request.getMethod()), request.getPostBody()));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> parentHeaders = super.getHeaders();
        Map<String, String> headers = new HashMap<>();

        if (parentHeaders != null && !parentHeaders.equals(Collections.emptyMap())) {
            headers.putAll(parentHeaders);
        }

        if (request.getHeaders() != null && !request.getHeaders().equals(Collections.emptyMap())) {
            headers.putAll(request.getHeaders());
        }

        LogUtils.verboseLog(LogConfig.API, String.format(LogFormat.HEADER_LOG_FORMAT, headers));

        return headers;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        return super.parseNetworkResponse(response);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return Payload.getMap(request.getPayload());
    }

    @Override
    public String getBodyContentType() {
        return "application/x-www-form-urlencoded";
    }

    /*@Override
    public byte[] getBody() throws AuthFailureError {
        String body = "key=ajkT14Asdfe526fasdfJKCckecsdps&command=fetch_categories";
        return body.getBytes();
    }*/

    @Override
    protected String getParamsEncoding() {
        return "utf-8";
    }

    @Override
    public Priority getPriority() {
        return Priority.HIGH;
    }
}
