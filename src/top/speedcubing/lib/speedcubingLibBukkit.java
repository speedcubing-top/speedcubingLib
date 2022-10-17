package top.speedcubing.lib;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import top.speedcubing.lib.bukkit.CubingLibPlayer;
import top.speedcubing.lib.bukkit.inventory.Glow;
import top.speedcubing.lib.bukkit.listeners.PacketListener;
import top.speedcubing.lib.bukkit.listeners.PlayerListener;
import top.speedcubing.lib.utils.Reflections;

import java.io.File;

public class speedcubingLibBukkit extends JavaPlugin {
    public static boolean deletePlayerFile = false;
    public static final boolean is1_8_8 = Bukkit.getVersion().contains("(MC: 1.8.8)");

    public void onEnable() {
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getPluginManager().registerEvents(new PacketListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        if (is1_8_8) {
            CubingLibPlayer.init();
            Reflections.setClassField(Enchantment.class, "acceptingNew", true);
            try {
                Glow.glow = new Glow(100);
                Enchantment.registerEnchantment(Glow.glow);
            } catch (Exception exception) {
            }
        }
    }

    public void onDisable() {
        if (deletePlayerFile) {
            File index = new File(Bukkit.getWorlds().get(0).getWorldFolder() + "/playerdata");
            File index2 = new File(Bukkit.getWorlds().get(0).getWorldFolder() + "/stats");
            if (index.list() != null)
                for (String s : index.list())
                    new File(index.getPath(), s).delete();
            if (index2.list() != null)
                for (String s : index2.list())
                    new File(index2.getPath(), s).delete();
            index.delete();
            index2.delete();
        }
    }
}
