package cubing.lib.utils;

public class TextUtils {

    public static char getLastColor(String str) {
        for (int i = str.length() - 1; i >= 0; i--) {
            if (str.charAt(i) == '§')
                return str.charAt(i + 1);
        }
        return 'f';
    }
}
