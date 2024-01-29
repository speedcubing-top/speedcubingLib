package top.speedcubing.lib.api;

import top.speedcubing.lib.utils.HTTPUtils;

import java.util.UUID;

public class NameMcAPI {
    public static Boolean likedServer(UUID uuid, String ip) {
        return likedServer(uuid.toString(), ip);
    }

    public static Boolean likedServer(String uuid, String ip) {
        try {
            return Boolean.parseBoolean(HTTPUtils.get("https://api.namemc.com/server/" + ip + "/likes?profile=" + uuid));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
