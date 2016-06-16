package com.app.overboxsample.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by himanshu on 26/08/15.
 */
public class NumberUtils {

    private static final String  NUMBER_REGEX        = "\\d{10}";
    private static final String  SIM_NUMBER_REGEX        = "\\d{19}[u]";
    private static final String  LEADING_ZEROS_REGEX = "^0+(?!$)";
    private static final String  NON_DIGIT_REGEX     = "\\D";
    private static final Pattern sPattern            = Pattern.compile(NUMBER_REGEX);
    private static final Pattern sSimPattern            = Pattern.compile(SIM_NUMBER_REGEX);

    public static float toFloat(String value) {
        if (TextUtils.isEmpty(value)) return 0f;
        try {
            return Float.valueOf(value);
        } catch (NumberFormatException e) {
            return 0f;
        }
    }

    public static boolean toFloat(String value, float number) {
        if (TextUtils.isEmpty(value)) return false;
        try {
            number = Float.valueOf(value);
            return true;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static long toLong(String value) {
        if (TextUtils.isEmpty(value)) return 0;
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static long toLong(Object value) {
        if (value == null) return 0;

        if (value instanceof String) {
            return toLong((String) value);
        } else if (value instanceof Long) {
            return (Long) value;
        }

        return 0;
    }

    public static double toDouble(String value) {
        if (TextUtils.isEmpty(value)) return 0;
        try {
            return Double.valueOf(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static double toDouble(String value, double defVal) {
        if (TextUtils.isEmpty(value)) return defVal;
        try {
            return Double.valueOf(value);
        } catch (NumberFormatException e) {
            return defVal;
        }
    }

    public static double toDouble(Object value) {
        if (value == null) return 0;

        if (value instanceof String) {
            return toDouble((String) value);
        } else if (value instanceof Double) {
            return (Double) value;
        } else if (value instanceof Integer || value instanceof Float) {
            return toDouble(String.valueOf(value));
        }

        return 0;
    }

    public static int toInt(String value) {
        if (TextUtils.isEmpty(value)) return 0;
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     *
     * @param value
     * @return
     */
    public static float mod(String value) {
        if (TextUtils.isEmpty(value)) return 0;
        return Math.abs(NumberUtils.toFloat(value));
    }

    /**
     * Fail-safe method which returns object value of String,
     * String.valueOf(Object) returns "null" which is not required.
     * @param value
     * @return
     */
    public static String toString(Object value) {
        if(value == null) return null;
        return String.valueOf(value);
    }

    @Deprecated
    public static boolean toBoolean(Object value) {
        return toBoolean(value, false);
    }

    public static boolean toBoolean(Object value, boolean defaultValue) {
        boolean flag = false;
        if(value == null) return flag;
        if(value instanceof String) {
            try {
                String str = (String)value;
                if (TextUtils.isEmpty(str)) flag = false;
                flag =  Boolean.valueOf(str);

            } catch (NumberFormatException e) {
                flag =  false;
            }
        }
        if(value instanceof Integer) {
            try {
                flag = (Integer)value == 1;

            } catch (Exception e) {
                flag =  false;
            }
        }
        return flag;
    }

    public static float getFraction(float numerator, float denominator) {
        float fraction = 0;
        if (denominator == 0) return fraction;

        fraction = numerator / denominator;

        if (fraction > 1) return 1f;
        if (fraction < 0) return 0f;

        return fraction;
    }

    public static float getPercentage(String numerator, String denominator) {
        return getFraction(toFloat(numerator), toFloat(denominator)) * 100;
    }

    public static boolean hasExpired(long timestamp) {
        return timestamp < System.currentTimeMillis();
    }

    public static boolean hasExpired(String timestamp) {
        return hasExpired(toLong(timestamp));
    }

    public static byte[] toByteArray( Object ob ){
        return ((ob.toString()).getBytes());
    }

    /**
     * Get 10 digit msisdn. It does not validate msisdn for being numeric.
     *
     * @param msisdn
     * @return 10 character long msisdn
     */
    public static String getTenDigitMsisdn(String msisdn) {
        if (TextUtils.isEmpty(msisdn))
            return msisdn;
        msisdn = msisdn.trim();
        int length = msisdn.length();
        if (length == 10) {
            return msisdn;
        }
        if (length > 10) {
            return msisdn.substring(length - 10);
        }

        return msisdn;
    }

    /**
     * Adds +91 prefix to 10 digit msisdn. It does not validate msisdn for being
     * numeric.
     *
     * @param msisdn
     * @return +91 prefixed 13 character long msisdn
     */
    public static String getPrefixedMsisdn(String msisdn) {
        if (TextUtils.isEmpty(msisdn))
            return msisdn;
        msisdn = msisdn.trim();
        int length = msisdn.length();
        if (length == 10) {
            return "+91" + msisdn;
        }
        if (length > 10) {
            return "+91" + msisdn.substring(length - 10);
        }

        return msisdn;
    }

    /**
     * Adds 91 prefix to 10 digit msisdn. It does not validate msisdn for being
     * numeric. It Does NOT append '+' to the msisdn, use {@link #getPrefixedMsisdn(String)}
     * for that.
     *
     * @param msisdn
     * @return 91 prefixed 12 character long msisdn
     */
    public static String getPrefixedMsisdnNoPlus(String msisdn) {
        if (TextUtils.isEmpty(msisdn))
            return msisdn;
        msisdn = msisdn.trim();
        int length = msisdn.length();
        if (length == 10) {
            return "91" + msisdn;
        }
        if (length > 10) {
            return "91" + msisdn.substring(length - 10);
        }

        return msisdn;
    }

    /**
     * Validates a number
     *
     * @param number
     * @return true/false
     */
    public static boolean isPhoneNumberValid(String number) {
        if (TextUtils.isEmpty(number)) {
            return false;
        }

        number = number.trim();
        number = number.replaceFirst(LEADING_ZEROS_REGEX, "");

        Matcher matcher = sPattern.matcher(number);
        boolean valid = matcher.matches();

        return valid;
    }

    public static String getTenDigitNumberForContact(String numberInContact) {
        if (TextUtils.isEmpty(numberInContact)) {
            return numberInContact;
        }
        numberInContact = numberInContact.trim();
        numberInContact = numberInContact.replace(" ", "");
        return getTenDigitMsisdn(numberInContact);
    }

    public static String formatNumber(String number) {
        if (TextUtils.isEmpty(number)) {
            return "";
        } else {
            number = number.replaceAll(NON_DIGIT_REGEX, "");
            // number = number.replace(" ","").replace("-","").replace("+","");
            String tempNumber = number;
            if (tempNumber.startsWith("91")) {
                tempNumber = tempNumber.substring(2);
            } else if (tempNumber.startsWith("0")) {
                tempNumber = tempNumber.substring(1);
            }
            return tempNumber;
        }
    }

    /**
     * This method assumes that
     *
     * @param number
     *            is already formatted using formatNumber method.
     * @return
     */
    public static boolean isValidIndianMobile(String number) {
        if (TextUtils.isEmpty(number)) {
            return false;
        }

        boolean isNumber = false;
        if (number.length() == 10 && (number.startsWith("9") || number.startsWith("8") || number.startsWith("7"))) {
            isNumber = true;
        }
        return isNumber;
    }

    public static boolean isValidSimNumber(String number) {
        if (TextUtils.isEmpty(number)) {
            return false;
        }
        boolean isNumber = false;
        number = number.toLowerCase();
        number = number.trim();

        Matcher matcher = sSimPattern.matcher(number);
        boolean valid = matcher.matches();

        /*if (number.length() == 20 && (number.endsWith("u"))) {
            isNumber = true;
        }*/
        return valid;
    }
}
