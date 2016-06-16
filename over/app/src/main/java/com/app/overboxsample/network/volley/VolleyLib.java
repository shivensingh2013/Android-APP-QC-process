package com.app.overboxsample.network.volley;

import android.support.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.app.overboxsample.network.interfaces.HttpClient;
import com.app.overboxsample.network.request.Request;
import com.app.overboxsample.network.response.AbstractResponse;
import com.app.overboxsample.network.response.JsonResponse;
import com.app.overboxsample.network.response.NetworkResponseListener;
import com.app.overboxsample.network.response.Response;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by rohan on 8/27/15.
 */
public class VolleyLib implements HttpClient<JSONObject> {

    private static final VolleyLib instance = new VolleyLib();

    public static VolleyLib getInstance() {
        return instance;
    }

    @Override
    public Response<JSONObject> excecute(@NonNull Request request) {
        return excecute(request, VolleyQueueUtils.getGeneralRequestQueue());
    }

    @Override
    public void excecuteAsync(@NonNull Request request, NetworkResponseListener<JSONObject> listener) {
        VolleyResponseListener internalListener = new VolleyResponseListener(listener);
        VolleyQueueUtils.getGeneralRequestQueue().add(new VolleyRequest(request, internalListener, internalListener));
    }

    public Response<JSONObject> excecute(@NonNull Request request, @NonNull RequestQueue queue) {
        RequestFuture<JsonResponse> future = RequestFuture.newFuture();
        VolleyRequest req = new VolleyRequest(request, future, future);
        queue.add(req);
        AbstractResponse.Builder<JSONObject> builder = new AbstractResponse.Builder<>();
        Response<JSONObject> response = null;

        try {
            response = future.get(request.timeout(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
            if (e.getCause() instanceof VolleyError) {
                VolleyError error = (VolleyError) e.getCause();
                if (error.networkResponse != null) {
                    builder.status(error.networkResponse.statusCode);
                }
                response = builder.build();
            }
        } catch (TimeoutException e) {
            response = builder.build();

        }
        return response;
    }

    @Override
    public void cancel(Object tag) {
        VolleyQueueUtils.getGeneralRequestQueue().cancelAll(tag);
    }
}
