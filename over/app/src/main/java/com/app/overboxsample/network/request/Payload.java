package com.app.overboxsample.network.request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Just a wrapper around JSONObject.
 * <p/>
 * Kept around to avoid code rewrite
 * and the convenience of chaining and Exception handling while adding data
 * <p/>
 * Created by himanshu on 31/08/15.
 */
public class Payload extends JSONObject {

    public Payload add(String key, Object value) {
        try {
            super.put(key, value);
        } catch (JSONException ignore) {
        }
        return this;
    }

    public Payload addAll(HashMap<String, String> params) {
        for (Map.Entry<String, String> keyValPair : params.entrySet()) {
            add(keyValPair.getKey(), keyValPair.getValue());
        }
        return this;
    }

    public static String getJsonString(Payload payload) {
        if (payload == null || payload.length() == 0)
            return "";

        return payload.toString();
    }

    public static HashMap<String, String> getMap(Payload payload) {
        HashMap<String, String> hashMap = new HashMap<>();

        Iterator<String> keys = payload.keys();

        String key;
        while(keys.hasNext()) {
            key = keys.next();

            try {
                hashMap.put(key, payload.getString(key));
            } catch (JSONException e) {
            }
        }

        return hashMap;
    }
}
