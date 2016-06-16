package com.app.overboxsample.network.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.app.overboxsample.network.response.AbstractResponse;
import com.app.overboxsample.network.response.JsonResponse;
import com.app.overboxsample.network.response.NetworkResponseListener;
import com.app.overboxsample.network.response.ResponseConfig;

import org.json.JSONException;
import org.json.JSONObject;

public class VolleyResponseListener implements Response.ErrorListener, Response.Listener<JsonResponse> {

    private final NetworkResponseListener<JSONObject> listener;

    public VolleyResponseListener(NetworkResponseListener<JSONObject> listener) {
        this.listener = listener;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        AbstractResponse.Builder<JSONObject> builder = new AbstractResponse.Builder<>();
        JSONObject obj = new JSONObject();

        int code = parseVolleyErrorCode(error).getCode();

        try {
            obj.put(ResponseConfig.VOLLEY_STATUS_CODE, code);
        } catch (JSONException e) {
        }

        builder.response(obj).status(code);
        notifyListener(builder.build());
    }

    @Override
    public void onResponse(JsonResponse response) {
        notifyListener(response);
    }

    private void notifyListener(AbstractResponse<JSONObject> response) {
        if (listener != null) {
            listener.onNetworkResponse(response);
        }
    }

    private ResponseConfig.ResponseError parseVolleyErrorCode(VolleyError error){
        ResponseConfig.ResponseError errorCode = ResponseConfig.ResponseError.NONE;

        if (error instanceof TimeoutError) {
            errorCode = ResponseConfig.ResponseError.TIMEOUT_ERROR;
        } else if (error instanceof AuthFailureError) {
            errorCode = ResponseConfig.ResponseError.AUTH_FAILURE_ERROR;
        } else if (error instanceof NoConnectionError) {
            errorCode = ResponseConfig.ResponseError.NO_CONNECTION_ERROR;
        } else if (error instanceof ServerError) {
            errorCode = ResponseConfig.ResponseError.SERVER_ERROR;
        } else if (error instanceof NetworkError) {
            errorCode = ResponseConfig.ResponseError.NETWORK_ERROR;
        } else if (error instanceof ParseError) {
            errorCode = ResponseConfig.ResponseError.PARSE_ERROR;
        }

        return errorCode;
    }
}
