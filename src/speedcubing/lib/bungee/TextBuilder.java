package speedcubing.lib.bungee;


import net.md_5.bungee.api.chat.*;

public class TextBuilder {
    private final ComponentBuilder builder;

    public TextBuilder() {
        this.builder = new ComponentBuilder();
    }

    public TextBuilder str(String s) {
        this.builder.append(TextComponent.fromLegacyText(s));
        return this;
    }

    public TextBuilder click(String s, ClickEvent c) {
        BaseComponent[] b = TextComponent.fromLegacyText(s);
        for (BaseComponent a : b) {
            a.setClickEvent(c);
        }
        this.builder.append(b);
        return this;
    }

    public TextBuilder hover(String s, HoverEvent h) {
        BaseComponent[] b = TextComponent.fromLegacyText(s);
        for (BaseComponent a : b) {
            a.setHoverEvent(h);
        }
        this.builder.append(b);
        return this;
    }

    public TextBuilder both(String s, ClickEvent c, HoverEvent h) {
        BaseComponent[] b = TextComponent.fromLegacyText(s);
        for (BaseComponent a : b) {
            a.setHoverEvent(h);
            a.setClickEvent(c);
        }
        this.builder.append(b);
        return this;
    }

    public BaseComponent[] build() {
        return this.builder.create();
    }
}
