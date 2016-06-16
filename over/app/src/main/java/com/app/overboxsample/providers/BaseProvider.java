package com.app.overboxsample.providers;

import com.app.overboxsample.network.interfaces.IViewCallback;
import com.app.overboxsample.network.response.HttpResponse;
import com.app.overboxsample.utils.ExecutorUtils;

public abstract class BaseProvider {

    private boolean isAttached = false;

    public void attach() {
        isAttached = true;
    }

    public void detach() {
        isAttached = false;
    }

    protected void notifyResponse(HttpResponse httpResponse, IViewCallback viewCallback) {
        final HttpResponse innerResponse = httpResponse;
        final IViewCallback innerCallback = viewCallback;

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                postResponse(innerResponse, innerCallback);
            }
        };
        if (viewCallback != null)
            ExecutorUtils.postOnUiThread(runnable);
    }

    private void postResponse(HttpResponse httpResponse, IViewCallback viewCallback){

        if (isAttached) {
            if (viewCallback != null) {
                if (isSuccessResponse(httpResponse)) {
                    viewCallback.onSuccess(httpResponse.getData());

                } else {
                    viewCallback.onError(httpResponse.getStatus().getErrorMessage(),
                            httpResponse.getStatus().getErrorCode(), httpResponse.getData());
                }
            }
        }
    }

    protected boolean isSuccessResponse(HttpResponse httpResponse) {
        return httpResponse != null && httpResponse.getStatus().isSuccess();
    }
}
