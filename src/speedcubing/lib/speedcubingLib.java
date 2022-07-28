package speedcubing.lib;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import speedcubing.lib.bukkit.inventory.Glow;
import speedcubing.lib.bukkit.listeners.PacketListener;
import speedcubing.lib.bukkit.listeners.PlayerListener;

import java.lang.reflect.Field;

public class speedcubingLib extends JavaPlugin {
    public static boolean is1_8_8;

    public void onEnable() {
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        is1_8_8 = Bukkit.getVersion().contains("(MC: 1.8.8)");
        Bukkit.getPluginManager().registerEvents(new PacketListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        if(is1_8_8)
        try {
            Field field = Enchantment.class.getDeclaredField("acceptingNew");
            field.setAccessible(true);
            field.set(field, true);
            Glow.glow = new Glow(100);
            Enchantment.registerEnchantment(Glow.glow);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
