package top.speedcubing.lib.velocity;

import top.speedcubing.lib.utils.minecraft.TextUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

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
        last += s;
        this.builder = this.builder.append(legacy("§" + lastcolor + s));
        lastcolor = TextUtils.getLastColor(last);
        return this;
    }

    public TextBuilder click(String s, ClickEvent c) {
        last += s;
        this.builder = this.builder.append(legacy("§" + lastcolor + s).clickEvent(c));
        return this;
    }

    public TextBuilder hover(String s, HoverEvent<?> h) {
        last += s;
        this.builder = this.builder.append(legacy("§" + lastcolor + s).hoverEvent(h));
        return this;
    }

    public TextBuilder both(String s, ClickEvent c, HoverEvent<?> h) {
        last += s;
        this.builder = this.builder.append(legacy("§" + lastcolor + s).clickEvent(c).hoverEvent(h));
        return this;
    }

    public TextComponent build() {
        return this.builder;
    }
}
