package top.speedcubing.lib.minecraft.text;

public class ClickEvent {

    public static ClickEvent openURL(String s) {
        return new ClickEvent(s, (byte) 4);
    }


    public static ClickEvent runCommand(String s) {
        return new ClickEvent(s, (byte) 5);
    }

    public static ClickEvent suggestCommand(String s) {
        return new ClickEvent(s, (byte) 6);
    }

    private final String s;
    private final byte b;

    public String getString() {
        return s;
    }

    public byte getB() {
        return b;
    }

    public ClickEvent(String s, byte b) {
        this.s = s;
        this.b = b;
    }
}
