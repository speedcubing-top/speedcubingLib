package top.speedcubing.lib.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;
import top.speedcubing.lib.api.event.ProfileRespondEvent;
import top.speedcubing.lib.api.exception.APIErrorException;
import top.speedcubing.lib.api.mojang.ProfileSkin;
import top.speedcubing.lib.api.mojang.Profile;
import top.speedcubing.lib.utils.UUIDUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class MojangAPI {

    private static Profile t(String name, boolean call) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openConnection();
            if (connection.getResponseCode() == 200) {
                JsonObject object = new JsonParser().parse(new InputStreamReader(connection.getInputStream())).getAsJsonObject();
                String fixedName = object.get("name").getAsString();
                String uuid = UUIDUtils.addDash(object.get("id").getAsString());
                if (call)
                    new ProfileRespondEvent(new ProfileSkin(fixedName, uuid, null, null)).call();
                return new Profile(fixedName, uuid);
            } else
                throw new APIErrorException(connection.getResponseCode());
        } catch (IOException e) {
            return null;
        }
    }

    public static Profile getByName(String name) {
        return t(name, true);
    }

    public static ProfileSkin getSkinByName(String name) {
        Profile p = t(name, false);
        return getSkinByUUID(p.getUUID());
    }


    public static ProfileSkin getSkinByUUID(UUID uuid) {
        return getSkinByUUID(uuid.toString());
    }

    public static ProfileSkin getSkinByUUID(String uuid) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false").openConnection();
            if (connection.getResponseCode() == 200) {
                JsonObject object = new JsonParser().parse(new InputStreamReader(connection.getInputStream())).getAsJsonObject();
                JsonObject object2 = object.get("properties").getAsJsonArray().get(0).getAsJsonObject();
                ProfileSkin profile = new ProfileSkin(object.get("name").getAsString(), uuid, object2.get("value").getAsString(), object2.get("signature").getAsString());
                new ProfileRespondEvent(profile).call();
                return profile;
            } else
                throw new APIErrorException(connection.getResponseCode());
        } catch (IOException e) {
            return null;
        }
    }
}
