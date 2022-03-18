package cubing;

import cubing.bukkit.Glow;
import cubing.bukkit.PacketListner;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class speedcubingLib extends JavaPlugin {
    public static Enchantment glow;

    public void onEnable() {
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getPluginManager().registerEvents(new PacketListner(), this);
        try {
            Field field = Enchantment.class.getDeclaredField("acceptingNew");
            field.setAccessible(true);
            field.set(null, true);
            glow = new Glow(100);
            Enchantment.registerEnchantment(glow);
        } catch (Exception ignored) {
        }
    }
}
