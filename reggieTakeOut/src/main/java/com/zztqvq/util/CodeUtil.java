package com.zztqvq.util;

import java.util.UUID;

public class CodeUtil {
    public static String generateUniqueCode() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
