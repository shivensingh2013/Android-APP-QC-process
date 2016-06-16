package com.app.overboxsample.utils;

import java.util.HashMap;
import java.util.Map;

public class ApiUtils {

    public static Map<String, String> getDefaultHeaders() {
        Map<String, String> defaultHeaders = new HashMap<>(0);
        //defaultHeaders.put("Content-Type", "application/json");
        defaultHeaders.put("Content-Type", "application/x-www-form-urlencoded");

        return defaultHeaders;
    }
}
