package com.readview.user.service.util;

import com.readview.user.service.entity.User;

public class CommonUtils {

    public static String trimAndGet(String val) {
        return val != null && !val.isBlank() ? val.trim() : null;
    }

    public static String getFullUserName(User user) {
        String name = user.getFirstName();
        if(user.getFirstName() != null) {
            name += " ";
        }
        name += user.getLastName();

        return name;
    }
}
