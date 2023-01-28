package top.speedcubing.lib.bungee;


import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextBuilder {
    private final List<BaseComponent> builder = new ArrayList<>();

    public TextBuilder str(String s) {
        this.builder.addAll(Arrays.asList(TextComponent.fromLegacyText(s)));
        return this;
    }

    public TextBuilder click(String s, ClickEvent.Action clickevent, String clickeventstring) {
        BaseComponent[] b = TextComponent.fromLegacyText(s);
        ClickEvent c = new ClickEvent(clickevent, clickeventstring);
        for (BaseComponent a : b) {
            a.setClickEvent(c);
            builder.add(a);
        }
        return this;
    }

    public TextBuilder hover(String s, HoverEvent.Action hoverevent, BaseComponent[] hovereventstring) {
        BaseComponent[] b = TextComponent.fromLegacyText(s);
        HoverEvent h = new HoverEvent(hoverevent, hovereventstring);
        for (BaseComponent a : b) {
            a.setHoverEvent(h);
            builder.add(a);
        }
        return this;
    }

    public TextBuilder both(String s, ClickEvent.Action clickevent, String clickeventstring, HoverEvent.Action hoverevent, BaseComponent[] hovereventstring) {
        BaseComponent[] b = TextComponent.fromLegacyText(s);
        ClickEvent c = new ClickEvent(clickevent, clickeventstring);
        HoverEvent h = new HoverEvent(hoverevent, hovereventstring);
        for (BaseComponent a : b) {
            a.setClickEvent(c);
            a.setHoverEvent(h);
            builder.add(a);
        }
        return this;
    }

    public BaseComponent[] build() {
        return builder.toArray(new BaseComponent[0]);
    }
}
