package com.app.overboxsample.network.request;

import android.text.TextUtils;

import com.app.overboxsample.network.utils.HttpMethod;
import com.app.overboxsample.network.utils.RequestExecutionMode;

import java.util.Map;

/**
 * Created by rohan on 8/21/15.
 */
public class AbstractRequest implements Request {
    private final String url;
    private final HttpMethod method;
    private final Map<String, String> headers;
    private final int timeout;
    private final Object tag;
    private final String contentType;
    private final RequestExecutionMode executionMode;
    private final boolean isForceExpiry;
    private final String body;

    private final Payload payload;
    private final String cacheKey;

    public AbstractRequest(Builder builder) {
        if (builder == null) {
            throw new IllegalArgumentException("builder == null");
        }

        method = builder.method;
        url = builder.url;
        headers = builder.headers;
        timeout = builder.timeout;
        tag = builder.tag;
        contentType = builder.contentType;
        executionMode = builder.executionMode;
        isForceExpiry = builder.isForceExpiry;
        body = builder.body;
        payload = builder.payload;
        this.cacheKey = builder.cacheKey;
    }

    public Payload getPayload() {
        return payload;
    }

    /**
     * returns complete request url after appending UTF-8 encoded query params
     */
    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public HttpMethod getMethod() {
        return method;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public String getPostBody() {
        return body;
    }

    @Override
    public int timeout() {
        return timeout;
    }

    @Override
    public Object getTag() {
        return tag;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public RequestExecutionMode getExecutionMode() {
        return executionMode;
    }

    @Override
    public boolean isForceExpiry() {
        return isForceExpiry;
    }

    @Override
    public String getCacheKey() {
        return cacheKey;
    }

    public static class Builder {
        private String url;
        private HttpMethod method;
        private Map<String, String> headers;
        private String body;
        private Payload payload;
        private int timeout;
        private Object tag;
        private String contentType;
        private RequestExecutionMode executionMode;
        private boolean isForceExpiry;
        private String cacheKey;

        public Builder(HttpMethod method) {
            if (method == null) {
                throw new IllegalArgumentException("method == null");
            }
            this.method = method;
            this.contentType = ContentType.JSON;
        }

        public Builder url(String url) {
            if (TextUtils.isEmpty(url)) {
                throw new IllegalArgumentException("url is null or empty");
            }
            this.url = url;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder payload(Payload payload) {
            this.payload = payload;
            return this;
        }

        public Builder timeout(int timout) {
            this.timeout = timout;
            return this;
        }

        public Builder tag(Object tag) {
            this.tag = tag;
            return this;
        }

        public Builder executionMode(RequestExecutionMode mode) {
            this.executionMode = mode;
            return this;
        }

        public Builder isForceExpiry(boolean isForceExpiry) {
            this.isForceExpiry = isForceExpiry;
            return this;
        }

        public Builder cacheKey(String cacheKey) {
            this.cacheKey = cacheKey;
            return this;
        }

        public AbstractRequest build() {
            return new AbstractRequest(this);
        }
    }
}
