package com.readview.user.service.util;

public class CommonUtils {

    public static String trimAndGet(String val) {
        return val != null && !val.isBlank() ? val.trim() : null;
    }
}
