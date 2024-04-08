package top.speedcubing.lib.minecraft.text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import top.speedcubing.lib.utils.minecraft.TextUtils;

public class VelocityTextBuilder {
    private net.kyori.adventure.text.TextComponent builder = Component.text("");

    public net.kyori.adventure.text.TextComponent getComponent() {
        return builder;
    }

    private char lastcolor = 'f';

    public void str(String s) {
        builder = builder.append(legacy("§" + lastcolor + s));
        checkLastColor(s);
    }

    private void click(String s, TextClickEvent c) {
        builder = builder.append(legacy("§" + lastcolor + s).clickEvent(a(c)));
        checkLastColor(s);
    }

    private void hover(String s, TextHoverEvent h) {
        builder = builder.append(legacy("§" + lastcolor + s).hoverEvent(a(h)));
        checkLastColor(s);
    }

    private void both(String s, TextClickEvent c, TextHoverEvent h) {
        builder = builder.append(legacy("§" + lastcolor + s).clickEvent(a(c)).hoverEvent(a(h)));
        checkLastColor(s);
    }

    private net.kyori.adventure.text.event.ClickEvent a(TextClickEvent c) {
        switch (c.b) {
            case 4:
                return net.kyori.adventure.text.event.ClickEvent.openUrl(c.getString());
            case 5:
                return net.kyori.adventure.text.event.ClickEvent.runCommand(c.getString());
            case 6:
                return net.kyori.adventure.text.event.ClickEvent.suggestCommand(c.getString());
        }
        return null;
    }

    private net.kyori.adventure.text.event.HoverEvent<?> a(TextHoverEvent h) {
        switch (h.b) {
            case 7:
                return net.kyori.adventure.text.event.HoverEvent.showText(new TextBuilder().str(h.getString()).toVelo());
        }
        return null;
    }

    private net.kyori.adventure.text.TextComponent legacy(String s) {
        return LegacyComponentSerializer.builder().extractUrls(Style.style().build()).build().deserialize(s);
    }

    private void checkLastColor(String s) {
        char ch = TextUtils.getLastColorExact(s);
        lastcolor = ch == ' ' ? lastcolor : ch;
    }
}
