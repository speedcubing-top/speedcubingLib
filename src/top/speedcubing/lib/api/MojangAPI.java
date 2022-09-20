package top.speedcubing.lib.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import top.speedcubing.lib.api.event.ProfileRespondEvent;
import top.speedcubing.lib.api.event.SkinRespondEvent;
import top.speedcubing.lib.api.exception.APIErrorException;
import top.speedcubing.lib.utils.UUIDUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class MojangAPI {

    public static class Profile {
        private UUID uuid;
        private String name;

        public Profile(String name) {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openConnection();
                if (connection.getResponseCode() == 200) {
                    JsonObject object = new JsonParser().parse(new InputStreamReader(connection.getInputStream())).getAsJsonObject();
                    this.uuid = UUID.fromString(UUIDUtils.addDash(object.get("id").getAsString()));
                    this.name = object.get("name").getAsString();
                    new ProfileRespondEvent(name, uuid).call();
                } else
                    throw new APIErrorException(connection.getResponseCode());
            } catch (IOException e) {
            }
        }
    }

    public static class Skin {
        private String name;
        private String value;
        private String signature;

        public Skin(String uuid) {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false").openConnection();
                if (connection.getResponseCode() == 200) {
                    JsonObject object = new JsonParser().parse(new InputStreamReader(connection.getInputStream())).getAsJsonObject();
                    this.name = object.get("name").getAsString();
                    object = object.get("properties").getAsJsonArray().get(0).getAsJsonObject();
                    this.value = object.get("value").getAsString();
                    this.signature = object.get("signature").getAsString();
                    UUID id = UUID.fromString(uuid);
                    new ProfileRespondEvent(name, id).call();
                    new SkinRespondEvent(name, id, value, signature).call();
                } else
                    throw new APIErrorException(connection.getResponseCode());
            } catch (IOException e) {
            }
        }
    }

    public static UUID getUUID(String name) {
        return new Profile(name).uuid;
    }

    public static String[] getUUIDAndName(String name) {
        Profile profile = new Profile(name);
        return profile.uuid == null ? null : new String[]{profile.uuid.toString(), profile.name};
    }

    public static String getName(String uuid) {
        return new Skin(uuid).name;
    }

    public static String getName(UUID uuid) {
        return getName(uuid.toString());
    }

    public static String[] getSkin(String uuid) {
        Skin skin = new Skin(uuid);
        return skin.name == null ? null : new String[]{skin.value, skin.signature};
    }

    public static String[] getSkin(UUID uuid) {
        return getSkin(uuid.toString());
    }
}
