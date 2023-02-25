package top.speedcubing.lib.minecraft.text;

public class HoverEvent {

    public static HoverEvent showText(String s) {
        return new HoverEvent(s, (byte) 4);
    }

    private final String s;
    private final byte b;

    public String getString() {
        return s;
    }

    public byte getB() {
        return b;
    }

    public HoverEvent(String s, byte b) {
        this.s = s;
        this.b = b;
    }
}
