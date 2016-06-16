package com.app.overboxsample.network.utils;

import com.app.overboxsample.network.response.Response;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

/**
 * Created by guneet on 8/28/15.
 */
public class NetworkUtils {
    public static String encodeUTF8(Map<String, String> params) {
        if (params != null && params.size()>0)
            return "?" + encodeUTF8NonPrefixed(params);
        else
            return "";
    }

    public static String encodeUTF8NonPrefixed(Map<String, String> params) {
        StringBuilder builder = new StringBuilder();
        if( params != null ) {
            Set<String> keySet = params.keySet();
            int i = 0;
            for (String key : keySet) {
                String value;
                value = encode(params.get(key), "UTF-8");
                builder.append(key + "=" + value);
                if (i < keySet.size() - 1)
                    builder.append("&");
                i++;
            }
        }
        return builder.toString();
    }

    public static String encode(String value, String standard) {
        if(value == null || value.isEmpty()) return value;

        try {
            return URLEncoder.encode(value, standard);
        } catch (UnsupportedEncodingException e) {
            return value;
        }
    }

    public static boolean isSuccess(Response<JSONObject> response) {
        return !(response == null || response.getStatusCode() != 200);
    }
}
