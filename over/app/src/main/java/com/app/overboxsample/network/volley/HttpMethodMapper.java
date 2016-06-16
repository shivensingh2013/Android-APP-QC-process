package com.app.overboxsample.network.volley;

import com.android.volley.Request;
import com.app.overboxsample.network.utils.HttpMethod;

public class HttpMethodMapper {

    private HttpMethodMapper() {
        // No instance creation
    }

    public static int getVolleyHttpMethod(HttpMethod method) {
        switch (method) {
            case GET:
                return Request.Method.GET;

            case POST:
                return Request.Method.POST;

            case PUT:
                return Request.Method.PUT;

            case PATCH:
                return Request.Method.PATCH;

            case DELETE:
                return Request.Method.DELETE;

            default:
                return -1;
        }
    }
}
