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

public class VolleyRequest extends JsonRequest<JsonResponse> {

    private interface LogFormat {
        String REQUEST_LOG_FORMAT = "[url=%s] [method=%s]\n[payload=%s]";
        String HEADER_LOG_FORMAT = "[headers=%s]";
        String RESPONSE_LOG_FORMAT = "[response=%s]";
    }

    private final Request request;

    public VolleyRequest(@NonNull Request request, Response.Listener<JsonResponse> successListener,
                         Response.ErrorListener errorListener) {

        super(HttpMethodMapper.getVolleyHttpMethod(request.getMethod()), request.getUrl(),
                null, successListener, errorListener);

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
    protected Response<JsonResponse> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            JSONObject responseObject = new JSONObject(jsonString);
            Map<String, String> responseHeaders = response.headers;

            // Create the wrapped response object instead directly
            AbstractResponse.Builder<JSONObject> builder =  new AbstractResponse.Builder<>();
            responseObject.put(ResponseConfig.HTTP_STATUS_CODE, response.statusCode);
            builder.response(responseObject)
                    .headers(responseHeaders)
                    .status(response.statusCode);

            LogUtils.verboseLog(LogConfig.API, String.format(LogFormat.RESPONSE_LOG_FORMAT, responseObject));

            long sofTtl = 0, ttl = 0;

            if(responseHeaders != null && responseHeaders.containsKey(ResponseConfig.RESPONSE_KEY_CACHE_CONTROL)) {

                String cacheControlValue = responseHeaders.get(ResponseConfig.RESPONSE_KEY_CACHE_CONTROL);
                if(!TextUtils.isEmpty(cacheControlValue)) {
                    String[] parts = cacheControlValue.split(",");

                    if(parts != null && parts.length == 2) {
                        sofTtl = NumberUtils.toLong(parts[0].split("=")[1]) * 1000;
                        ttl = NumberUtils.toLong(parts[1].split("=")[1]) * 1000;
                    }
                }
            }

            return Response.success(new JsonResponse(builder), getCacheEntry(response, sofTtl, ttl));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));

        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    /**
     * @see <a href="http://stackoverflow.com/a/16852314/507905">Answer</a>
     *
     * Extracts a {@link Cache.Entry} from a {@link NetworkResponse}.
     * Cache-control headers are ignored. SoftTtl == 3 mins, ttl == 24 hours.
     * @param response The network response to parse headers from
     * @return a cache entry for the given response, or null if the response is not cacheable.
     */
    private Cache.Entry getCacheEntry(NetworkResponse response, long softExpiryDelta, long hardExpiryDelta) {
        long now = System.currentTimeMillis();

        Map<String, String> headers = response.headers;
        long serverDate = 0;
        String serverEtag = null;
        String headerValue;

        headerValue = headers.get("Date");
        if (headerValue != null) {
            serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
        }

        serverEtag = headers.get("ETag");

        // in 3 minutes cache will be hit, but also refreshed on background
        final long softTtl = now + softExpiryDelta;
        final long ttl = now + hardExpiryDelta;

        Cache.Entry entry = new Cache.Entry();
        entry.data = response.data;
        entry.etag = serverEtag;
        entry.softTtl = softTtl;
        entry.ttl = ttl;
        entry.serverDate = serverDate;
        entry.responseHeaders = headers;

        return entry;
    }

    @Override
    protected void deliverResponse(JsonResponse response) {
        super.deliverResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
    }

    @Override
    public String getPostBodyContentType() {
        return "application/x-www-form-urlencoded; charset=UTF-8";
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return Payload.getMap(request.getPayload());
    }

    @Override
    public String getBodyContentType() {
        return "application/x-www-form-urlencoded; charset=UTF-8";
    }

    @Override
    public String getCacheKey() {
        String cacheKey = request.getCacheKey();

        //FAIL-SAFE if anyhow cachekey is NULL or ""
        if(TextUtils.isEmpty(cacheKey)) cacheKey = request.getUrl();

        return cacheKey;
    }

    @Override
    public Priority getPriority() {
        return Priority.HIGH;
    }
}
