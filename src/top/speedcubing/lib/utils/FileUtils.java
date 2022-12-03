package top.speedcubing.lib.utils;

public class FileUtils {
    public static String getExtension(String s) {
        int index = s.lastIndexOf(46);
        index = Math.max(s.lastIndexOf(47), s.lastIndexOf(92)) > index ? -1 : index;
        return index == -1 ? "" : s.substring(index + 1);
    }
}
