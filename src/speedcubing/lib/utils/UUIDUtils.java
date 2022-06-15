package speedcubing.lib.utils;

import java.util.UUID;

public class UUIDUtils {

    public static String addDash(String uuid) {
        if(uuid.length() != 32)
            throw new IllegalArgumentException();
        return uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20);
    }

    public static String unDash(String uuid) {
        return uuid.replace("-", "");
    }

    public static String unDash(UUID uuid) {
        return unDash(uuid.toString());
    }
}
