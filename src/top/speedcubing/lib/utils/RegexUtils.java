package top.speedcubing.lib.utils;

import java.util.regex.Pattern;

public class RegexUtils {

    public static String replaceAll(String input, String pattern, String replace) {
        return replaceAll(input, Pattern.compile(pattern), replace);
    }

    public static String replaceAll(String input, Pattern pattern, String replace) {
        return pattern.matcher(input).replaceAll(replace);
    }
}
