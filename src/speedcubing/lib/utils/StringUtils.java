package speedcubing.lib.utils;

import java.util.regex.Pattern;

public class StringUtils {
    public static Pattern url = Pattern.compile("^(?:(https?)://)?([-\\w_\\.]{2,}\\.[a-z]{2,4})(/\\S*)?$");
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
}
