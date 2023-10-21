package top.speedcubing.lib.api;

import com.google.gson.*;
import top.speedcubing.lib.api.event.ProfileRespondEvent;
import top.speedcubing.lib.api.exception.APIErrorException;
import top.speedcubing.lib.api.mojang.*;
import top.speedcubing.lib.utils.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class MojangAPI {

    private static Profile t(String name, boolean call) {
        String s = HTTPUtils.get("https://api.mojang.com/users/profiles/minecraft/" + name);
        JsonObject object = JsonParser.parseString(s).getAsJsonObject();
        String fixedName = object.get("name").getAsString();
        String uuid = UUIDUtils.addDash(object.get("id").getAsString());
        if (call)
            new ProfileRespondEvent(new ProfileSkin(fixedName, uuid, null, null)).call();
        return new Profile(fixedName, uuid);
    }

    public static List<String> blockedServers(){
        return Arrays.asList(HTTPUtils.get("https://sessionserver.mojang.com/blockedservers").split("\n"));
    }
    public static List<Profile> getByNames(String... names) {
        try {
            if (names.length > 10)
                throw new IllegalArgumentException();
            StringBuilder payload = new StringBuilder("[");
            for (int i = 0; i < names.length; i++)
                payload.append("\"").append(names[i]).append(i == names.length - 1 ? "\"" : "\",");
            payload.append("]");
            HttpURLConnection connection = (HttpURLConnection) new URL("https://api.mojang.com/profiles/minecraft").openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(payload.toString());
            out.flush();
            out.close();
            if (connection.getResponseCode() == 200) {
                StringBuilder textBuilder = new StringBuilder();
                InputStream in = connection.getInputStream();
                int b;
                while ((b = in.read()) != -1)
                    textBuilder.append((char) b);
                List<Profile> result = new ArrayList<>();
                for (JsonElement e : JsonParser.parseString(textBuilder.toString()).getAsJsonArray()) {
                    String fixedName = e.getAsJsonObject().get("name").getAsString();
                    String uuid = UUIDUtils.addDash(e.getAsJsonObject().get("id").getAsString());
                    result.add(new Profile(fixedName, uuid));

                    new ProfileRespondEvent(new ProfileSkin(fixedName, uuid, null, null)).call();
                }
                return result;
            } else throw new APIErrorException(connection.getResponseCode());
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
        String s = HTTPUtils.get("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
        JsonObject object = JsonParser.parseString(s).getAsJsonObject();
        JsonObject object2 = object.get("properties").getAsJsonArray().get(0).getAsJsonObject();
        ProfileSkin profile = new ProfileSkin(object.get("name").getAsString(), uuid, object2.get("value").getAsString(), object2.get("signature").getAsString());
        new ProfileRespondEvent(profile).call();
        return profile;
    }

    public static ProfileTexture getTextureByUUID(String uuid) {
        return getSkinByUUID(uuid).getTexture();
    }
}
