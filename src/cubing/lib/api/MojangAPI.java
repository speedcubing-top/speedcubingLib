package cubing.lib.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cubing.lib.api.exception.APIErrorException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class MojangAPI {

    public static String getUUID(String name) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openConnection();
            if (connection.getResponseCode() == 200)
                return new JsonParser().parse(new InputStreamReader(connection.getInputStream())).getAsJsonObject().get("id").getAsString();
            else throw new APIErrorException(connection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] getUUIDAndName(String name) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openConnection();
            if (connection.getResponseCode() == 200) {
                JsonObject object = new JsonParser().parse(new InputStreamReader(connection.getInputStream())).getAsJsonObject();
                return new String[]{object.get("id").getAsString(), object.get("name").getAsString()};
            } else throw new APIErrorException(connection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getLatestName(String uuid) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://api.mojang.com/user/profiles/" + uuid + "/names").openConnection();
            if (connection.getResponseCode() == 200) {
                JsonArray array = new JsonParser().parse(new InputStreamReader(connection.getInputStream())).getAsJsonArray();
                return array.get(array.size() - 1).getAsJsonObject().get("name").getAsString();
            } else throw new APIErrorException(connection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getLatestName(UUID uuid) {
        return getLatestName(uuid.toString());
    }

    public static JsonElement getNameHistory(UUID uuid) {
        return getNameHistory(uuid.toString());
    }

    public static JsonElement getNameHistory(String uuid) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://api.mojang.com/user/profiles/" + uuid + "/names").openConnection();
            if (connection.getResponseCode() == 200)
                return new JsonParser().parse(new InputStreamReader(connection.getInputStream()));
            else throw new APIErrorException(connection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
