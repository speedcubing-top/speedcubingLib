package top.speedcubing.lib;

import net.md_5.bungee.api.plugin.Plugin;
import org.bukkit.enchantments.Enchantment;
import top.speedcubing.lib.bukkit.inventory.Glow;

public class speedcubingLibBungee extends Plugin {
    public void onEnable() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        }
    }
}