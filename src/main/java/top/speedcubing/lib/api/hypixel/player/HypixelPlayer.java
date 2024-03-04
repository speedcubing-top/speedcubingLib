package top.speedcubing.lib.api.hypixel.player;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import top.speedcubing.lib.api.hypixel.HypixelLib;
import top.speedcubing.lib.api.hypixel.stats.BedwarsStats;

public class HypixelPlayer {
    private final JsonObject player;

    public static HypixelPlayer get(UUID uuid) {
        return get(uuid.toString());
    }

    public static HypixelPlayer get(String uuid) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://api.hypixel.net/player?key=" + HypixelLib.key + "&uuid=" + uuid).openConnection();
            if (connection.getResponseCode() == 200) {
                JsonObject object = new JsonParser().parse(new InputStreamReader(connection.getInputStream())).getAsJsonObject();
                return object == null ? null : new HypixelPlayer(object);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private HypixelPlayer(JsonObject obj) {
        player = obj == null ? null : obj.getAsJsonObject().getAsJsonObject("player");
    }

    public BedwarsStats getBedwars() {
        JsonObject object = player.getAsJsonObject("stats").getAsJsonObject("Bedwars");
        return object != null ? new BedwarsStats(object) : null;
    }

    public JsonObject getPlayer() {
        return player;
    }

    public String getDisplayName() {
        return player.get("displayname").getAsString();
    }

    public String[] getRank() {
        String prefix;
        boolean specialcolor = false;
        if (player.has("prefix")) {
            specialcolor = true;
            prefix = player.get("prefix").getAsString();
        } else if (player.has("rank")) {
            prefix = player.get("rank").getAsString();
        } else prefix = "NORMAL";
        if (prefix.equals("NORMAL")) {
            if (player.has("monthlyPackageRank"))
                prefix = player.get("monthlyPackageRank").getAsString().equals("NONE") ? "[MVP+]" : "[MVP++]";
            else if (player.has("newPackageRank"))
                prefix = player.get("newPackageRank").getAsString();
            else if (player.has("packageRank"))
                prefix = player.get("packageRank").getAsString();
        }
        StringBuilder color = new StringBuilder();
        if (specialcolor) {
            StringBuilder newPrefix = new StringBuilder();
            boolean hasColor = false;
            boolean wasCode = false;
            for (int i = 0; i < prefix.length(); i++) {
                if (prefix.charAt(i) == '§') {
                    i += 1;
                    color.append(prefix.charAt(i)).append(" ");
                    hasColor = true;
                    wasCode = true;
                } else {
                    if (!hasColor) {
                        color.append("f ");
                    } else if (!wasCode) {
                        color.append(color.substring(color.length() - 2));
                    }
                    newPrefix.append(prefix.charAt(i));
                    wasCode = false;
                }
            }
            prefix = newPrefix + " ";
        } else switch (prefix) {
            case "VIP":
                color = new StringBuilder("a a a a a");
                prefix = "[VIP] ";
                break;
            case "MVP":
                color = new StringBuilder("b b b b b");
                prefix = "[MVP] ";
                break;
            case "VIP_PLUS":
                color = new StringBuilder("a a a a 6 a");
                prefix = "[VIP+] ";
                break;
            case "ADMIN":
                color = new StringBuilder("c c c c c c c");
                prefix = "[ADMIN] ";
                break;
            case "YOUTUBER":
                color = new StringBuilder("c f f f f f f f c");
                prefix = "[YOUTUBE] ";
                break;
            case "NORMAL":
                color = new StringBuilder("7");
                prefix = "";
                break;
            case "[MVP++]":
                String z = player.has("rankPlusColor") ? player.get("rankPlusColor").getAsString() : "c";
                color = new StringBuilder(!(player.has("monthlyRankColor")) || player.get("monthlyRankColor").getAsString().equals("GOLD") ? ("6 6 6 6 " + z + " " + z + " 6") : ("b b b b " + z + " " + z + " b"));
                prefix = "[MVP++] ";
                break;
            case "MVP_PLUS":
            case "[MVP+]":
                color = new StringBuilder("b b b b " + (player.has("rankPlusColor") ? player.get("rankPlusColor").getAsString() : "c") + " b");
                prefix = "[MVP+] ";
                break;
        }
        return new String[]{color.toString(), prefix};
    }
}
