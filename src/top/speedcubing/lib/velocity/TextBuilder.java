package top.speedcubing.lib.velocity;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import top.speedcubing.lib.utils.minecraft.TextUtils;

public class TextBuilder {
    static TextComponent legacy(String s) {
        return LegacyComponentSerializer.builder().extractUrls(Style.style().build()).build().deserialize(s);
    }

    private String last;
    private char lastcolor = 'f';
    private TextComponent builder = Component.text("");

    public TextBuilder() {
    }

    public TextBuilder str(String s) {
        builder = builder.append(legacy("§" + lastcolor + s));
        return add(s);
    }

    public TextBuilder click(String s, ClickEvent c) {
        builder = builder.append(legacy("§" + lastcolor + s).clickEvent(c));
        return add(s);
    }

    public TextBuilder hover(String s, HoverEvent<?> h) {
        builder = builder.append(legacy("§" + lastcolor + s).hoverEvent(h));
        return add(s);
    }

    public TextBuilder both(String s, ClickEvent c, HoverEvent<?> h) {
        builder = builder.append(legacy("§" + lastcolor + s).clickEvent(c).hoverEvent(h));
        return add(s);
    }

    TextBuilder add(String s) {
        last += s;
        char ch = TextUtils.getLastColorExact(last);
        if (ch != ' ')
            lastcolor = ch;
        return this;
    }

    public TextComponent build() {
        return builder;
    }
}
