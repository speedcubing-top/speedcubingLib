package top.speedcubing.lib.minecraft.text;

public class HoverEvent {

    public static HoverEvent showText(String s) {
        return new HoverEvent(s, (char) 7);
    }

    private final String s;
    private final char b;

    public String getString() {
        return s;
    }

    public char getB() {
        return b;
    }

    public HoverEvent(String s, char b) {
        this.s = s;
        this.b = b;
    }
}
