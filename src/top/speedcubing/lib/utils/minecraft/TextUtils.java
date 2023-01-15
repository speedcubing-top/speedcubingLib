package top.speedcubing.lib.utils.minecraft;

public class TextUtils {

    //get the last color of the text
    public static char getLastColor(String str) {
        for (int i = str.length() - 1; i >= 0; i--) {
            if (str.charAt(i) == '§')
                return str.charAt(i + 1);
        }
        return 'f';
    }

    //get the last color of the text but return empty if there's no color found.
    public static char getLastColorExact(String str) {
        for (int i = str.length() - 1; i >= 0; i--) {
            if (str.charAt(i) == '§')
                return str.charAt(i + 1);
        }
        return ' ';
    }

    public static String toHexColorCode(String c) {
        switch (c.toLowerCase()) {
            case "0":
            case "black":
                return "#000000";
            case "1":
            case "dark_blue":
                return "#0000AA";
            case "2":
            case "dark_green":
                return "#00AA00";
            case "3":
            case "dark_aqua":
                return "#00AAAA";
            case "4":
            case "dark_red":
                return "#AA0000";
            case "5":
            case "dark_purple":
                return "#AA00AA";
            case "6":
            case "gold":
                return "#FFAA00";
            case "7":
            case "gray":
                return "#AAAAAA";
            case "8":
            case "dark_gray":
                return "#555555";
            case "9":
            case "blue":
                return "#5555FF";
            case "a":
            case "green":
                return "#55FF55";
            case "b":
            case "aqua":
                return "#55FFFF";
            case "c":
            case "red":
                return "#FF5555";
            case "d":
            case "light_purple":
                return "#FF55FF";
            case "e":
            case "yellow":
                return "#FFFF55";
            case "f":
            case "white":
                return "#FFFFFF";
        }
        return null;
    }
}
