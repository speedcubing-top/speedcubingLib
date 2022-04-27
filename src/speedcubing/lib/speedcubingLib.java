package speedcubing.lib;

import speedcubing.lib.bukkit.Glow;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class speedcubingLib extends JavaPlugin {
    public void onEnable() {
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
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
