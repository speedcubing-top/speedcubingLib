package cubing.lib.bukkit;

import cubing.lib.utils.Reflections;
import net.minecraft.server.v1_8_R3.GameRules;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

public class WorldUtils {
    public static void clearGameRules(World world) {
        GameRules m = new GameRules();
        TreeMap<String, ?> map = (TreeMap<String, ?>) Reflections.getField(((CraftWorld) world).getHandle().getGameRules(), "a");
        Set<String> remove = new HashSet<>();
        map.forEach((k, v) -> {
            if (!m.contains(k)) remove.add(k);
        });
        remove.forEach(map::remove);
    }

    public static void resetGameRules(World world) {
        Reflections.setField(((CraftWorld) world).getHandle().worldData, "K", new GameRules());
    }
}
