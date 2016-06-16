package com.app.overboxsample.network.response;

import org.json.JSONObject;

public class JsonResponse extends AbstractResponse<JSONObject> {
    public JsonResponse(Builder<JSONObject> builder) {
        super(builder);
    }
}
