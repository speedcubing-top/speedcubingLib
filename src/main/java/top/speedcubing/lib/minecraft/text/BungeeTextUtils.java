package top.speedcubing.lib.minecraft.text;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import top.speedcubing.lib.utils.StringUtils;

public class BungeeTextUtils {

    public static TextComponent parse(String json) {
        BaseComponent[] b = ComponentSerializer.parse(json);
        if (b.length == 1)
            return (TextComponent) b[0];
        return new TextComponent(b);
    }

    public static String serialize(TextComponent component) {
        return ComponentSerializer.toString(component);
    }

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
