package top.speedcubing.lib.utils;

import java.util.regex.Pattern;

public class StringUtils {

    //match an url
    public static Pattern url = Pattern.compile("\"(?:(https?)://)?([-\\\\w_.]+\\\\.\\\\w{2,})(/\\\\S*)?\"");

    public static String encodeUnicode(String s) {
        StringBuilder unicode = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            unicode.append("\\u").append(Integer.toHexString(s.charAt(i)));
        }
        return unicode.toString();
    }

    public static String decodeUnicode(String unicode) {
        StringBuilder text = new StringBuilder();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            text.append((char) Integer.parseInt(hex[i], 16));
        }
        return text.toString();
    }

    //get the index of the Nth c from str
    //example: indexOf("ababa","b",2) = 3
    public static int indexOf(String str, String c, int N) {
        int result = 0;
        int index;
        for (int i = 0; i < N; i++) {
            index = str.indexOf(c) + 1;
            result += index;
            str = str.substring(index);
        }
        return result - 1;
    }

    public static String repeat(String s, int count) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < count; i++)
            b.append(s);
        return b.toString();
    }

    public static boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }
}
