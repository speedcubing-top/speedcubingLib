package top.speedcubing.lib.utils.SQL;

public class SQLUtils {
    public static String validateString(String data) {
        return "'" + data.replace("'", "\\'") + "'";
    }
}
