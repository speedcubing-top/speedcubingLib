package cubing.lib.velocity;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class TextBuilder {
    static TextComponent legacy(String s){
        return LegacyComponentSerializer.builder().extractUrls(Style.style().build()).build().deserialize(s);
    }
    private TextComponent builder = Component.text("");

    public TextBuilder() {
    }

    public TextBuilder str(String s) {
        this.builder = this.builder.append(legacy(s));
        return this;
    }

    public TextBuilder click(String s, ClickEvent c) {
        this.builder = this.builder.append(legacy(s).clickEvent(c));
        return this;
    }

    public TextBuilder hover(String s, HoverEvent<?> h) {
        this.builder = this.builder.append(legacy(s).hoverEvent(h));
        return this;
    }

    public TextBuilder both(String s, ClickEvent c, HoverEvent<?> h) {
        this.builder = this.builder.append(legacy(s).clickEvent(c).hoverEvent(h));
        return this;
    }

    public TextComponent build() {
        return this.builder;
    }
}
