package top.speedcubing.lib.api.mcserverping;

import com.google.gson.*;
import top.speedcubing.lib.utils.minecraft.TextUtils;

import java.util.*;

public class ServerPingInfo {
    private final String json;
    private final String versionName;
    private final int versionProtocol;
    private final int playerMax;
    private final int playerOnline;
    private final List<PlayerSample> playerSample = new ArrayList<>();
    private final String description;
    private final String favicon;
    private final String modType;

    public ServerPingInfo(String s) {
        this.json = s;
        JsonObject data = JsonParser.parseString(s).getAsJsonObject();
        JsonObject object = data.getAsJsonObject("version");
        versionName = object.get("name").getAsString();
        versionProtocol = object.get("protocol").getAsInt();
        object = data.getAsJsonObject("players");
        playerMax = object.get("max").getAsInt();
        playerOnline = object.get("online").getAsInt();
        if (object.has("sample"))
            for (JsonElement e : object.get("sample").getAsJsonArray())
                playerSample.add(new PlayerSample(e.getAsJsonObject().get("name").getAsString(), e.getAsJsonObject().get("id").getAsString()));
        if (data.has("favicon"))
            favicon = data.get("favicon").getAsString();
        else favicon = null;

        if (data.get("description").isJsonPrimitive())
            description = data.get("description").getAsString();
        else
            description = TextUtils.componentToText(data.getAsJsonObject("description"));

        //forge
        if (data.has("modinfo")) {
            modType = data.getAsJsonObject("modinfo").get("type").getAsString();
        } else {
            modType = null;
        }
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement jsonElement = JsonParser.parseString(getJson());
        return gson.toJson(jsonElement);
    }

    public String getJson() {
        return json;
    }

    public String getVersionName() {
        return versionName;
    }

    public int getVersionProtocol() {
        return versionProtocol;
    }

    public int getPlayerMax() {
        return playerMax;
    }

    public int getPlayerOnline() {
        return playerOnline;
    }

    public List<PlayerSample> getPlayerSample() {
        return playerSample;
    }

    public String getDescription() {
        return description;
    }

    public String getFavicon() {
        return favicon;
    }

    public String getModType() {
        return modType;
    }


    public static class PlayerSample {
        private final String name;
        private final String id;

        public PlayerSample(String name, String id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }
    }
}
