package top.speedcubing.lib.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import top.speedcubing.lib.api.event.ProfileRespondEvent;
import top.speedcubing.lib.api.exception.APIErrorException;
import top.speedcubing.lib.api.mojang.Profile;
import top.speedcubing.lib.api.mojang.ProfileNameUUID;
import top.speedcubing.lib.api.mojang.ProfileSkin;
import top.speedcubing.lib.utils.UUIDUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class MojangAPI {

   private static ProfileNameUUID t(String name,boolean call) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openConnection();
            if (connection.getResponseCode() == 200) {
                JsonObject object = new JsonParser().parse(new InputStreamReader(connection.getInputStream())).getAsJsonObject();
                String fixedName = object.get("name").getAsString();
                String uuid = UUIDUtils.addDash(object.get("id").getAsString());
                if(call)
                    new ProfileRespondEvent(new Profile(fixedName, uuid, null, null)).call();
                return new ProfileNameUUID(fixedName, uuid);
            } else
                throw new APIErrorException(connection.getResponseCode());
        } catch (IOException e) {
            return null;
        }
    }

    //uuid -> Profile
    public static Profile getProfileByUUID(UUID uuid) {
        return getProfileByUUID(uuid.toString());
    }

    public static Profile getProfileByUUID(String uuid) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false").openConnection();
            if (connection.getResponseCode() == 200) {
                JsonObject object = new JsonParser().parse(new InputStreamReader(connection.getInputStream())).getAsJsonObject();
                JsonObject object2 = object.get("properties").getAsJsonArray().get(0).getAsJsonObject();
                Profile profile = new Profile(object.get("name").getAsString(), uuid, object2.get("value").getAsString(), object2.get("signature").getAsString());
                new ProfileRespondEvent(profile).call();
                return profile;
            } else
                throw new APIErrorException(connection.getResponseCode());
        } catch (IOException e) {
            return null;
        }
    }

    //name -> UUID
    public static UUID getUUID(String name) {
        return getNameUUIDByName(name).getUUID();
    }

    public static String getUUIDString(String name) {
        return getNameUUIDByName(name).getUUIDString();
    }

    //uuid -> Name
    public static String getName(String uuid) {
        return getProfileByUUID(uuid).getName();
    }

    public static String getName(UUID uuid) {
        return getName(uuid.toString());
    }

    //any -> Name + UUID
    public static ProfileNameUUID getNameUUIDByName(String name) {
        return t(name, true);
    }

    public static ProfileNameUUID getNameUUIDByUUID(String uuid) {
        Profile profile = getProfileByUUID(uuid);
        return new ProfileNameUUID(profile.getName(), profile.getUUIDString());
    }

    public static ProfileNameUUID getNameUUIDByUUID(UUID uuid) {
        return getNameUUIDByUUID(uuid.toString());
    }

    //any -> Skin
    public static ProfileSkin getSkinByName(String name) {
        return getSkinByUUID(t(name, false).getUUIDString());
    }

    public static ProfileSkin getSkinByUUID(String uuid) {
        Profile s = getProfileByUUID(uuid);
        return new ProfileSkin(s.getValue(), s.getSignature());
    }

    public static ProfileSkin getSkinByUUID(UUID uuid) {
        return getSkinByUUID(uuid.toString());
    }
}
