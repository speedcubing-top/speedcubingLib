package top.speedcubing.lib.minecraft.text;

public class TextClickEvent {

    public static TextClickEvent openURL(String s) {
        return new TextClickEvent(s, (char) 4);
    }


    public static TextClickEvent runCommand(String s) {
        return new TextClickEvent(s, (char) 5);
    }

    public static TextClickEvent suggestCommand(String s) {
        return new TextClickEvent(s, (char) 6);
    }

    private final String s;
    final char b;

    public String getString() {
        return s;
    }

    public TextClickEvent(String s, char b) {
        this.s = s;
        this.b = b;
    }
}
