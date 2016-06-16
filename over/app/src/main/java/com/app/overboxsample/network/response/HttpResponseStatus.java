package com.app.overboxsample.network.response;

import android.text.TextUtils;

import org.json.JSONObject;

/**
 * {
 * "status": "success",
 * "responseCode": "0",
 * "data": {},
 * "errorMessage": "this is an error",
 * "errorCode": 668889
 * }
 */
public class HttpResponseStatus {

    private interface Keys {
        String status = "status";
        String responseCode = "responseCode";
        String errorMsg = "errorMsg";
        String errorCode = "errorCode";
        String errorMessage = "errorMessage";
    }

    private String status;
    private int responseCode;
    private String errorMessage;
    private int errorCode;
    private boolean isSuccess = true; //default is true

    public HttpResponseStatus() {}

    public HttpResponseStatus(JSONObject response)  {
        parse(response);
    }

    public static HttpResponseStatus getErrorStatus(String message) {
        HttpResponseStatus status = new HttpResponseStatus();
        status.responseFailed(message, ResponseConfig.ResponseError.NONE.getCode());

        return status;
    }

    private void parse(JSONObject response) {
        int volleyStatusCode = response.optInt(ResponseConfig.VOLLEY_STATUS_CODE);
        int httpStatusCode = response.optInt(ResponseConfig.HTTP_STATUS_CODE);
        int responseCode = response.optInt(Keys.responseCode);

        if(volleyStatusCode != ResponseConfig.ResponseError.NONE.getCode()){
            responseFailed(response.optString(Keys.errorMsg), response.optInt(ResponseConfig.VOLLEY_STATUS_CODE));
            return;

        } else if(httpStatusCode != 200){
            responseFailed(response.optString(Keys.errorMsg), httpStatusCode);
            return;

        } else if(responseCode != ResponseConfig.ResponseError.NONE.getCode()) {
            responseFailed(response.optString(Keys.errorMsg), responseCode);
            return;
        }

        setIsSuccess(true);
        setStatus(response.optString(Keys.status));
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
       return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void responseFailed(String errorMessage, int errorCode){
        ResponseConfig.ResponseError error = ResponseConfig.ResponseError.getById(errorCode);

        if(error == null) {
            error = ResponseConfig.ResponseError.NONE;
        }

        setIsSuccess(false);

        if(TextUtils.isEmpty(errorMessage)) {
            setErrorMessage(error.getMessage());

        } else {
            setErrorMessage(errorMessage);
        }

        setErrorCode(errorCode);
    }

    public  void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
