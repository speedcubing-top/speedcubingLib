package top.speedcubing.lib.api.mojang;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Base64;

public class ProfileTexture {
    private final long timeStamp;
    private final String url;

    public ProfileTexture(String value) {
        String decoded = new String(Base64.getDecoder().decode(value));
        JsonObject object = JsonParser.parseString(decoded).getAsJsonObject();
        timeStamp = object.get("timestamp").getAsLong();
        url = object.getAsJsonObject("textures").getAsJsonObject("SKIN").get("url").getAsString();
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getUrl() {
        return url;
    }
}
