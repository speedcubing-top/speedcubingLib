package top.speedcubing.lib.minecraft.text;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.regex.Pattern;

public class MinecraftTextUtils {

    //from net.md_5.bungee.api.ChatColor
    public static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)§[0-9A-FK-OR]");


    //from net.md_5.bungee.api.ChatColor
    public static String removeColorCode(String input) {
        return input == null ? null : STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }

    //from net.md_5.bungee.api.ChatColor.translateAlternateColorCodes
    public static String colorCode(char altColorChar, String textToTranslate) {
        char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < b.length - 1; ++i) {
            if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
                b[i] = 167;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }

        return new String(b);
    }

    public static String componentToText(JsonObject o) {
        return componentToText(o, "", "");
    }

    private static String componentToText(JsonObject o, String s, String color) {
        if (o.has("color")) {
            color = "§" + toMinecraftColorCode(o.get("color").getAsString());
            s += color;
        }
        s += o.get("text").getAsString();
        if (o.has("extra"))
            for (JsonElement e : o.get("extra").getAsJsonArray())
                s = componentToText(e.getAsJsonObject(), s, color);
        return s;
    }


    //get the last color of the text
    public static char getLastColor(String str) {
        char c = getLastColorExact(str);
        return c == ' ' ? 'f' : c;
    }

    //get the last color of the text but return empty if there's no color found.
    public static char getLastColorExact(String str) {
        for (int i = str.length() - 1; i >= 0; i--)
            if (str.charAt(i) == '§')
                return str.charAt(i + 1);
        return ' ';
    }

    public static String toHexColorCode(String c) {
        switch (c.toLowerCase()) {
            case "0":
            case "§0":
            case "black":
                return "#000000";
            case "1":
            case "§1":
            case "dark_blue":
                return "#0000AA";
            case "2":
            case "§2":
            case "dark_green":
                return "#00AA00";
            case "3":
            case "§3":
            case "dark_aqua":
                return "#00AAAA";
            case "4":
            case "§4":
            case "dark_red":
                return "#AA0000";
            case "5":
            case "§5":
            case "dark_purple":
                return "#AA00AA";
            case "6":
            case "§6":
            case "gold":
                return "#FFAA00";
            case "7":
            case "§7":
            case "gray":
                return "#AAAAAA";
            case "8":
            case "§8":
            case "dark_gray":
                return "#555555";
            case "9":
            case "§9":
            case "blue":
                return "#5555FF";
            case "a":
            case "§a":
            case "green":
                return "#55FF55";
            case "b":
            case "§b":
            case "aqua":
                return "#55FFFF";
            case "c":
            case "§c":
            case "red":
                return "#FF5555";
            case "d":
            case "§d":
            case "light_purple":
                return "#FF55FF";
            case "e":
            case "§e":
            case "yellow":
                return "#FFFF55";
            case "f":
            case "§f":
            case "white":
                return "#FFFFFF";
        }
        return null;
    }

    public static String toMinecraftColorCode(String c) {
        switch (c.toLowerCase()) {
            case "black":
                return "0";
            case "dark_blue":
                return "1";
            case "dark_green":
                return "2";
            case "dark_aqua":
                return "3";
            case "dark_red":
                return "4";
            case "dark_purple":
                return "5";
            case "gold":
                return "6";
            case "gray":
                return "7";
            case "dark_gray":
                return "8";
            case "blue":
                return "9";
            case "green":
                return "a";
            case "aqua":
                return "b";
            case "red":
                return "c";
            case "light_purple":
                return "d";
            case "yellow":
                return "e";
            case "white":
                return "f";
        }
        return null;
    }
}
