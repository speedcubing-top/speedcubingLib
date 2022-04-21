package cubing.lib.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cubing.lib.api.exception.APIErrorException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class SessionServer {

    public static String[] getSkin(String uuid) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false").openConnection();
            if (connection.getResponseCode() == 200) {
                JsonObject property = new JsonParser().parse(new InputStreamReader(connection.getInputStream())).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
                return new String[]{property.get("value").getAsString(), property.get("signature").getAsString()};
            } else throw new APIErrorException(connection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] getSkin(UUID uuid) {
        return getSkin(uuid.toString());
    }
}
