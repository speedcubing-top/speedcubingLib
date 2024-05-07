package top.speedcubing.lib.minecraft.text;

public class TextHoverEvent {

    public static TextHoverEvent showText(String s) {
        return new TextHoverEvent(s, (char) 7);
    }

    private final String s;
    final char b;

    public String getString() {
        return s;
    }

    public TextHoverEvent(String s, char b) {
        this.s = s;
        this.b = b;
    }
}
