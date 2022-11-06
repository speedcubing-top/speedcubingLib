package top.speedcubing.lib.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

import java.util.Collections;

public class TabCompleteUtils {
    static final TabCompleter completer = (commandSender, command, s1, strings) -> Collections.emptyList();

    public static void registerEmptyTabComplete(String... commands) {
        for (String s : commands) {
            PluginCommand command = Bukkit.getPluginCommand(s);
            if (command != null)
                command.setTabCompleter(completer);
        }
    }
}
