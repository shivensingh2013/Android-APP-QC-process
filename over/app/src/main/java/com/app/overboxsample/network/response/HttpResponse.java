package com.app.overboxsample.network.response;

/**
 * Created by himanshu on 07/09/15.
 */
public class HttpResponse<D> {
    HttpResponseStatus httpResponseStatus;
    D data;

    public HttpResponse() {
    }

    public HttpResponse(HttpResponseStatus status, D data) {
        httpResponseStatus = status;
        this.data = data;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public HttpResponseStatus getStatus() {
        return httpResponseStatus;
    }

    public void setStatus(HttpResponseStatus httpResponseStatus) {
        this.httpResponseStatus = httpResponseStatus;
    }
}
