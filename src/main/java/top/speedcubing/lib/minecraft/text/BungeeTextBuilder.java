package top.speedcubing.lib.minecraft.text;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

public class BungeeTextBuilder {
    TextComponent builder = new TextComponent();

    public BaseComponent[] parse(String json) {
        return ComponentSerializer.parse(json);
    }

    public String serialize() {
        return ComponentSerializer.toString(builder);
    }

    public BaseComponent[] getComponent() {
        return builder.getExtra().toArray(new BaseComponent[0]);
    }

    public BungeeTextBuilder str(String s) {
        this.builder.getExtra().addAll(Arrays.asList(TextComponent.fromLegacyText(s)));
        return this;
    }

    public BungeeTextBuilder click(String s, TextClickEvent c) {
        BaseComponent[] b = TextComponent.fromLegacyText(s);
        net.md_5.bungee.api.chat.ClickEvent clickEvent = a(c);
        for (BaseComponent a : b) {
            a.setClickEvent(clickEvent);
            builder.addExtra(a);
        }
        return this;
    }

    public BungeeTextBuilder hover(String s, TextHoverEvent h) {
        BaseComponent[] b = TextComponent.fromLegacyText(s);
        net.md_5.bungee.api.chat.HoverEvent hoverEvent = a(h);
        for (BaseComponent a : b) {
            a.setHoverEvent(hoverEvent);
            builder.addExtra(a);
        }
        return this;
    }

    public BungeeTextBuilder both(String s, TextClickEvent c, TextHoverEvent h) {
        BaseComponent[] b = TextComponent.fromLegacyText(s);
        net.md_5.bungee.api.chat.ClickEvent clickEvent = a(c);
        net.md_5.bungee.api.chat.HoverEvent hoverEvent = a(h);
        for (BaseComponent a : b) {
            a.setClickEvent(clickEvent);
            a.setHoverEvent(hoverEvent);
            builder.addExtra(a);
        }
        return this;
    }

    private net.md_5.bungee.api.chat.ClickEvent a(TextClickEvent c) {
        switch (c.b) {
            case 4:
                return new net.md_5.bungee.api.chat.ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.OPEN_URL, c.getString());
            case 5:
                return new net.md_5.bungee.api.chat.ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, c.getString());
            case 6:
                return new net.md_5.bungee.api.chat.ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, c.getString());
        }
        return null;
    }

    private net.md_5.bungee.api.chat.HoverEvent a(TextHoverEvent h) {
        switch (h.b) {
            case 7:
                return new net.md_5.bungee.api.chat.HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new BungeeTextBuilder().str(h.getString()).getComponent());
        }
        return null;
    }
}
