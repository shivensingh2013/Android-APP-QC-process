package com.app.overboxsample.network.response;

import android.text.TextUtils;

/**
 * Created by himanshu on 09/09/15.
 */
public class ResponseConfig {

    public static final String HTTP_STATUS_CODE ="httpStatusCode";
    public static final String VOLLEY_STATUS_CODE ="volleyStatusCode";
    public static final String RESPONSE_KEY_CACHE_CONTROL = "cache-control";

    public enum ResponseError {
        NONE("", 0),
        TIMEOUT_ERROR("", 1),
        NO_CONNECTION_ERROR("", 2),
        AUTH_FAILURE_ERROR("", 3),
        SERVER_ERROR("", 4),
        NETWORK_ERROR("", 5),
        PARSE_ERROR("", 6);

        private String message;
        private int code;

        ResponseError(String message, int code) {
            this.message = message;
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return this.message;
        }

        public static ResponseError getById(int id) {
            for (ResponseError error : ResponseError.values()) {
                if (error.code == id) {
                    return error;
                }
            }
            return ResponseError.NONE;
        }
    }

    public enum WalletStatusCode {
        NONE("-1"), SUCCESS("0"), FAILURE("1"), FAVORITE_NOT_FOUND("3");

        String id;

        WalletStatusCode(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public static WalletStatusCode getCode(String id) {
            if(TextUtils.isEmpty(id)) return NONE;

            for(WalletStatusCode code : WalletStatusCode.values()) {
                if(id.equalsIgnoreCase(code.getId())) return  code;
            }

            return NONE;
        }
    }

    public enum WalletErrorCode {
        NONE("-1"), TIMESTAMP_EXPIRED("999998"), DEVICE_ID_EXPIRED("1111"), USER_NOT_REGISTERED("65006"),
        MERCHANT_NOT_REGISTERED("65010"), USER_BLOCKED("78046"), USER_BLOCKED_2("99142"),
        OTP_VERIFY_SUCCESS("000"), INCORRECT_MPIN("99105"), HISTORY_NO_DATA_FOUND("55004"),
        MPIN_BLOCKED("99145"), MPIN_EXPIRED("99146"), ALREADY_REGISTERED("35117");

        String id;

        WalletErrorCode(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public static WalletErrorCode parse(String errorCode) {
            if (TextUtils.isEmpty(errorCode)) return NONE;

            for (WalletErrorCode error : WalletErrorCode.values()) {
                if (errorCode.matches(error.getId()))
                    return error;
            }

            return NONE;
        }
    }
}
