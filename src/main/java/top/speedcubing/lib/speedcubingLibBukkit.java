package top.speedcubing.lib;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import top.speedcubing.lib.bukkit.CubingLibPlayer;
import top.speedcubing.lib.bukkit.entity.NPC;
import top.speedcubing.lib.bukkit.inventory.Glow;
import top.speedcubing.lib.bukkit.inventory.SignBuilder;
import top.speedcubing.lib.bukkit.listeners.PlayerListener;
import top.speedcubing.lib.utils.ReflectionUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class speedcubingLibBukkit extends JavaPlugin {
    public static final boolean is1_8_8 = Bukkit.getVersion().contains("(MC: 1.8.8)");
    public static final ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);

    public void onEnable() {
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        if (is1_8_8) {
            CubingLibPlayer.init();
            Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
            ReflectionUtils.setClassField(Enchantment.class, "acceptingNew", true);
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Glow.glow = new Glow(100);
                Enchantment.registerEnchantment(Glow.glow);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
                try {
                    for (NPC n : NPC.all) {
                        if (n.isGravity()) {
                            n.entityPlayer.g(0, 0);
                            n.updateNpcLocation();
                        }
                    }

                    Set<SignBuilder> toRemove = new HashSet<>();
                    for (SignBuilder b : SignBuilder.signs) {
                        if (System.currentTimeMillis() - b.getGeneratedTime() > b.getTimeout()) {
                            toRemove.add(b);
                        }
                    }
                    SignBuilder.signs.removeAll(toRemove);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 0, 1);
        }
    }
}
