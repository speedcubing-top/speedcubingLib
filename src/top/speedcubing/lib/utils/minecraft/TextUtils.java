package top.speedcubing.lib.utils.minecraft;

public class TextUtils {

    public static char getLastColor(String str) {
        for (int i = str.length() - 1; i >= 0; i--) {
            if (str.charAt(i) == '§')
                return str.charAt(i + 1);
        }
        return 'f';
    }

    public static String toHexColorCode(String c) {
        switch (c) {
            case "0":
            case "BLACK":
                return "#000000";
            case "1":
            case "DARK_BLUE":
                return "#0000AA";
            case "2":
            case "DARK_GREEN":
                return "#00AA00";
            case "3":
            case "DARK_AQUA":
                return "#00AAAA";
            case "4":
            case "DARK_RED":
                return "#AA0000";
            case "5":
            case "DARK_PURPLE":
                return "#AA00AA";
            case "6":
            case "GOLD":
                return "#FFAA00";
            case "7":
            case "GRAY":
                return "#AAAAAA";
            case "8":
            case "DARK_GRAY":
                return "#555555";
            case "9":
            case "BLUE":
                return "#5555FF";
            case "a":
            case "GREEN":
                return "#55FF55";
            case "b":
            case "AQUA":
                return "#55FFFF";
            case "c":
            case "RED":
                return "#FF5555";
            case "d":
            case "LIGHT_PURPLE":
                return "#FF55FF";
            case "e":
            case "YELLOW":
                return "#FFFF55";
            case "f":
            case "WHITE":
                return "#FFFFFF";
        }
        return null;
    }
}
