package top.speedcubing.lib.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

public class NameMcAPI {
    public static Boolean likedServer(UUID uuid, String ip) {
        return likedServer(uuid.toString(), ip);
    }

    public static Boolean likedServer(String uuid, String ip) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new URL("https://api.namemc.com/server/" + ip + "/likes?profile=" + uuid).openStream()));
            String str = in.readLine();
            in.close();
            return Boolean.parseBoolean(str);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
