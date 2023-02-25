package top.speedcubing.lib.bungee;


import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import top.speedcubing.lib.minecraft.text.ClickEvent;
import top.speedcubing.lib.minecraft.text.HoverEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextBuilder {
    private final List<BaseComponent> builder = new ArrayList<>();

    private net.md_5.bungee.api.chat.ClickEvent a(ClickEvent c) {
        switch (c.getB()) {
            case 4:
                return new net.md_5.bungee.api.chat.ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.OPEN_URL, c.getString());
            case 5:
                return new net.md_5.bungee.api.chat.ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, c.getString());
            case 6:
                return new net.md_5.bungee.api.chat.ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, c.getString());
        }
        return null;
    }

    private net.md_5.bungee.api.chat.HoverEvent a(HoverEvent h) {
        switch (h.getB()) {
            case 7:
                return new net.md_5.bungee.api.chat.HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new TextBuilder().str(h.getString()).build());
        }
        return null;
    }

    public TextBuilder str(String s) {
        serial.append(s).append((char) 0);
        this.builder.addAll(Arrays.asList(TextComponent.fromLegacyText(s)));
        return this;
    }

    public TextBuilder click(String s, ClickEvent c) {
        serial.append(s).append(c.getB()).append(c.getString()).append((char) 1);
        BaseComponent[] b = TextComponent.fromLegacyText(s);
        net.md_5.bungee.api.chat.ClickEvent clickEvent = a(c);
        for (BaseComponent a : b) {
            a.setClickEvent(clickEvent);
            builder.add(a);
        }
        return this;
    }

    public TextBuilder hover(String s, HoverEvent h) {
        serial.append(s).append(h.getB()).append(h.getString()).append((char) 2);
        BaseComponent[] b = TextComponent.fromLegacyText(s);
        net.md_5.bungee.api.chat.HoverEvent hoverEvent = a(h);
        for (BaseComponent a : b) {
            a.setHoverEvent(hoverEvent);
            builder.add(a);
        }
        return this;
    }

    public TextBuilder both(String s, ClickEvent c, HoverEvent h) {
        serial.append(s).append(c.getB()).append(c.getString()).append(h.getB()).append(h.getString()).append((char) 3);
        BaseComponent[] b = TextComponent.fromLegacyText(s);
        net.md_5.bungee.api.chat.ClickEvent clickEvent = a(c);
        net.md_5.bungee.api.chat.HoverEvent hoverEvent = a(h);
        for (BaseComponent a : b) {
            a.setClickEvent(clickEvent);
            a.setHoverEvent(hoverEvent);
            builder.add(a);
        }
        return this;
    }

    public BaseComponent[] build() {
        return builder.toArray(new BaseComponent[0]);
    }

    private final StringBuilder serial = new StringBuilder();

    public byte[] getSerial() {
        return serial.toString().getBytes();
    }

    public static TextBuilder unSerialize(byte[] b) {
        TextBuilder builder = new TextBuilder();
        byte lastclick = -1;
        byte lasthover = -1;
        StringBuilder s = new StringBuilder();
        String s2 = "";
        String s3 = "";
        for (byte value : b) {
            switch (value) {
                case 0:
                    builder.str(s.toString());
                    s = new StringBuilder();
                    break;
                case 1:
                    builder.click(s2, new ClickEvent(s.toString(), lastclick));
                    s = new StringBuilder();
                    s2 = "";
                    break;
                case 2:
                    builder.hover(s3, new HoverEvent(s.toString(), lasthover));
                    s = new StringBuilder();
                    s3 = "";
                    break;
                case 3:
                    builder.both(s2, new ClickEvent(s3, lastclick), new HoverEvent(s.toString(), lasthover));
                    s = new StringBuilder();
                    s2 = "";
                    s3 = "";
                    break;
                case 4:
                case 5:
                    lastclick = value;
                    s2 = s.toString();
                    s = new StringBuilder();
                    break;
                case 6:
                    lasthover = value;
                    s3 = s.toString();
                    s = new StringBuilder();
                    break;
                default:
                    s.append((char) value);
                    break;
            }
        }
        return builder;
    }
}
