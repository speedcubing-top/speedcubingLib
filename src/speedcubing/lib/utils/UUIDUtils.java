package speedcubing.lib.utils;

import java.util.UUID;

public class UUIDUtils {

    public static String addDash(String uuid) {
        return uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20);
    }

    public static String removeDash(String uuid) {
        return uuid.replace("-", "");
    }

    public static String removeDash(UUID uuid) {
        return uuid.toString().replace("-", "");
    }
}
