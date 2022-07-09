package speedcubing.lib;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import speedcubing.lib.bukkit.inventory.Glow;
import speedcubing.lib.bukkit.listeners.PacketListener;
import speedcubing.lib.bukkit.listeners.PlayerListener;

import java.lang.reflect.Field;

public class speedcubingLib extends JavaPlugin {
    public void onEnable() {
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getPluginManager().registerEvents(new PacketListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        String ver = Bukkit.getVersion();
        System.out.println(ver);
        try {
            Field field = Enchantment.class.getDeclaredField("acceptingNew");
            field.setAccessible(true);
            field.set(field, true);
            Glow.glow = new Glow(100);
            Enchantment.registerEnchantment(Glow.glow);
        } catch (Exception ignored) {
        }
    }
}
