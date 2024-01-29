package top.speedcubing.lib.bungee;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import top.speedcubing.lib.utils.StringUtils;

public class TextComponentUtils {
    public static void reApplyURL(BaseComponent[] components) {
        for (BaseComponent c : components) {
            String s = c.toPlainText();
            if (s.endsWith("\n"))
                s = s.substring(0, s.length() - 1);
            if (StringUtils.url.matcher(s).matches())
                c.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, (s.startsWith("http") ? "" : "http://") + s));
        }
    }
}
