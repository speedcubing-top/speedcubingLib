package top.speedcubing.lib.utils;

import java.util.regex.Pattern;

public class RegexUtils {

    public static String replaceAll(String pattern, String input, String replace) {
        return replaceAll(Pattern.compile(pattern), input, replace);
    }

    public static String replaceAll(Pattern pattern, String input, String replace) {
        return pattern.matcher(input).replaceAll(replace);
    }
}
