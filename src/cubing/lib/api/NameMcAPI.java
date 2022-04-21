package cubing.lib.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

public class NameMcAPI {
    public static boolean likedServer(UUID uuid, String ip) {
        return likedServer(uuid.toString(),ip);
    }

    public static boolean likedServer(String uuid, String ip) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new URL("https://api.namemc.com/server/" + ip + "/likes?profile=" + uuid).openStream()));
            String str = in.readLine();
            in.close();
            return Boolean.parseBoolean(str);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
