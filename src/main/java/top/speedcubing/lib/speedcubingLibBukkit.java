package top.speedcubing.lib;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import top.speedcubing.lib.bukkit.CubingLibPlayer;
import top.speedcubing.lib.bukkit.entity.NPC;
import top.speedcubing.lib.bukkit.inventory.Glow;
import top.speedcubing.lib.bukkit.listeners.PlayerListener;
import top.speedcubing.lib.utils.Reflections;

public class speedcubingLibBukkit extends JavaPlugin {
    public static final boolean is1_8_8 = Bukkit.getVersion().contains("(MC: 1.8.8)");

    public void onEnable() {
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        if (is1_8_8) {
            CubingLibPlayer.init();
            Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
            Reflections.setClassField(Enchantment.class, "acceptingNew", true);
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Glow.glow = new Glow(100);
                Enchantment.registerEnchantment(Glow.glow);
            } catch (Exception e) {
            }
            Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
                try {
                    for (NPC n : NPC.all) {
                        if (n.isGravity()) {
                            n.entityPlayer.g(0, 0);
                            n.updateNpcLocation();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 0, 0);
        }
    }
}
