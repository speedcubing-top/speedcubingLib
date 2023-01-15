package top.speedcubing.lib.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;

import java.util.Collections;

public class TabCompleteUtils {

    public static void registerEmptyTabComplete(String... commands) {
        for (String s : commands) {
            PluginCommand command = Bukkit.getPluginCommand(s);
            if (command != null)
                command.setTabCompleter((a, b, c, d) -> Collections.emptyList());
        }
    }
}
