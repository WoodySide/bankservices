package com.woodyside.captcha.util;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class UtilRandomStringGenerator {

    public static String generate() {
        return UUID.randomUUID().toString();
    }

    public static String toHash(String hashed) {
        return UUID.nameUUIDFromBytes(hashed.getBytes()).toString();
    }
}
