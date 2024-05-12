package top.speedcubing.lib.utils;

import java.util.Arrays;
import org.fusesource.jansi.Ansi;

public class Console {
    public static void printColor(String message) {
        Ansi reset = Ansi.ansi().a(Ansi.Attribute.RESET);
        System.out.print(message
                .replace("§0", reset.fg(Ansi.Color.BLACK).boldOff().toString())
                .replace("§1", reset.fg(Ansi.Color.BLUE).boldOff().toString())
                .replace("§2", reset.fg(Ansi.Color.GREEN).boldOff().toString())
                .replace("§3", reset.fg(Ansi.Color.CYAN).boldOff().toString())
                .replace("§4", reset.fg(Ansi.Color.RED).boldOff().toString())
                .replace("§5", reset.fg(Ansi.Color.MAGENTA).boldOff().toString())
                .replace("§6", reset.fg(Ansi.Color.YELLOW).boldOff().toString())
                .replace("§7", reset.fg(Ansi.Color.WHITE).boldOff().toString())
                .replace("§8", reset.fg(Ansi.Color.BLACK).bold().toString())
                .replace("§9", reset.fg(Ansi.Color.BLUE).bold().toString())
                .replace("§a", reset.fg(Ansi.Color.GREEN).bold().toString())
                .replace("§b", reset.fg(Ansi.Color.CYAN).bold().toString())
                .replace("§c", reset.fg(Ansi.Color.RED).bold().toString())
                .replace("§d", reset.fg(Ansi.Color.MAGENTA).bold().toString())
                .replace("§e", reset.fg(Ansi.Color.YELLOW).bold().toString())
                .replace("§f", reset.fg(Ansi.Color.WHITE).bold().toString())
                .replace("§k", Ansi.ansi().a(Ansi.Attribute.BLINK_SLOW).toString())
                .replace("§l", Ansi.ansi().a(Ansi.Attribute.UNDERLINE_DOUBLE).toString())
                .replace("§m", Ansi.ansi().a(Ansi.Attribute.STRIKETHROUGH_ON).toString())
                .replace("§n", Ansi.ansi().a(Ansi.Attribute.UNDERLINE).toString())
                .replace("§o", Ansi.ansi().a(Ansi.Attribute.ITALIC).toString())
                .replace("§r", reset.toString()) + reset
        );
    }

    //convert ansi string to plain string
    public static String removeAnsi(String str) {
        return str.replaceAll("\u001B\\[[;\\d]*m", "");
    }
}
