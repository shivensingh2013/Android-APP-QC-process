package com.app.overboxsample.network.response;

import java.util.Map;

/**
 * Created by rohan on 8/21/15.
 */
public class AbstractResponse<T> implements Response<T> {
    private final int statusCode;
    private final Map<String, String> headers;
    private final T response;

    public AbstractResponse(Builder<T> builder) {
        if (builder == null) {
            throw new IllegalArgumentException("builder == null");
        }

        statusCode = builder.statusCode;
        headers = builder.headers;
        response = builder.response;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public T getResponse() {
        return response;
    }

    public static class Builder<T> {
        private int statusCode;
        private Map<String, String> headers;
        private T response;

        public Builder<T> response(T response) {
            this.response = response;
            return this;
        }

        public Builder<T> status(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder<T> headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public AbstractResponse<T> build() {
            return new AbstractResponse<>(this);
        }
    }
}
