package top.speedcubing.lib.velocity;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import top.speedcubing.lib.minecraft.text.ClickEvent;
import top.speedcubing.lib.minecraft.text.HoverEvent;
import top.speedcubing.lib.utils.minecraft.TextUtils;

public class TextBuilder {

    private net.kyori.adventure.text.event.ClickEvent a(ClickEvent c) {
        switch (c.getB()) {
            case 4:
                return net.kyori.adventure.text.event.ClickEvent.openUrl(c.getString());
            case 5:
                return net.kyori.adventure.text.event.ClickEvent.runCommand(c.getString());
            case 6:
                return net.kyori.adventure.text.event.ClickEvent.suggestCommand(c.getString());
        }
        return null;
    }

    private net.kyori.adventure.text.event.HoverEvent<?> a(HoverEvent h) {
        switch (h.getB()) {
            case 7:
                return net.kyori.adventure.text.event.HoverEvent.showText(new TextBuilder().str(h.getString()).build());
        }
        return null;
    }

    private TextComponent legacy(String s) {
        return LegacyComponentSerializer.builder().extractUrls(Style.style().build()).build().deserialize(s);
    }

    private String last;
    private char lastcolor = 'f';
    private TextComponent builder = Component.text("");

    public TextBuilder() {
    }

    public TextBuilder str(String s) {
        serial += (s + ((char) 0));
        builder = builder.append(legacy("§" + lastcolor + s));
        return add(s);
    }

    public TextBuilder click(String s, ClickEvent c) {
        serial += (s + c.getB() + c.getString() + ((char) 1));
        builder = builder.append(legacy("§" + lastcolor + s).clickEvent(a(c)));
        return add(s);
    }

    public TextBuilder hover(String s, HoverEvent h) {
        serial += (s + h.getB() + h.getString() + ((char) 2));
        builder = builder.append(legacy("§" + lastcolor + s).hoverEvent(a(h)));
        return add(s);
    }

    public TextBuilder both(String s, ClickEvent c, HoverEvent h) {
        serial += (s + c.getB() + c.getString() + h.getB() + h.getString() + ((char) 3));
        builder = builder.append(legacy("§" + lastcolor + s).clickEvent(a(c)).hoverEvent(a(h)));
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

    private String serial = "";


    public byte[] getSerial() {
        return serial.getBytes();
    }

    public static TextBuilder unserial(byte[] b) {
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
