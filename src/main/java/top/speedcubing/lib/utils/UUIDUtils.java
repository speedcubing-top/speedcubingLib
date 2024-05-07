package top.speedcubing.lib.utils;

import java.util.UUID;

public class UUIDUtils {

    public static String dash(String uuid) {
        if (uuid.length() != 32)
            throw new IllegalArgumentException("UUID length must be 32 characters");
        return uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20);
    }

    public static String undash(UUID uuid) {
        return undash(uuid.toString());
    }

    public static String undash(String uuid) {
        if (uuid.length() != 36)
            throw new IllegalArgumentException("UUID length must be 36 characters");
        StringBuilder result = new StringBuilder();
        for (char c : uuid.toCharArray()) {
            if (c != '-') {
                result.append(c);
            }
        }
        return result.toString();
    }
}
