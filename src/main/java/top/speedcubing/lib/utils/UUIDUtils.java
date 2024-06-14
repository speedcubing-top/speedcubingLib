package top.speedcubing.lib.utils;

import java.util.UUID;
import top.speedcubing.lib.utils.bytes.DataConversion;

public class UUIDUtils {

    public static String dash(String uuid) {
        Preconditions.checkArgument(uuid.length() == 32, "UUID length must be 32 characters");
        return uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20);
    }

    public static String undash(UUID uuid) {
        return undash(uuid.toString());
    }

    public static String undash(String uuid) {
        Preconditions.checkArgument(uuid.length() == 36, "UUID length must be 36 characters");

        StringBuilder result = new StringBuilder();
        for (char c : uuid.toCharArray()) {
            if (c != '-') {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static byte[] toByteArray(UUID uuid) {
        return toByteArray(uuid.toString());
    }

    public static byte[] toByteArray(String uuid) {
        if (uuid.length() == 36) {
            uuid = undash(uuid);
        }
        return DataConversion.hexStringToByteArray(uuid);
    }
}
