package top.speedcubing.lib.minecraft.text;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import top.speedcubing.lib.utils.minecraft.TextUtils;

public class TextBuilder {

    private final StringBuilder serial = new StringBuilder();

    public String getSerial() {
        return serial.toString();
    }

    public BaseComponent[] toBungee() {
        return new BungeeText(getSerial()).get();
    }

    public net.kyori.adventure.text.TextComponent toVelo() {
        return new VeloBuilder(getSerial()).get();
    }

    public TextBuilder str(String s) {
        serial.append(s).append((char) 0);
        return this;
    }

    public TextBuilder click(String s, ClickEvent c) {
        serial.append(s).append(c.b).append(c.getString()).append((char) 1);
        return this;
    }

    public TextBuilder hover(String s, HoverEvent h) {
        serial.append(s).append(h.b).append(h.getString()).append((char) 2);
        return this;
    }

    public TextBuilder both(String s, ClickEvent c, HoverEvent h) {
        serial.append(s).append(c.b).append(c.getString()).append(h.b).append(h.getString()).append((char) 3);
        return this;
    }

    public static TextBuilder unSerialize(String serial) {
        TextBuilder builder = new TextBuilder();

        char lastclick = (char) -1;
        char lasthover = (char) -1;
        StringBuilder s = new StringBuilder();
        String s2 = "";
        String s3 = "";
        for (char value : serial.toCharArray()) {
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
                case 6:
                    lastclick = value;
                    s2 = s.toString();
                    s = new StringBuilder();
                    break;
                case 7:
                    lasthover = value;
                    s3 = s.toString();
                    s = new StringBuilder();
                    break;
                default:
                    s.append(value);
                    break;
            }
        }
        return builder;
    }


    static class BungeeText {
        List<BaseComponent> builder = new ArrayList<>();

        public BaseComponent[] get() {
            return builder.toArray(new BaseComponent[0]);
        }

        private net.md_5.bungee.api.chat.ClickEvent a(ClickEvent c) {
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

        private net.md_5.bungee.api.chat.HoverEvent a(HoverEvent h) {
            switch (h.b) {
                case 7:
                    return new net.md_5.bungee.api.chat.HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new TextBuilder().str(h.getString()).toBungee());
            }
            return null;
        }

        public void str(String s) {
            this.builder.addAll(Arrays.asList(TextComponent.fromLegacyText(s)));
        }

        private void click(String s, ClickEvent c) {
            BaseComponent[] b = TextComponent.fromLegacyText(s);
            net.md_5.bungee.api.chat.ClickEvent clickEvent = a(c);
            for (BaseComponent a : b) {
                a.setClickEvent(clickEvent);
                builder.add(a);
            }
        }

        private void hover(String s, HoverEvent h) {
            BaseComponent[] b = TextComponent.fromLegacyText(s);
            net.md_5.bungee.api.chat.HoverEvent hoverEvent = a(h);
            for (BaseComponent a : b) {
                a.setHoverEvent(hoverEvent);
                builder.add(a);
            }
        }

        private void both(String s, ClickEvent c, HoverEvent h) {
            BaseComponent[] b = TextComponent.fromLegacyText(s);
            net.md_5.bungee.api.chat.ClickEvent clickEvent = a(c);
            net.md_5.bungee.api.chat.HoverEvent hoverEvent = a(h);
            for (BaseComponent a : b) {
                a.setClickEvent(clickEvent);
                a.setHoverEvent(hoverEvent);
                builder.add(a);
            }
        }

        private BungeeText(String serial) {
            char lastclick = (char) -1;
            char lasthover = (char) -1;
            StringBuilder s = new StringBuilder();
            String s2 = "";
            String s3 = "";
            for (char value : serial.toCharArray()) {
                switch (value) {
                    case 0:
                        str(s.toString());
                        s = new StringBuilder();
                        break;
                    case 1:
                        click(s2, new ClickEvent(s.toString(), lastclick));
                        s = new StringBuilder();
                        s2 = "";
                        break;
                    case 2:
                        hover(s3, new HoverEvent(s.toString(), lasthover));
                        s = new StringBuilder();
                        s3 = "";
                        break;
                    case 3:
                        both(s2, new ClickEvent(s3, lastclick), new HoverEvent(s.toString(), lasthover));
                        s = new StringBuilder();
                        s2 = "";
                        s3 = "";
                        break;
                    case 4:
                    case 5:
                    case 6:
                        lastclick = value;
                        s2 = s.toString();
                        s = new StringBuilder();
                        break;
                    case 7:
                        lasthover = value;
                        s3 = s.toString();
                        s = new StringBuilder();
                        break;
                    default:
                        s.append(value);
                        break;
                }
            }
        }

    }

    static class VeloBuilder {
        private net.kyori.adventure.text.TextComponent builder = Component.text("");

        public net.kyori.adventure.text.TextComponent get() {
            return builder;
        }

        private net.kyori.adventure.text.event.ClickEvent a(ClickEvent c) {
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

        private net.kyori.adventure.text.event.HoverEvent<?> a(HoverEvent h) {
            switch (h.b) {
                case 7:
                    return net.kyori.adventure.text.event.HoverEvent.showText(new TextBuilder().str(h.getString()).toVelo());
            }
            return null;
        }

        private net.kyori.adventure.text.TextComponent legacy(String s) {
            return LegacyComponentSerializer.builder().extractUrls(Style.style().build()).build().deserialize(s);
        }

        private char lastcolor = 'f';

        private void str(String s) {
            builder = builder.append(legacy("§" + lastcolor + s));
            checkLastColor(s);
        }

        private void click(String s, ClickEvent c) {
            builder = builder.append(legacy("§" + lastcolor + s).clickEvent(a(c)));
            checkLastColor(s);
        }

        private void hover(String s, HoverEvent h) {
            builder = builder.append(legacy("§" + lastcolor + s).hoverEvent(a(h)));
            checkLastColor(s);
        }

        private void both(String s, ClickEvent c, HoverEvent h) {
            builder = builder.append(legacy("§" + lastcolor + s).clickEvent(a(c)).hoverEvent(a(h)));
            checkLastColor(s);
        }

        private void checkLastColor(String s) {
            char ch = TextUtils.getLastColorExact(s);
            lastcolor = ch == ' ' ? lastcolor : ch;
        }

        private VeloBuilder(String serial) {
            char lastclick = (char) -1;
            char lasthover = (char) -1;
            StringBuilder s = new StringBuilder();
            String s2 = "";
            String s3 = "";
            for (char value : serial.toCharArray()) {
                switch (value) {
                    case 0:
                        str(s.toString());
                        s = new StringBuilder();
                        break;
                    case 1:
                        click(s2, new ClickEvent(s.toString(), lastclick));
                        s = new StringBuilder();
                        s2 = "";
                        break;
                    case 2:
                        hover(s3, new HoverEvent(s.toString(), lasthover));
                        s = new StringBuilder();
                        s3 = "";
                        break;
                    case 3:
                        both(s2, new ClickEvent(s3, lastclick), new HoverEvent(s.toString(), lasthover));
                        s = new StringBuilder();
                        s2 = "";
                        s3 = "";
                        break;
                    case 4:
                    case 5:
                    case 6:
                        lastclick = value;
                        s2 = s.toString();
                        s = new StringBuilder();
                        break;
                    case 7:
                        lasthover = value;
                        s3 = s.toString();
                        s = new StringBuilder();
                        break;
                    default:
                        s.append(value);
                        break;
                }
            }
        }
    }
}
