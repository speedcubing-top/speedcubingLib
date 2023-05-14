package top.speedcubing.lib.minecraft.text;

public class ClickEvent {

    public static ClickEvent openURL(String s) {
        return new ClickEvent(s, (char) 4);
    }


    public static ClickEvent runCommand(String s) {
        return new ClickEvent(s, (char) 5);
    }

    public static ClickEvent suggestCommand(String s) {
        return new ClickEvent(s, (char) 6);
    }

    private final String s;
    private final char b;

    public String getString() {
        return s;
    }

    public char getB() {
        return b;
    }

    public ClickEvent(String s, char b) {
        this.s = s;
        this.b = b;
    }
}
