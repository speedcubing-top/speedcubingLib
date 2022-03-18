package cubing.bungee;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;

import java.util.regex.Pattern;

public class TextComponentUtils {
    public static Pattern url = Pattern.compile("^(?:(https?)://)?([-\\w_\\.]{2,}\\.[a-z]{2,4})(/\\S*)?$");

    public static void reApplyURL(BaseComponent[] components) {
        for (BaseComponent c : components) {
            String s = c.toPlainText();
            if (s.endsWith("\n"))
                s = s.substring(0, s.length() - 1);
            if (url.matcher(s).matches())
                c.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, (s.startsWith("http") ? "" : "http://") + s));
        }
    }
}
